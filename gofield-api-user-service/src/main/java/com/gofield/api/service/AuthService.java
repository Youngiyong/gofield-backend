package com.gofield.api.service;


import com.gofield.api.dto.Authentication;
import com.gofield.api.dto.SocialAuthentication;
import com.gofield.api.dto.req.LoginRequest;
import com.gofield.api.dto.req.SignupRequest;
import com.gofield.api.dto.req.TokenRefreshRequest;
import com.gofield.api.dto.res.ClientResponse;
import com.gofield.api.dto.res.LoginResponse;
import com.gofield.api.dto.res.TokenResponse;
import com.gofield.api.util.ApiUtil;
import com.gofield.api.util.TokenUtil;
import com.gofield.common.exception.InternalRuleException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.Constants;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.utils.LocalDateTimeUtils;
import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.entity.category.Category;
import com.gofield.domain.rds.entity.category.CategoryRepository;
import com.gofield.domain.rds.entity.device.DeviceRepository;
import com.gofield.domain.rds.entity.deviceModel.DeviceModelRepository;
import com.gofield.domain.rds.entity.term.Term;
import com.gofield.domain.rds.entity.term.TermRepository;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.entity.user.UserRepository;
import com.gofield.domain.rds.entity.userAccess.UserAccess;
import com.gofield.domain.rds.entity.userAccess.UserAccessRepository;
import com.gofield.domain.rds.entity.userWebAccessLog.UserWebAccessLog;
import com.gofield.domain.rds.entity.userWebAccessLog.UserWebWebAccessLogRepository;
import com.gofield.domain.rds.entity.userAccount.UserAccount;
import com.gofield.domain.rds.entity.userAccount.UserAccountRepository;
import com.gofield.domain.rds.entity.userClientDetail.UserClientDetail;
import com.gofield.domain.rds.entity.userClientDetail.UserClientDetailRepository;
import com.gofield.domain.rds.entity.userHasCategory.UserHasCategory;
import com.gofield.domain.rds.entity.userHasCategory.UserHasCategoryRepository;
import com.gofield.domain.rds.entity.userHasDevice.UserHasDeviceRepository;
import com.gofield.domain.rds.entity.userHasTerm.UserHasTerm;
import com.gofield.domain.rds.entity.userHasTerm.UserHasTermRepository;
import com.gofield.domain.rds.entity.userSns.UserSns;
import com.gofield.domain.rds.entity.userSns.UserSnsRepository;
import com.gofield.domain.rds.entity.userWebToken.UserWebToken;
import com.gofield.domain.rds.entity.userWebToken.UserWebWebTokenRepository;
import com.gofield.domain.rds.enums.ESocialFlag;
import com.gofield.domain.rds.enums.EStatusFlag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final TermRepository termRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final UserSnsRepository userSnsRepository;
    private final CategoryRepository categoryRepository;
    private final UserWebWebTokenRepository userWebTokenRepository;
    private final UserAccessRepository userAccessRepository;
    private final DeviceModelRepository deviceModelRepository;
    private final UserHasTermRepository userHasTermRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserWebWebAccessLogRepository userWebAccessLogRepository;
    private final UserHasDeviceRepository userHasDeviceRepository;
    private final UserHasCategoryRepository userHasCategoryRepository;
    private final UserClientDetailRepository userClientDetailRepository;

    private final TokenUtil tokenUtil;

    private final ThirdPartyService thirdPartyService;


