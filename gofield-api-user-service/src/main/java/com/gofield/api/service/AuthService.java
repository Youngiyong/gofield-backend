package com.gofield.api.service;

import com.gofield.api.dto.Authentication;
import com.gofield.api.dto.SocialAuthentication;
import com.gofield.api.dto.enums.TermSelectionType;
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
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.common.utils.LocalDateTimeUtils;
import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.domain.common.EEnvironmentFlag;
import com.gofield.domain.rds.domain.item.Category;
import com.gofield.domain.rds.domain.item.CategoryRepository;
import com.gofield.domain.rds.domain.user.*;
import com.gofield.domain.rds.domain.user.UserAccessLog;
import com.gofield.domain.rds.domain.user.UserAccessLogRepository;
import com.gofield.domain.rds.domain.user.UserCategory;
import com.gofield.domain.rds.domain.user.UserSns;
import com.gofield.domain.rds.domain.user.UserSnsRepository;
import com.gofield.domain.rds.domain.user.UserToken;
import com.gofield.domain.rds.domain.user.UserTokenRepository;
import com.gofield.domain.rds.domain.user.ESocialFlag;
import com.gofield.domain.rds.domain.common.EStatusFlag;
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
    private final UserRepository userRepository;
    private final UserSnsRepository userSnsRepository;
    private final UserTokenRepository userWebTokenRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserAccessLogRepository userWebAccessLogRepository;
    private final UserClientDetailRepository userClientDetailRepository;
    private final TokenUtil tokenUtil;
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final ThirdPartyService thirdPartyService;

    @Transactional
    public LoginResponse login(LoginRequest request, String secret){
        Boolean isSign = true;

        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String ipAddress = ApiUtil.getIpAddress(servletRequest);
        String userAgent = servletRequest.getHeader("User-Agent");

        ClientResponse clientDetail = ApiUtil.base64EncodingStrToDecodeClientDetail(secret);
        UserClientDetail resultClientDetail = userClientDetailRepository.findByClientId(clientDetail.getClientId());
        if(resultClientDetail==null){
            throw new NotFoundException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, String.format("<%s> 유효하지 않는 클라이언트 아이디입니다.", clientDetail.getClientId()));
        }
        SocialAuthentication socialAuthentication = thirdPartyService.getSocialAuthentication(request.getCode(), request.getSocial(), request.getEnvironment());

        UserSns userSns = userSnsRepository.findByUniQueIdAndRoute(socialAuthentication.getUniqueId(), request.getSocial());
        if(userSns==null){
            User saveUser = User.newInstance(RandomUtils.makeRandomUuid(), socialAuthentication.getEmail(), socialAuthentication.getNickName(), socialAuthentication.getNickName());
            userRepository.save(saveUser);
            UserSns saveSns = UserSns.newInstance(saveUser, socialAuthentication.getUniqueId(), request.getSocial());
            userSnsRepository.save(saveSns);
            userSns = saveSns;
        }

        UserAccount userAccount = userAccountRepository.findByUserId(userSns.getUser().getId());
        if(userAccount==null){
            isSign = false;
        }

        Authentication authentication = Authentication.of(userSns.getUser().getUuid(), userSns.getUser().getId() , Constants.TOKEN_ISSUER);
        TokenResponse token = tokenUtil.generateToken(authentication, resultClientDetail.getAccessTokenValidity(), resultClientDetail.getRefreshTokenValidity(), isSign, request.getSocial().getKey());
        LocalDateTime refreshExpireDate = LocalDateTimeUtils.epochMillToLocalDateTime(token.getRefreshTokenExpiresIn());

        UserToken userToken = UserToken.newInstance(resultClientDetail, userSns.getUser(), token.getAccessToken(), token.getRefreshToken(), refreshExpireDate);
        userWebTokenRepository.save(userToken);

        UserAccessLog saveLog = UserAccessLog.newInstance(userSns.getUser().getId(), userAgent, ipAddress);
        userWebAccessLogRepository.save(saveLog);
        return LoginResponse.of(token.getGrantType(), token.getAccessToken(), token.getRefreshToken(), token.getAccessTokenExpiresIn(), token.getRefreshTokenExpiresIn(), isSign);
    }

    @Transactional
    public TokenResponse signup(String Authorization, SignupRequest request){
        User user = userRepository.findByUuid(userService.getUserDecryptUuid());
        if(user==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("존재하지 않는 사용자입니다."));
        }
        if(!(user.getStatus().equals(EStatusFlag.ACTIVE) || user.getStatus().equals(EStatusFlag.WAIT))){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s>는 활성된 상태가 아니거나 가입되어 있지 않는 사용자입니다.", user.getNickName()));
        }
        UserAccount account = userAccountRepository.findByUserId(user.getId());
        UserToken userToken = userWebTokenRepository.findByAccessToken(tokenUtil.resolveToken(Authorization));
        UserClientDetail userClientDetail = userToken.getClient();
        if(account==null){
            account = UserAccount.newInstance(user);
            userAccountRepository.save(account);
            if(request.getAgreeList()!=null && !request.getAgreeList().isEmpty()){
                userService.insertUserHasTerm(request.getAgreeList(), user);
            } else {
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "필수 약관은 선택해 주셔야 됩니다.");
            }
            if(request.getCategoryList()!=null && !request.getCategoryList().isEmpty()){
                List<Category> categoryList = categoryRepository.findAllByInId(request.getCategoryList());
                for(Category category: categoryList){
                    UserCategory userCategory = UserCategory.newInstance(user, category);
                    userCategoryRepository.save(userCategory);
                }
            }
            if(request.getSelectionList()!=null && !request.getSelectionList().isEmpty()){
                for(TermSelectionType type: request.getSelectionList()){
                    userService.insertUserHasTerm(type, user);
                }
            }
            user.updateSign();
        }

        Authentication authentication = Authentication.of(user.getUuid(), user.getId(), Constants.TOKEN_ISSUER);
        TokenResponse token =  tokenUtil.generateToken(authentication, userClientDetail.getAccessTokenValidity(), userClientDetail.getRefreshTokenValidity(), true, tokenUtil.getSocial(Authorization));
        LocalDateTime refreshExpireDate = LocalDateTimeUtils.epochMillToLocalDateTime(token.getRefreshTokenExpiresIn());
        userToken.updateToken(token.getAccessToken(), token.getRefreshToken(), refreshExpireDate);
        return token;
    }

    @Transactional(readOnly = true)
    public TokenResponse getToken(Long id){
        Authentication authentication = null;
        if(id.equals(30L)){
            authentication = Authentication.of("nonMember", null, Constants.TOKEN_ISSUER);
        } else {
            User resultUser = userRepository.findByIdAndStatusActive(id);
            authentication = Authentication.of(resultUser.getUuid(), resultUser.getId() ,Constants.TOKEN_ISSUER);
        }
        return tokenUtil.generateToken(authentication,9947483647000L , 9947483647000L, false, ESocialFlag.APPLE.getKey());
    }
    @Transactional
    public void logout(String Authorization){
        String accessToken = tokenUtil.resolveToken(Authorization);
        UserToken userToken = userWebTokenRepository.findByAccessToken(accessToken);
        /*
        ToDO: 추후 수정
         */
        if(userToken==null){
            return;
        }
        userWebTokenRepository.delete(userToken);
    }

    @Transactional
    public TokenResponse refresh(String Authorization, TokenRefreshRequest request){
        UserToken userToken = userWebTokenRepository.findByRefreshToken(request.getRefreshToken());
        if(userToken ==null || userToken.getExpireDate().isBefore(LocalDateTime.now())){
            throw new InternalRuleException(ErrorCode.E499_INTERNAL_RULE, ErrorAction.TOAST, "세션이 만료되어 로그아웃됩니다.");
        }
        User resultUser = userRepository.findByIdAndStatusActive(userToken.getUser().getId());
        if(resultUser==null){
            throw new InternalRuleException(ErrorCode.E499_INTERNAL_RULE, ErrorAction.TOAST, "세션이 만료되어 강제 로그아웃됩니다.");
        }
        UserClientDetail resultClientDetail = userClientDetailRepository.findByClientId(userToken.getClient().getId());
        Authentication authentication = Authentication.of(resultUser.getUuid(), resultUser.getId() , Constants.TOKEN_ISSUER);
        TokenResponse token = tokenUtil.generateToken(authentication, resultClientDetail.getAccessTokenValidity(), resultClientDetail.getRefreshTokenValidity(), true,  tokenUtil.getSocial(Authorization));
        LocalDateTime refreshExpireDate = LocalDateTimeUtils.epochMillToLocalDateTime(token.getRefreshTokenExpiresIn());
        userToken.updateToken(token.getAccessToken(), token.getRefreshToken(), refreshExpireDate);
        return token;
    }
}

