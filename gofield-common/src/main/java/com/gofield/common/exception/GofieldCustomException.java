package com.gofield.common.exception;

import com.gofield.common.model.ErrorCode;
import com.gofield.common.model.ErrorAction;

public abstract class GofieldCustomException extends RuntimeException {

    private final ErrorCode errorCode;
    private final ErrorAction action;

    public GofieldCustomException(ErrorCode errorCode, ErrorAction action, String message) {
        super(message);
        this.errorCode = errorCode;
        this.action = action;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public ErrorAction getAction() { return action; }

}
