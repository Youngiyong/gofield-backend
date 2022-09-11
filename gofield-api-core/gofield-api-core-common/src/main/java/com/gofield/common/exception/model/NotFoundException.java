package com.gofield.common.exception.model;

import com.gofield.common.exception.type.ErrorCode;
import com.gofield.common.exception.type.ErrorAction;

public class NotFoundException extends GofieldCustomException {

    public NotFoundException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public NotFoundException(String message) {
        super(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.NONE, message);
    }

}
