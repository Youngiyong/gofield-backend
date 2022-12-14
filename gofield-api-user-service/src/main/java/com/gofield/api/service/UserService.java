package com.gofield.api.service;

import com.gofield.api.config.resolver.UserUuidResolver;
import com.gofield.api.dto.enums.TermSelectionType;
import com.gofield.api.dto.enums.TermType;
import com.gofield.api.dto.req.UserRequest;
import com.gofield.api.dto.res.*;
import com.gofield.common.exception.ForbiddenException;
import com.gofield.common.exception.InternalRuleException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.common.utils.EncryptUtils;
import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.domain.user.Term;
import com.gofield.domain.rds.domain.user.TermRepository;
import com.gofield.domain.rds.domain.user.TermGroup;
import com.gofield.domain.rds.domain.user.TermGroupRepository;
import com.gofield.domain.rds.domain.user.User;
import com.gofield.domain.rds.domain.user.UserRepository;
import com.gofield.domain.rds.domain.user.UserAccount;
import com.gofield.domain.rds.domain.user.UserAccountRepository;
import com.gofield.domain.rds.domain.user.UserAccountSmsHistoryRepository;
import com.gofield.domain.rds.domain.user.UserAddress;
import com.gofield.domain.rds.domain.user.UserAddressRepository;
import com.gofield.domain.rds.domain.user.UserTerm;
import com.gofield.domain.rds.domain.user.UserTermRepository;
import com.gofield.domain.rds.domain.user.UserAccountSmsHistory;
import com.gofield.domain.rds.domain.user.UserSnsRepository;
import com.gofield.domain.rds.domain.common.EStatusFlag;
import com.gofield.domain.rds.domain.user.ETermFlag;
import com.gofield.infrastructure.internal.api.sns.dto.request.SmsRequest;
import com.gofield.infrastructure.s3.infra.S3FileStorageClient;
import com.gofield.infrastructure.s3.model.enums.FileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${secret.cdn.url}")
    private String CDN_URL;

    @Value("${secret.sns.auth}")
    private String SNS_CERT_TOKEN;
    @Value("${secret.gofield.token_key}")
    private String TOKEN_DECRYPT_KEY;
    private final UserRepository userRepository;
    private final UserSnsRepository userSnsRepository;
    private final UserTermRepository userTermRepository;
    private final UserAddressRepository userAddressRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserAccountSmsHistoryRepository userAccountSmsHistoryRepository;
    private final TermRepository termRepository;
    private final TermGroupRepository termGroupRepository;
    private final SNSService snsService;
    private final S3FileStorageClient s3FileStorageClient;

    public String getUserDecryptUuid(){
        return EncryptUtils.aes256Decrypt(TOKEN_DECRYPT_KEY, UserUuidResolver.getCurrentUserUuid());
    }

    public void validateNonMember(User user){
        if(user.getUuid().equals("nonMember")){
            throw new ForbiddenException(ErrorCode.E403_FORBIDDEN_EXCEPTION, ErrorAction.TOAST, "???????????? ????????? ???????????????.");
        }
    }

    @Transactional(readOnly = true)
    public User getUser(){
        String uuid = getUserDecryptUuid();
        if(uuid.equals("nonMember")){
            System.out.println("nonMember");
            return User.newNonMemberInstance();
        }
        User user =  userRepository.findByUuidAndStatusActive(getUserDecryptUuid());
        if(user==null){
            throw new InternalRuleException(ErrorCode.E499_INTERNAL_RULE, ErrorAction.TOAST, "?????? ?????????????????? ?????? ???????????? ????????????.");
        }
        return user;
    }

    @Transactional(readOnly = true)
    public User getUserNotNonUser(){
        String uuid = getUserDecryptUuid();
        if(uuid.equals("nonMember")){
            throw new ForbiddenException(ErrorCode.E403_FORBIDDEN_EXCEPTION, ErrorAction.TOAST, "???????????? ????????? ???????????????.");
        }
        return userRepository.findByUuidAndStatusActive(getUserDecryptUuid());
    }

    @Transactional(readOnly = true)
    public UserAccount getUserAccount(Long userId){
        return userAccountRepository.findByUserId(userId);
    }

    @Transactional
    public void sendSms(UserRequest.UserAccountTel request){
        User user = getUserNotNonUser();
        List<Long> smsAccountCount = userAccountSmsHistoryRepository.todaySmsAccountCount(user.getId());
        if(smsAccountCount.size()>5){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("?????? ?????? ??????(5)??? ?????????????????????. ???????????? ?????? ??????????????????..", smsAccountCount.size()));
        }
        UserAccountSmsHistory userAccountSmsHistory = UserAccountSmsHistory.newInstance(user.getId(), request.getTel(), RandomUtils.makeRandomNumberCode(6));
        userAccountSmsHistoryRepository.save(userAccountSmsHistory);

        String content = String.format("???????????? ????????????\n\r[%s]??? ??????????????????.\n", userAccountSmsHistory.getCode());

        SmsRequest.SmsCustom smsCustom = SmsRequest.SmsCustom.builder()
                .messageType("SMS")
                .subject("[?????????]")
                .tel(request.getTel())
                .content(content)
                .build();

        snsService.sendSms(smsCustom);
    }

    @Transactional
    public void certSms(UserRequest.UserAccountCode request){
        User user = getUserNotNonUser();
        UserAccountSmsHistory userAccountSmsHistory = userAccountSmsHistoryRepository.findByUserIdAndCode(user.getId(), request.getCode());
        if(userAccountSmsHistory==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("%s ???????????? ?????? ???????????????.", request.getCode()));
        } else {
            user.updateTel(userAccountSmsHistory.getTel());
        }
    }

    @Transactional
    public void updateAccountInfo(UserRequest.UserAccountInfo request){
        User user = getUserNotNonUser();
        UserAccount userAccount = userAccountRepository.findByUserId(user.getId());
        userAccount.updateAccountInfo(request.getBankName(), request.getBankCode(), request.getBankHolderName(), request.getBankAccountNumber());
        if(request.getAgreeList()!=null){
            if(!request.getAgreeList().isEmpty()){
                insertUserHasTerm(request.getAgreeList(), user);
            }
        }
    }

    @Transactional(readOnly = true)
    public UserProfileResponse findUserProfile(){
        User user = getUserNotNonUser();
        return UserProfileResponse.of(user);
    }

    @Transactional(readOnly = true)
    public UserAccountResponse getUserAccount(){
        User user = getUserNotNonUser();
        UserAccount userAccount = userAccountRepository.findByUserId(user.getId());
        return UserAccountResponse.of(userAccount);
    }

    @Transactional(readOnly = true)
    public UserTelResponse getUserTel(){
        User user = getUserNotNonUser();
        return UserTelResponse.of(user);
    }

    @Transactional
    public void updateProfile(UserRequest.UserProfile request, MultipartFile file){
        String thumbnail = null;
        if(file!=null && !file.isEmpty()){
            thumbnail = s3FileStorageClient.uploadFile(file, FileType.USER_IMAGE);
        }
        User user = getUserNotNonUser();
        user.updateProfile(request.getName(), request.getNickName(), thumbnail, request.getWeight(), request.getHeight());
    }

    @Transactional
    public void userWithDraw(){
        User user = getUserNotNonUser();
        if(user.getStatus().equals(EStatusFlag.DELETE)){
            throw new InternalRuleException(ErrorCode.E499_INTERNAL_RULE, ErrorAction.TOAST, String.format("%s ?????? ????????? ??????????????????.", user.getNickName()));
        }
        user.withDraw();
        List<Long> userSnsIdList = userSnsRepository.findIdListByUserId(user.getId());
        if(!userSnsIdList.isEmpty()){
            userSnsRepository.withdraw(userSnsIdList);
        }
    }

    @Transactional(readOnly = true)
    public List<UserAddressResponse> findUserAddressList(){
        User user = getUserNotNonUser();
        List<UserAddress> userAddressList = user.getAddress();
        return UserAddressResponse.of(userAddressList);
    }

    @Transactional
    public void updateUserAddress(Long id, UserRequest.UserUpdateAddress request){
        User user = getUserNotNonUser();
        List<UserAddress> userAddressList = user.getAddress();
        Boolean isUpdate = false;

        for(UserAddress userAddress: userAddressList){
            if(userAddress.getId().equals(id)){
                isUpdate = true;
                userAddress.update(request.getTel(), request.getName(), request.getZipCode(), request.getAddress(), request.getAddressExtra(), request.getIsMain());
            } else {
                if(request.getIsMain()){
                    userAddress.updateMain(false);
                }
            }
        }
        if(!isUpdate){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("?????? ?????? ?????? ???????????? ???????????? ?????? ????????? ?????? ?????? ?????????<%s>?????????.", id));
        }
    }

    @Transactional
    public void createUserAddress(UserRequest.UserAddress request){
        User user = getUserNotNonUser();
        UserAddress userAddress = UserAddress.newInstance(user, request.getTel(), request.getName(), request.getZipCode(), request.getAddress(), request.getAddressExtra(), request.getIsMain());
        List<UserAddress> userAddressList = user.getAddress();
        for(UserAddress address: userAddressList){
            if(request.getAddress().equals(address.getAddress()) && request.getAddressExtra().equals(address.getAddressExtra())){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s %s> ????????? ???????????? ?????? ?????? ???????????????.", request.getAddress(), request.getAddressExtra()));
            }
            if(request.getIsMain()){
                address.updateMain(false);
            }
        }
        userAddressRepository.save(userAddress);
    }

    @Transactional
    public void deleteUserAddress(Long id){
        User user = getUserNotNonUser();
        UserAddress userAddress = userAddressRepository.findByIdAndUserId(id, user.getId());
        if(userAddress==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("?????? ?????? ?????? ???????????? ???????????? ?????? ????????? ?????? ?????? ?????????<%s>?????????.", id));
        }
        userAddressRepository.delete(userAddress);
    }


    @Transactional(readOnly = true)
    public List<TermResponse> getTermList(TermType type){
        TermGroup termGroup = null;
        if(type.equals(TermType.SIGNUP)){
            termGroup = termGroupRepository.findByGroupId(2L);
        } else if(type.equals(TermType.PRIVACY)){
            termGroup = termGroupRepository.findByGroupId(3L);
        }
        return TermResponse.of(termGroup.getTerms());
    }


    @Transactional
    public void insertUserHasTerm(List<Long> termList, User user){
        List<Term> resultTermList = termRepository.findAllByInId(termList);
        for(Term term: resultTermList){
            UserTerm userTerm = UserTerm.newInstance(user, term);
            userTermRepository.save(userTerm);
        }
    }

    @Transactional
    public void insertUserHasTerm(TermSelectionType selectionType, User user){
        ETermFlag termFlag = ETermFlag.MARKETING;
        if(selectionType.equals(ETermFlag.MARKETING_EMAIL)){
            termFlag = ETermFlag.MARKETING_EMAIL;
        } else if(selectionType.equals(ETermFlag.MARKETING_SMS)){
            termFlag = ETermFlag.MARKETING_SMS;
        } else if(selectionType.equals(ETermFlag.MARKETING_PUSH)){
            termFlag = ETermFlag.MARKETING_PUSH;
        }

        Term term = termRepository.findByType(termFlag);
        UserTerm userTerm = UserTerm.newInstance(user, term);
        userTermRepository.save(userTerm);
    }

    @Transactional(readOnly = true)
    public UserAddress getUserMainAddress(Long userId){
        return userAddressRepository.findByUserIdOrderByMain(userId);
    }

    @Transactional(readOnly = true)
    public UserAlertResponse getUserAlert(){
        return UserAlertResponse.of(getUserNotNonUser());
    }

    @Transactional
    public void updateUserAlert(UserRequest.UserAlert request){
        User user = getUserNotNonUser();
        user.updateUserAlertPromotion(request.getIsAlertPromotion());
    }

}
