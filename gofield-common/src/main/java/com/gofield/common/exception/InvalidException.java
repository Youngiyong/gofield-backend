package com.gofield.common.exception;

import com.gofield.common.model.ErrorCode;
import com.gofield.common.model.ErrorAction;

public class InvalidException extends GofieldCustomException {

    public InvalidException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public InvalidException(String message) {
        super(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, message);
    }

}
