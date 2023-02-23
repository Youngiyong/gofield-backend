package com.gofield.common.exception;

import com.gofield.common.model.ErrorCode;
import com.gofield.common.model.ErrorAction;

public class ForbiddenException extends GofieldBaseException {

    public ForbiddenException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public ForbiddenException(String message) {
        super(ErrorCode.E403_FORBIDDEN_EXCEPTION, ErrorAction.NONE, message);
    }

}
