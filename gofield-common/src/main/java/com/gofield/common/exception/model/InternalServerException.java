package com.gofield.common.exception.model;

import com.gofield.common.exception.type.ErrorCode;
import com.gofield.common.exception.type.ErrorAction;

public class InternalServerException extends GofieldCustomException {
    public InternalServerException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public InternalServerException(String message) {
        super(ErrorCode.INTERNAL_SERVER_EXCEPTION, ErrorAction.NONE, message);
    }
}
