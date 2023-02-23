package com.gofield.api.service.user;

import com.gofield.api.service.user.dto.TermSelectionType;
import com.gofield.api.service.user.dto.TermType;
import com.gofield.api.service.user.dto.response.TermResponse;
import com.gofield.api.service.user.dto.request.UserRequest;
import com.gofield.api.service.sns.SnsService;
import com.gofield.api.service.user.dto.response.*;
import com.gofield.common.exception.*;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
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
    private final SnsService snsService;
    private final S3FileStorageClient s3FileStorageClient;


    @Transactional(readOnly = true)
    public User getUser(Long userId){
        User user = userRepository.findByUserIdAndStatusActive(userId);
        if(user==null){
            throw new ForbiddenException(ErrorCode.E403_FORBIDDEN_EXCEPTION, ErrorAction.NONE, String.format("%s userId는 제한된 사용자이거나 존재하지 않는 사용자입니다."));
        }
        return user;
    }

    @Transactional
    public void sendSms(UserRequest.UserAccountTel request, Long userId){
        List<Long> smsAccountCount = userAccountSmsHistoryRepository.todaySmsAccountCount(userId);
        if(smsAccountCount.size()>5){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("하루 인증 횟수(5)가 초과되었습니다. 다음날에 다시 이용해주세요..", smsAccountCount.size()));
        }
        UserAccountSmsHistory userAccountSmsHistory = UserAccountSmsHistory.newInstance(userId, request.getTel(), RandomUtils.makeRandomNumberCode(6));
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
    public void certSms(UserRequest.UserAccountCode request, Long userId){
        UserAccountSmsHistory userAccountSmsHistory = userAccountSmsHistoryRepository.findByUserIdAndCode(userId, request.getCode());
        User user = getUser(userId);
        if(userAccountSmsHistory==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("%s 일치하지 않는 코드입니다.", request.getCode()));
        } else {
            user.updateTel(userAccountSmsHistory.getTel());
        }
    }

    @Transactional
    public void updateAccountInfo(UserRequest.UserAccountInfo request, Long userId){
        User user = getUser(userId);
        UserAccount userAccount = userAccountRepository.findByUserId(user.getId());
        userAccount.updateAccountInfo(request.getBankName(), request.getBankCode(), request.getBankHolderName(), request.getBankAccountNumber());
        if(request.getAgreeList()!=null){
            if(!request.getAgreeList().isEmpty()){
                insertUserHasTerm(request.getAgreeList(), user);
            }
        }
    }

    @Transactional(readOnly = true)
    public UserProfileResponse retrieveProfile(Long userId){
        User user = getUser(userId);
        return UserProfileResponse.of(user);
    }

    @Transactional(readOnly = true)
    public UserAccountResponse retrieveAccount(Long userId){
        User user = getUser(userId);
        UserAccount userAccount = userAccountRepository.findByUserId(user.getId());
        return UserAccountResponse.of(userAccount);
    }

    @Transactional(readOnly = true)
    public UserTelResponse retrieveTel(Long userId){
        User user = getUser(userId);
        return UserTelResponse.of(user);
    }

    @Transactional
    public void updateProfile(UserRequest.UserProfile request, MultipartFile file, Long userId){
        User user = getUser(userId);

        String thumbnail = null;
        if(file!=null && !file.isEmpty()){
            thumbnail = s3FileStorageClient.uploadFile(file, FileType.USER_IMAGE);
        }
        user.updateProfile(request.getName(), request.getNickName(), thumbnail, request.getWeight(), request.getHeight());
    }

    @Transactional
    public void userWithDraw(Long userId){
        User user = getUser(userId);
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
    public List<UserAddressResponse> retrieveAddresses(Long userId){
        User user = getUser(userId);
        List<UserAddress> userAddressList = user.getAddress();
        return UserAddressResponse.of(userAddressList);
    }

    @Transactional
    public void updateAddress(Long id, UserRequest.UserUpdateAddress request, Long userId){
        User user = getUser(userId);
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
    public void registerAddress(UserRequest.UserAddress request, Long userId){
        User user = getUser(userId);
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
    public void deleteAddress(Long id, Long userId){
        User user = getUser(userId);
        UserAddress userAddress = userAddressRepository.findByIdAndUserId(id, user.getId());
        if(userAddress==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("이미 삭제 처리 되었거나 존재하지 않는 사용자 배송 주소 아이디<%s>입니다.", id));
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
    public UserAlertResponse getUserAlert(Long userId){
        return UserAlertResponse.of(getUser(userId));
    }

    @Transactional
    public void updateUserAlert(UserRequest.UserAlert request, Long userId){
        User user = getUser(userId);
        user.updateUserAlertPromotion(request.getIsAlertPromotion());
    }

}
