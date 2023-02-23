package com.gofield.api.config.interceptor;

import com.gofield.common.model.Constants;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final LoginCheckHandler loginCheckHandler;
    private final LoginOptionalCheckHandler loginOptionalCheckHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(handler instanceof HandlerMethod == false) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
        if (auth == null) {
            return true;
        }

        if(auth.allowNonLogin()){
            Long userIdNullable = loginOptionalCheckHandler.getUserIdOptional(request);
            request.setAttribute(Constants.USER_ID, userIdNullable);
            return true;
        }

        Long userId = loginCheckHandler.getUserId(request);
        request.setAttribute(Constants.USER_ID, userId);
        return true;
    }

}
