package com.gofield.common.exception;

import com.gofield.common.model.ErrorCode;
import com.gofield.common.model.ErrorAction;

public class UnAuthorizedException extends GofieldCustomException {

    public UnAuthorizedException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public UnAuthorizedException(String message) {
        super(ErrorCode.E401_UNAUTHORIZED, ErrorAction.NONE, message);
    }

}