//    @Transactional
//    public TokenResponse loginAuto(LoginAutoRequest request){
//        Device device = deviceRepository.findByDeviceKey(request.getDeviceKey());
//        if(device==null){
//            throw new InternalRuleException(ErrorCode.E499_INTERNAL_RULE, ErrorAction.NONE, String.format("<%s> 존재하지 않는 디바이스키입니다.", request.getDeviceKey()));
//        }
//        UserAccess userAccess = userAccessRepository.findByDeviceIdAndAccessKey(device.getId(), request.getAccessKey());
//        if(userAccess==null){
//            throw new InternalRuleException(ErrorCode.E499_INTERNAL_RULE, ErrorAction.NONE, String.format("<%s> 일치하지 않는 엑세스키입니다.", request.getAccessKey()));
//        }
//        UserToken userToken = userTokenRepository.findByAccessId(userAccess.getId());
//        UserClientDetail userClientDetail = userToken.getClient();
//        Authentication authentication = getAuthentication(userAccess.getUser().getUuid(), userAccess.getUser().getId() , Constants.TOKEN_ISSUER);
//        TokenResponse token = tokenUtil.generateToken(authentication, userClientDetail.getAccessTokenValidity(), userClientDetail.getRefreshTokenValidity());
//        LocalDateTime refreshExpireDate = LocalDateTimeUtils.epochMillToLocalDateTime(token.getRefreshTokenExpiresIn());
//        //토큰 테이블을 날릴경우 Null일 수 있음
//        if(userToken==null){
//            userToken = UserToken.newInstance(userClientDetail, userToken.getUser(), userAccess, token.getRefreshToken(), refreshExpireDate);
//            userTokenRepository.save(userToken);
//        } else {
//            userToken.updateToken(token.getRefreshToken(), refreshExpireDate);
//        }
//        return token;
//    }

    @Transactional
    public LoginResponse login(LoginRequest request, String secret){
        Boolean isFirst = false;

        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String ipAddress = ApiUtil.getIpAddress(servletRequest);
        String userAgent = servletRequest.getHeader("User-Agent");

        ClientResponse clientDetail = ApiUtil.base64EncodingStrToDecodeClientDetail(secret);
        UserClientDetail resultClientDetail = userClientDetailRepository.findByClientId(clientDetail.getClientId());
        if(resultClientDetail==null){
            throw new NotFoundException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, String.format("<%s> 유효하지 않는 클라이언트 아이디입니다.", clientDetail.getClientId()));
        }
        SocialAuthentication socialAuthentication = thirdPartyService.getAuthentication(request.getState(), request.getCode(), request.getSocial());

        UserSns userSns = userSnsRepository.findByUniQueIdAndRoute(socialAuthentication.getUniqueId(), request.getSocial());
        if(userSns==null){
            User saveUser = User.newInstance(RandomUtils.makeRandomUuid(), socialAuthentication.getNickName()==null ? "신규고객" : socialAuthentication.getNickName());
            userRepository.save(saveUser);
            UserSns saveSns = UserSns.newInstance(saveUser, socialAuthentication.getUniqueId(), request.getSocial());
            userSnsRepository.save(saveSns);
            userSns = saveSns;
        }

        UserAccount userAccount = userAccountRepository.findByUserId(userSns.getUser().getId());
        if(userAccount==null){
            isFirst = true;
        }
        Authentication authentication = getAuthentication(userSns.getUser().getUuid(), userSns.getUser().getId() , Constants.TOKEN_ISSUER);
        TokenResponse token = tokenUtil.generateToken(authentication, resultClientDetail.getAccessTokenValidity(), resultClientDetail.getRefreshTokenValidity());
        LocalDateTime refreshExpireDate = LocalDateTimeUtils.epochMillToLocalDateTime(token.getRefreshTokenExpiresIn());

        UserWebToken userWebToken = UserWebToken.newInstance(resultClientDetail, userSns.getUser(), token.getAccessToken(), token.getRefreshToken(), refreshExpireDate);
        userWebTokenRepository.save(userWebToken);

        UserWebAccessLog saveLog = UserWebAccessLog.newInstance(userSns.getUser().getId(), userAgent, ipAddress);
        userWebAccessLogRepository.save(saveLog);
        return LoginResponse.of(isFirst, token.getGrantType(), token.getAccessToken(), token.getRefreshToken(), token.getAccessTokenExpiresIn(), token.getRefreshTokenExpiresIn());
    }

    @Transactional
    public void signup(SignupRequest request){
        if(request.getAgreeList()==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "필수 약관은 선택해 주셔야 됩니다.");
        }
        User user = userRepository.findByUuid(userService.getUserDecryptUuid());
        if(user==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("존재하지 않는 사용자입니다."));
        }
        if(user.getStatus().equals(EStatusFlag.ACTIVE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s>는 이미 가입되어 있는 사용자입니다.", user.getNickName()));
        } else if(!user.getStatus().equals(EStatusFlag.WAIT))
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s>는 활성된 상태가 아니거나 가입되어 있지 않는 사용자입니다.", user.getNickName()));
        UserAccount account = userAccountRepository.findByUserId(user.getId());
        if(account!=null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s>는 이미 계정 정보가 등록되어 있는 사용자입니다.", user.getNickName()));
        } else {
            account = UserAccount.newInstance(user);
            userAccountRepository.save(account);
        }
        if(request.getCategoryList()!=null){
            if(!request.getCategoryList().isEmpty()){
                List<Category> resultCategoryList = categoryRepository.findAllByInId(request.getCategoryList());
                for(Category category: resultCategoryList){
                    UserHasCategory userHasCategory = UserHasCategory.newInstance(user, category);
                    userHasCategoryRepository.save(userHasCategory);
                }
            }
        }
        if(request.getAgreeList()!=null){
            if(!request.getAgreeList().isEmpty()){
                userService.insertUserHasTerm(request.getAgreeList(), user);
            }
        }
        if(request.getDisAgreeList()!=null){
            if(!request.getDisAgreeList().isEmpty()){
                List<Term> resultTermList = termRepository.findAllByInId(request.getDisAgreeList());
                for(Term term: resultTermList){
                    if(term.getIsEssential()){
                        throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "선택 약관 리스트에 필수항목이 있습니다.");
                    } else {
                        UserHasTerm userHasTerm = UserHasTerm.newInstance(user, term);
                        userHasTermRepository.save(userHasTerm);
                    }
                }
            }
        }

        user.updateSign(request.getEmail(), request.getWeight(), request.getHeight());
    }

    @Transactional(readOnly = true)
    public TokenResponse getToken(Long id){
        User resultUser = userRepository.findByIdAndStatusActive(id);
        UserClientDetail resultClientDetail = userClientDetailRepository.findByClientId(21L);
        Authentication authentication = new Authentication(resultUser.getUuid(), resultUser.getId() ,Constants.TOKEN_ISSUER);
        return tokenUtil.generateToken(authentication, resultClientDetail.getAccessTokenValidity(), resultClientDetail.getRefreshTokenValidity());
    }

    @Transactional
    public void logout(String accessKey){
        UserAccess userAccess = userAccessRepository.findByAccessKey(accessKey);
        userWebTokenRepository.delete(userAccess.getId());
        userAccessRepository.delete(userAccess);
    }
    @Transactional
    public TokenResponse refresh(TokenRefreshRequest request){
        UserWebToken userWebToken = userWebTokenRepository.findByRefreshToken(request.getRefreshToken());
        if(userWebToken ==null || userWebToken.getExpireDate().isBefore(LocalDateTime.now())){
            throw new InternalRuleException(ErrorCode.E499_INTERNAL_RULE, ErrorAction.TOAST, "세션이 만료되어 로그아웃됩니다.");
        }
        User resultUser = userRepository.findByIdAndStatusActive(userWebToken.getUser().getId());
        UserClientDetail resultClientDetail = userClientDetailRepository.findByClientId(userWebToken.getClient().getId());
        Authentication authentication = getAuthentication(resultUser.getUuid(), resultUser.getId() , Constants.TOKEN_ISSUER);
        TokenResponse token = tokenUtil.generateToken(authentication, resultClientDetail.getAccessTokenValidity(), resultClientDetail.getRefreshTokenValidity());
        LocalDateTime refreshExpireDate = LocalDateTimeUtils.epochMillToLocalDateTime(token.getRefreshTokenExpiresIn());
        userWebToken.updateToken(null, token.getRefreshToken(), refreshExpireDate);
        return token;
    }

    private Authentication getAuthentication(String uuid, Long userId, String issue){
        return Authentication.builder()
                .uuid(uuid)
                .userId(userId)
                .issue(issue)
                .build();
    }
}

