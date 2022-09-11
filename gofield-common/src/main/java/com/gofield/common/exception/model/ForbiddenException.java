package com.gofield.common.exception.model;

import com.gofield.common.exception.type.ErrorCode;
import com.gofield.common.exception.type.ErrorAction;

public class ForbiddenException extends GofieldCustomException {

    public ForbiddenException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public ForbiddenException(String message) {
        super(ErrorCode.E403_FORBIDDEN_EXCEPTION, ErrorAction.NONE, message);
    }

}
