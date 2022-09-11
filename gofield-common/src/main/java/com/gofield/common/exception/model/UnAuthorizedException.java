package com.gofield.common.exception.model;

import com.gofield.common.exception.type.ErrorCode;
import com.gofield.common.exception.type.ErrorAction;

public class UnAuthorizedException extends GofieldCustomException {

    public UnAuthorizedException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public UnAuthorizedException(ErrorAction action, String message) {
        super(ErrorCode.UNAUTHORIZED_EXCEPTION, action, message);
    }

}
