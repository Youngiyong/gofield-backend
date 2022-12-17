package com.gofield.common.exception;

import com.gofield.common.model.ErrorCode;
import com.gofield.common.model.ErrorAction;

public class NotFoundException extends GofieldCustomException {

    public NotFoundException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public NotFoundException(String message) {
        super(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.NONE, message);
    }

}
