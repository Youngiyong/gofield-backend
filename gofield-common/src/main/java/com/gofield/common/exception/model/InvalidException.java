package com.gofield.common.exception.model;

import com.gofield.common.exception.type.ErrorCode;
import com.gofield.common.exception.type.ErrorAction;

public class InvalidException extends GofieldCustomException {

    public InvalidException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public InvalidException(String message) {
        super(ErrorCode.VALIDATION_EXCEPTION, ErrorAction.NONE, message);
    }

}
