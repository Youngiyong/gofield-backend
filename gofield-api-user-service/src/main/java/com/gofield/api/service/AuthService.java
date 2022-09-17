package com.gofield.api.service;


import com.gofield.api.model.AppleTokenPayload;
import com.gofield.api.model.Authentication;
import com.gofield.api.model.request.LoginRequest;
import com.gofield.api.model.request.SignupRequest;
import com.gofield.api.model.request.TokenRefreshRequest;
import com.gofield.api.model.response.ClientResponse;
import com.gofield.api.model.response.LoginResponse;
import com.gofield.api.model.response.TokenResponse;
import com.gofield.api.util.ApiUtil;
import com.gofield.api.util.TokenUtil;
import com.gofield.common.exception.InternalRuleException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.entity.category.Category;
import com.gofield.domain.rds.entity.category.CategoryRepository;
import com.gofield.domain.rds.entity.device.Device;
import com.gofield.domain.rds.entity.device.DeviceRepository;
import com.gofield.domain.rds.entity.deviceModel.DeviceModel;
import com.gofield.domain.rds.entity.deviceModel.DeviceModelRepository;
import com.gofield.domain.rds.entity.term.Term;
import com.gofield.domain.rds.entity.term.TermRepository;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.entity.user.UserRepository;
import com.gofield.domain.rds.entity.userAccessLog.UserAccessLog;
import com.gofield.domain.rds.entity.userAccessLog.UserAccessLogRepository;
import com.gofield.domain.rds.entity.userAccount.UserAccount;
import com.gofield.domain.rds.entity.userAccount.UserAccountRepository;
import com.gofield.domain.rds.entity.userClientDetail.UserClientDetail;
import com.gofield.domain.rds.entity.userClientDetail.UserClientDetailRepository;
import com.gofield.domain.rds.entity.userHasCategory.UserHasCategory;
import com.gofield.domain.rds.entity.userHasCategory.UserHasCategoryRepository;
import com.gofield.domain.rds.entity.userHasDevice.UserHasDevice;
import com.gofield.domain.rds.entity.userHasDevice.UserHasDeviceRepository;
import com.gofield.domain.rds.entity.userHasTerm.UserHasTerm;
import com.gofield.domain.rds.entity.userHasTerm.UserHasTermRepository;
import com.gofield.domain.rds.entity.userSns.UserSns;
import com.gofield.domain.rds.entity.userSns.UserSnsRepository;
import com.gofield.domain.rds.entity.userToken.UserToken;
import com.gofield.domain.rds.entity.userToken.UserTokenRepository;
import com.gofield.domain.rds.enums.ESocialFlag;
import com.gofield.domain.rds.enums.EStatusFlag;
import com.gofield.infrastructure.external.api.apple.AppleTokenDecoderImpl;
import com.gofield.infrastructure.external.api.kakao.KaKaoAuthApiClient;
import com.gofield.infrastructure.external.api.kakao.dto.response.KaKaoProfileResponse;
import com.gofield.infrastructure.external.api.naver.NaverAuthApiClient;
import com.gofield.infrastructure.external.api.naver.dto.response.NaverProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenUtil tokenUtil;
    private final UserService userService;

    private final TermRepository termRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final UserSnsRepository userSnsRepository;
    private final CategoryRepository categoryRepository;
    private final UserTokenRepository userTokenRepository;
    private final DeviceModelRepository deviceModelRepository;
    private final UserHasTermRepository userHasTermRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserAccessLogRepository userAccessLogRepository;
    private final UserHasDeviceRepository userHasDeviceRepository;
    private final UserHasCategoryRepository userHasCategoryRepository;
    private final UserClientDetailRepository userClientDetailRepository;


    private final NaverAuthApiClient naverAuthApiClient;
    private final KaKaoAuthApiClient kaKaoAuthApiClient;
    private final AppleTokenDecoderImpl appleTokenDecoder;


    @Transactional
    public LoginResponse login(LoginRequest request, String secret){
        String email = null;
        String uniqueId = null;
        String nickName = null;
        String deviceName = null;
        Boolean isFirst = false;
        DeviceModel device = null;

        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String version = servletRequest.getHeader("version");
        String platform = servletRequest.getHeader("platform");
        String osVersion = servletRequest.getHeader("os-version");
        String deviceModel = servletRequest.getHeader("device-model");
        String ipAddress = ApiUtil.getIpAddress(servletRequest);
        String userAgent = servletRequest.getHeader("User-Agent");

        if(deviceModel!=null){
            device = deviceModelRepository.findByModel(deviceModel);
        }
        if (device != null) {
            String brand = device.getBrand() == null ? "" : device.getBrand();
            String name = device.getName() == null ? "" : " " + device.getName();
            deviceName = brand + name;
        } else {
            deviceName = deviceModel;
        }

        ClientResponse clientDetail = ApiUtil.base64EncodingStrToDecodeClientDetail(secret);
        UserClientDetail resultClientDetail = userClientDetailRepository.findByClientId(clientDetail.getClientId());
        if(resultClientDetail==null){
            throw new NotFoundException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "유효하지 않는 클라이언트 아이디입니다.");
        }

        if(request.getSocial().equals(ESocialFlag.KAKAO.KAKAO)){
            KaKaoProfileResponse profile = kaKaoAuthApiClient.getProfileInfo(request.getToken());
            uniqueId = profile.getId();
            email = profile.getEmail();
            nickName = profile.getNickName();
        } else if(request.getSocial().equals(ESocialFlag.APPLE)){
            uniqueId = appleTokenDecoder.getSocialIdFromAppleIdToken(request.getToken());
        } else if(request.getSocial().equals(ESocialFlag.NAVER)){
            NaverProfileResponse profile = naverAuthApiClient.getProfileInfo(request.getToken());
            uniqueId = profile.getResponse().getId();
        } else {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "지원하지 않는 로그인타입니다.");
        }

        UserSns userSns = userSnsRepository.findByUniQueIdAndRoute(uniqueId, request.getSocial());
        if(userSns==null){
            User saveUser = User.newInstance(RandomUtils.makeRandomUuid(), nickName==null ? "신규고객" : nickName);
            userRepository.save(saveUser);
            UserSns saveSns = UserSns.newInstance(saveUser, uniqueId, ESocialFlag.KAKAO);
            userSnsRepository.save(saveSns);
            userSns = saveSns;
        }
        UserAccount userAccount = userAccountRepository.findByUserId(userSns.getUser().getId());
        if(userAccount==null){
            isFirst = true;
        }
        Authentication authentication = new Authentication(userSns.getUser().getUuid(), userSns.getUser().getId() ,"www.gofield.shop");
        TokenResponse token = tokenUtil.generateToken(authentication, resultClientDetail.getAccessTokenValidity(), resultClientDetail.getRefreshTokenValidity());
        LocalDateTime refreshExpireDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(token.getRefreshTokenExpiresIn()), ZoneId.systemDefault());
        UserToken saveToken = UserToken.newInstance(resultClientDetail.getId(),
                userSns.getUser().getId(),
                token.getRefreshToken(),
                refreshExpireDate);
        userTokenRepository.save(saveToken);

        Device resultDevice = deviceRepository.findByDeviceKey(request.getDeviceKey());
        if(resultDevice==null){
            Device saveDevice = Device.newInstance(request.getDeviceKey(), version, deviceName, platform, osVersion, ipAddress);
            deviceRepository.save(saveDevice);
            resultDevice=saveDevice;
        }

        UserHasDevice resultUserHasDevice = userHasDeviceRepository.findByUserIdAndDeviceId(userSns.getUser().getId(), resultDevice.getId());
        if(resultUserHasDevice==null){
            UserHasDevice userHasDevice = UserHasDevice.newInstance(userSns.getUser(), resultDevice);
            userHasDeviceRepository.save(userHasDevice);
        }

        UserAccessLog saveLog = UserAccessLog.newInstance(userSns.getUser().getId(), resultDevice.getId(), userAgent, ipAddress);
        userAccessLogRepository.save(saveLog);

        return LoginResponse.of(isFirst, token.getGrantType(), token.getAccessToken(), token.getRefreshToken(), token.getAccessTokenExpiresIn(), token.getRefreshTokenExpiresIn());
    }

    @Transactional
    public void signup(SignupRequest request){
        if(request.getAgreeList()==null || request.getAgreeList().isEmpty()){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "필수 약관은 선택해 주셔야 됩니다.");
        }
        User user = userRepository.findByUuid(userService.getUserDecryptUuid());
        if(user==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "존재하지 않는 사용자입니다.");
        }
        if(user.getStatus().equals(EStatusFlag.ACTIVE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 가입되어 있는 사용자입니다.");
        } else if(!user.getStatus().equals(EStatusFlag.WAIT))
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "활성되었거나 대기 상태인 사용자가 아닙니다.");
        UserAccount account = userAccountRepository.findByUserId(user.getId());
        if(account!=null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 가입되어 있는 사용자입니다.");
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

    @Transactional
    public TokenResponse getToken(Long id){
        User resultUser = userRepository.findByIdAndStatusActive(id);
        UserClientDetail resultClientDetail = userClientDetailRepository.findByClientId(21L);
        Authentication authentication = new Authentication(resultUser.getUuid(), resultUser.getId() ,"www.gofield.co.kr");
        return tokenUtil.generateToken(authentication, resultClientDetail.getAccessTokenValidity(), resultClientDetail.getRefreshTokenValidity());
    }

    @Transactional
    public void logout(){
        User user = userRepository.findByUuid(userService.getUserDecryptUuid());
        List<UserToken> userTokenList = userTokenRepository.findByUserId(user.getId());
        if(userTokenList.isEmpty()){
            throw new InternalRuleException(ErrorCode.E499_INTERNAL_RULE, ErrorAction.TOAST, "이미 로그아웃 처리된 사용자입니다.");
        }
        List<Long> idList = userTokenList
                .stream()
                .map(p->p.getUserId())
                .collect(Collectors.toList());
        userTokenRepository.delete(idList);
    }
    @Transactional
    public TokenResponse refresh(TokenRefreshRequest request){
        UserToken userToken = userTokenRepository.findByRefreshToken(request.getRefreshToken());
        if(userToken==null || userToken.getExpireDate().isBefore(LocalDateTime.now())){
            throw new InternalRuleException(ErrorCode.E499_INTERNAL_RULE, ErrorAction.TOAST, "유효하지 않는 리프레쉬 토큰입니다.");
        }
        User resultUser = userRepository.findByIdAndStatusActive(userToken.getUserId());
        UserClientDetail resultClientDetail = userClientDetailRepository.findByClientId(userToken.getClientId());
        Authentication authentication = new Authentication(resultUser.getUuid(), resultUser.getId() ,"www.gofield.co.kr");
        TokenResponse token = tokenUtil.generateToken(authentication, resultClientDetail.getAccessTokenValidity(), resultClientDetail.getRefreshTokenValidity());
        LocalDateTime refreshExpireDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(token.getRefreshTokenExpiresIn()), ZoneId.systemDefault());
        userToken.updateToken(token.getRefreshToken(), refreshExpireDate);

        return token;
    }
}
