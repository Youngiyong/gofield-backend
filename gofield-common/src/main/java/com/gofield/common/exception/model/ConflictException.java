package com.gofield.common.exception.model;


import com.gofield.common.exception.type.ErrorCode;
import com.gofield.common.exception.type.ErrorAction;

public class ConflictException extends GofieldCustomException {

    public ConflictException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public ConflictException(String message) {
        super( ErrorCode.E409_CONFLICT_EXCEPTION, ErrorAction.NONE, message);
    }

}
