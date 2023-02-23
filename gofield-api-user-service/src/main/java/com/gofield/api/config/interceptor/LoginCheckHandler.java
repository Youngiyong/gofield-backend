package com.gofield.api.config.interceptor;

import com.gofield.api.util.TokenUtil;
import com.gofield.common.exception.UnAuthorizedException;
import com.gofield.domain.rds.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class LoginCheckHandler {
    private final TokenUtil tokenUtil;
    private final UserRepository userRepository;

    public Long getUserId(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(authorization)) {
            throw new UnAuthorizedException("인증이 실패하였습니다 - Authorization 헤더가 존재하지 않습니다");
        }
        String uuid = tokenUtil.getUserUuid(request);
        if(uuid==null){
            throw new UnAuthorizedException("인증이 실패하였습니다 - 유저 아이디가 존재하지 않습니다");
        }
        Long userId = userRepository.findUserIdByUuid(uuid);
        if (userId==null) {
            throw new UnAuthorizedException(String.format("인증이 실패하였습니다 - uuid(%s)에 해당하는 유저는 회원 탈퇴되거나 유효하지 않은 유저입니다 ", uuid));
        }
        return userId;
    }

}
