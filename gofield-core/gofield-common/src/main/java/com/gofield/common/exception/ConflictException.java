package com.gofield.common.exception;


import com.gofield.common.model.ErrorCode;
import com.gofield.common.model.ErrorAction;

public class ConflictException extends GofieldBaseException {

    public ConflictException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public ConflictException(String message) {
        super( ErrorCode.E409_CONFLICT_EXCEPTION, ErrorAction.NONE, message);
    }

}
