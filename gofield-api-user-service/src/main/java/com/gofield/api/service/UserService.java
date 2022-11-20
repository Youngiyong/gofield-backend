package com.gofield.api.service;

import com.gofield.api.config.resolver.UserUuidResolver;
import com.gofield.api.dto.enums.TermSelectionType;
import com.gofield.api.dto.enums.TermType;
import com.gofield.api.dto.req.UserRequest;
import com.gofield.api.dto.res.*;
import com.gofield.common.exception.InternalRuleException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.utils.EncryptUtils;
import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.entity.cart.CartRepository;
import com.gofield.domain.rds.entity.category.Category;
import com.gofield.domain.rds.entity.category.CategoryRepository;
import com.gofield.domain.rds.entity.term.Term;
import com.gofield.domain.rds.entity.term.TermRepository;
import com.gofield.domain.rds.entity.termGroup.TermGroup;
import com.gofield.domain.rds.entity.termGroup.TermGroupRepository;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.entity.user.UserRepository;
import com.gofield.domain.rds.entity.userAccount.UserAccount;
import com.gofield.domain.rds.entity.userAccount.UserAccountRepository;
import com.gofield.domain.rds.entity.userAccountSmsHistory.UserAccountSmsHistoryRepository;
import com.gofield.domain.rds.entity.userAddress.UserAddress;
import com.gofield.domain.rds.entity.userAddress.UserAddressRepository;
import com.gofield.domain.rds.entity.userHasTerm.UserHasTerm;
import com.gofield.domain.rds.entity.userHasTerm.UserHasTermRepository;
import com.gofield.domain.rds.entity.userWebPush.UserWebPush;
import com.gofield.domain.rds.entity.userWebPush.UserWebWebPushRepository;
import com.gofield.domain.rds.entity.userAccountSmsHistory.UserAccountSmsHistory;
import com.gofield.domain.rds.entity.userSns.UserSnsRepository;
import com.gofield.domain.rds.enums.EStatusFlag;
import com.gofield.domain.rds.enums.ETermFlag;
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

    private final CartRepository cartRepository;
    private final TermRepository termRepository;
    private final TermGroupRepository termGroupRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final UserSnsRepository userSnsRepository;
    private final UserWebWebPushRepository userWebPushRepository;
    private final UserAddressRepository userAddressRepository;
    private final UserHasTermRepository userHasTermRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserAccountSmsHistoryRepository userAccountSmsHistoryRepository;
    private final SNSService snsService;
    private final S3FileStorageClient s3FileStorageClient;

    public String getUserDecryptUuid(){
        return EncryptUtils.aes256Decrypt(TOKEN_DECRYPT_KEY, UserUuidResolver.getCurrentUserUuid());
    }

    public String uploadProfile(MultipartFile file){
        return s3FileStorageClient.uploadFile(file, FileType.USER_IMAGE);
    }

    @Transactional(readOnly = true)
    public CountResponse getCartCount(){
        return CountResponse.of(cartRepository.getCartCount(getUserDecryptUuid()));
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
        UserWebPush userWebPush = userWebPushRepository.findByUserIdAndPushKey(user.getId(), request.getPushKey());
        if(userWebPush==null){
            userWebPush = UserWebPush.newInstance(user, request.getPushKey());
            userWebPushRepository.save(userWebPush);
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

        String content = String.format("본인확인 인증번호\n\r[%s]를 입력해주세요.\n", userAccountSmsHistory.getCode());

        SmsRequest.SmsCustom smsCustom = SmsRequest.SmsCustom.builder()
                .messageType("SMS")
                .subject("[고필드]")
                .tel(request.getTel())
                .content(content)
                .build();

        snsService.sendSms(smsCustom);
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
        userAccount.updateAccountInfo(request.getBankName(), request.getBankCode(), request.getBankHolderName(), request.getBankAccountNumber());
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
        user.updateProfile(request.getName(), request.getNickName(), request.getThumbnail(),  request.getIsAlertPromotion(), request.getWeight(), request.getHeight());
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

    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryList(){
        List<Category> resultList = categoryRepository.findAllIsActiveAndIsAttentionOrderBySort();
        return CategoryResponse.of(resultList);
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


    public void insertUserHasTerm(List<Long> termList, User user){
        List<Term> resultTermList = termRepository.findAllByInId(termList);
        for(Term term: resultTermList){
            UserHasTerm userHasTerm = UserHasTerm.newInstance(user, term);
            userHasTermRepository.save(userHasTerm);
        }
    }

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
        UserHasTerm userHasTerm = UserHasTerm.newInstance(user, term);
        userHasTermRepository.save(userHasTerm);
    }



}
