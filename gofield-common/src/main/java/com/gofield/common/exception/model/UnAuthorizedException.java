package com.gofield.common.exception.model;

import com.gofield.common.exception.type.ErrorCode;
import com.gofield.common.exception.type.ErrorAction;

public class UnAuthorizedException extends GofieldCustomException {

    public UnAuthorizedException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public UnAuthorizedException(String message) {
        super(ErrorCode.E401_UNAUTHORIZED, ErrorAction.NONE, message);
    }

}
