package com.gofield.api.service;

import com.gofield.api.config.resolver.UserUuidResolver;
import com.gofield.api.model.request.UserRequest;
import com.gofield.api.model.response.UserAccountResponse;
import com.gofield.api.model.response.UserAddressResponse;
import com.gofield.api.model.response.UserProfileResponse;
import com.gofield.common.exception.InternalRuleException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.utils.EncryptUtils;
import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.entity.term.Term;
import com.gofield.domain.rds.entity.term.TermRepository;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.entity.user.UserRepository;
import com.gofield.domain.rds.entity.userAccount.UserAccount;
import com.gofield.domain.rds.entity.userAccount.UserAccountRepository;
import com.gofield.domain.rds.entity.userAccountSmsHistory.UserAccountSmsHistoryRepository;
import com.gofield.domain.rds.entity.userAddress.UserAddress;
import com.gofield.domain.rds.entity.userAddress.UserAddressRepository;
import com.gofield.domain.rds.entity.userHasTerm.UserHasTerm;
import com.gofield.domain.rds.entity.userHasTerm.UserHasTermRepository;
import com.gofield.domain.rds.entity.userPush.UserPush;
import com.gofield.domain.rds.entity.userPush.UserPushRepository;
import com.gofield.domain.rds.entity.userAccountSmsHistory.UserAccountSmsHistory;
import com.gofield.domain.rds.entity.userSns.UserSns;
import com.gofield.domain.rds.entity.userSns.UserSnsRepository;
import com.gofield.domain.rds.enums.EStatusFlag;
import com.gofield.infrastructure.external.api.naver.NaverSnsApiClient;
import com.gofield.infrastructure.internal.api.sns.GofieldSnsApiClient;
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
    @Value("${cdn.url}")
    private String CDN_URL;

    @Value("${sns.auth}")
    private String SNS_CERT_TOKEN;
    @Value("${gofield.token_key}")
    private String tokenDecryptKey;

    private final TermRepository termRepository;
    private final UserRepository userRepository;
    private final UserSnsRepository userSnsRepository;
    private final UserPushRepository userPushRepository;
    private final UserAddressRepository userAddressRepository;
    private final UserHasTermRepository userHasTermRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserAccountSmsHistoryRepository userAccountSmsHistoryRepository;


    private final GofieldSnsApiClient gofieldSnsApiClient;
    private final S3FileStorageClient s3FileStorageClient;

    public String getUserDecryptUuid(){
        return EncryptUtils.aes256Decrypt(tokenDecryptKey, UserUuidResolver.getCurrentUserUuid());
    }

    public String uploadProfile(MultipartFile file){
        return s3FileStorageClient.uploadFile(file, FileType.USER_IMAGE);
    }

    @Transactional(readOnly = true)
    public User getUser(){
        User user =  userRepository.findByUuidAndStatusActive(getUserDecryptUuid());
        if(user==null){
            throw new InternalRuleException(ErrorCode.E499_INTERNAL_RULE, ErrorAction.TOAST, "삭제 처리되었거나 정상 사용자가 아닙니다.");
        }
        return user;
    }

    @Transactional
    public void updatePush(UserRequest.PushKey request){
        User user = getUser();
        UserPush userPush = userPushRepository.findByPlatformAndPushKey(request.getPlatform(), request.getPushKey());
        if(userPush==null){
            userPush = UserPush.newInstance(user, request.getPushKey(), request.getPlatform());
            userPushRepository.save(userPush);
        } else {
            userPush.update(request.getPushKey());
        }
    }

    @Transactional
    public void sendSms(UserRequest.UserAccountTel request){
        User user = getUser();
        List<Long> smsAccountCount = userAccountSmsHistoryRepository.todaySmsAccountCount(user.getId());
        if(smsAccountCount.size()>5){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("하루 인증 횟수(5)가 초과되었습니다. 다음날에 다시 이용해주세요..", smsAccountCount.size()));
        }
        UserAccountSmsHistory userAccountSmsHistory = UserAccountSmsHistory.newInstance(user.getId(), request.getTel(), RandomUtils.makeRandomNumberCode(6));
        userAccountSmsHistoryRepository.save(userAccountSmsHistory);

        String content = "" +
                "[고필드 인증]\r\n" +
                "본인확인 인증번호는\r\n" + "[" + userAccountSmsHistory.getCode() + "]" +
                "입니다.\n";

        SmsRequest.SmsCustom smsCustom = SmsRequest.SmsCustom.builder()
                .messageType("LMS")
                .tel(request.getTel())
                .subject("고필드 [인증]")
                .content(content)
                .build();

        gofieldSnsApiClient.sendSingleMessage(SNS_CERT_TOKEN, smsCustom);
    }

    @Transactional
    public void certSms(UserRequest.UserAccountCode request){
        User user = getUser();
        UserAccountSmsHistory userAccountSmsHistory = userAccountSmsHistoryRepository.findByUserIdAndCode(user.getId(), request.getCode());
        if(userAccountSmsHistory==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("%s 일치하지 않는 코드입니다.", request.getCode()));
        } else {
            user.updateTel(userAccountSmsHistory.getTel());
        }
    }

    @Transactional
    public void updateAccountInfo(UserRequest.UserAccountInfo request){
        User user = getUser();
        UserAccount userAccount = userAccountRepository.findByUserId(user.getId());
        userAccount.updateAccountInfo(request.getBankName(), request.getBankHolderName(), request.getBankAccountNumber());
        if(request.getAgreeList()!=null){
            if(!request.getAgreeList().isEmpty()){
                insertUserHasTerm(request.getAgreeList(), user);
            }
        }
    }

    @Transactional(readOnly = true)
    public UserProfileResponse findUserProfile(){
        User user = getUser();
        return UserProfileResponse.of(user, CDN_URL);
    }

    @Transactional(readOnly = true)
    public UserAccountResponse findUserAccount(){
        User user = getUser();
        UserAccount userAccount = userAccountRepository.findByUserId(user.getId());
        return UserAccountResponse.of(userAccount);
    }

    @Transactional
    public void updateProfile(UserRequest.UserProfile request){
        User user = getUser();
        user.updateProfile(request.getName(), request.getNickName(), request.getThumbnail(), request.getWeight(), request.getHeight(), request.getIsAlertPromotion());
    }

    @Transactional
    public void userWithDraw(){
        User user = getUser();
        if(user.getStatus().equals(EStatusFlag.DELETE)){
            throw new InternalRuleException(ErrorCode.E499_INTERNAL_RULE, ErrorAction.TOAST, String.format("%s 이미 탈퇴한 사용자입니다.", user.getNickName()));
        }
        user.withDraw();
        List<Long> userSnsIdList = userSnsRepository.findIdListByUserId(user.getId());
        if(!userSnsIdList.isEmpty()){
            userSnsRepository.withdraw(userSnsIdList);
        }
    }

    @Transactional(readOnly = true)
    public List<UserAddressResponse> findUserAddressList(){
        User user = getUser();
        List<UserAddress> userAddressList = user.getAddress();
        return UserAddressResponse.of(userAddressList);
    }

    @Transactional
    public void updateUserAddress(Long id, UserRequest.UserUpdateAddress request){
        User user = getUser();
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
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("이미 삭제 처리 되었거나 존재하지 않는 사용자 배송 주소 아이디<%s>입니다.", id));
        }

    }

    @Transactional
    public void insertUserAddress(UserRequest.UserAddress request){
        User user = getUser();
        UserAddress userAddress = UserAddress.newInstance(user, request.getTel(), request.getName(), request.getZipCode(), request.getAddress(), request.getAddressExtra(), request.getIsMain());
        List<UserAddress> userAddressList = user.getAddress();
        for(UserAddress address: userAddressList){
            if(request.getAddress().equals(address.getAddress()) && request.getAddressExtra().equals(address.getAddressExtra())){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s %s> 기존에 추가되어 있는 배송 주소입니다.", request.getAddress(), request.getAddressExtra()));
            }
            if(request.getIsMain()){
                address.updateMain(false);
            }
        }
        userAddressRepository.save(userAddress);
    }

    @Transactional
    public void deleteUserAddress(Long id){
        User user = getUser();
        UserAddress userAddress = userAddressRepository.findByIdAndUserId(id, user.getId());
        if(userAddress==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("이미 삭제 처리 되었거나 존재하지 않는 사용자 배송 주소 아이디<%s>입니다.", id));
        }
        userAddressRepository.delete(userAddress);
    }

    public void insertUserHasTerm(List<Long> termList, User user){
        List<Term> resultTermList = termRepository.findAllByInId(termList);
        for(Term term: resultTermList){
            UserHasTerm userHasTerm = UserHasTerm.newInstance(user, term);
            userHasTermRepository.save(userHasTerm);
        }
    }
}
