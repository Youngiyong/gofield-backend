package com.gofield.common.exception;

import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.model.enums.ErrorAction;

public class InternalServerException extends GofieldCustomException {
    public InternalServerException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public InternalServerException(String message) {
        super(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, message);
    }
}
