package com.gofield.api.config.resolver;

import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUuidResolver {

    private UserUuidResolver() { }

    public static String getCurrentUserUuid() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw  new InvalidException(ErrorCode.E499_INTERNAL_RULE, ErrorAction.NONE, "UnSupported Authentication");
        }

        return authentication.getName();
    }

}