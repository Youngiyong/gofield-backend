package com.gofield.api.config.resolver;

import com.gofield.common.model.Constants;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class UserIdResolver {

    public static Long getUserId(){
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object accountId = servletRequest.getAttribute(Constants.USER_ID);
        if (accountId == null) {
            return null;
        }
        return (Long) accountId;
    }

}
