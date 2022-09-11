package com.gofield.common.exception.model;

import com.gofield.common.type.ErrorCode;
import com.gofield.common.type.ErrorAction;

public class InternalRuleException extends RuntimeException {

    private ErrorCode errorCode;

    private ErrorAction action;

    public InternalRuleException(ErrorCode errorCode, ErrorAction action, String message) {
        super(message);
        this.errorCode = errorCode;
        this.action = action;
    }


    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public ErrorAction getAction() { return action; }

}
