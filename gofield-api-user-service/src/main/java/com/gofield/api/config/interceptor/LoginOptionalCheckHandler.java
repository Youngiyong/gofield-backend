package com.gofield.api.config.interceptor;

import com.gofield.api.util.TokenUtil;
import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class LoginOptionalCheckHandler {

    private final TokenUtil tokenUtil;
    private final UserRepository userRepository;

    public Long getUserIdOptional(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(authorization)) {
            return null;
        }
        String uuid = tokenUtil.getUserUuid(request);
        if(uuid==null){
            return null;
        }
        Long userId = userRepository.findUserIdByUuid(uuid);
        if (userId==null) {
            return null;
        }
        return userId;
    }
}
