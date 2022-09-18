package com.gofield.api.service;

import com.gofield.api.config.resolver.UserUuidResolver;
import com.gofield.api.model.request.UserRequest;
import com.gofield.api.model.response.UserAccountResponse;
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
import com.gofield.domain.rds.entity.userAddress.UserAddressRepository;
import com.gofield.domain.rds.entity.userHasTerm.UserHasTerm;
import com.gofield.domain.rds.entity.userHasTerm.UserHasTermRepository;
import com.gofield.domain.rds.entity.userPush.UserPush;
import com.gofield.domain.rds.entity.userPush.UserPushRepository;
import com.gofield.domain.rds.entity.userAccountSmsHistory.UserAccountSmsHistory;
import com.gofield.domain.rds.entity.userSns.UserSns;
import com.gofield.domain.rds.entity.userSns.UserSnsRepository;
import com.gofield.domain.rds.enums.EStatusFlag;
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
    public UserProfileResponse getUserProfile(){
        User user = getUser();
        return UserProfileResponse.of(user, CDN_URL);
    }

    @Transactional(readOnly = true)
    public UserAccountResponse getUserAccount(){
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



    public void insertUserHasTerm(List<Long> termList, User user){
        List<Term> resultTermList = termRepository.findAllByInId(termList);
        for(Term term: resultTermList){
            UserHasTerm userHasTerm = UserHasTerm.newInstance(user, term);
            userHasTermRepository.save(userHasTerm);
        }
    }
}
