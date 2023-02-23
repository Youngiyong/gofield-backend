package com.gofield.common.exception;

import com.gofield.common.model.ErrorCode;
import com.gofield.common.model.ErrorAction;

public class BadGatewayException extends GofieldBaseException {

    public BadGatewayException(String message) {
        super(ErrorCode.E502_BAD_GATEWAY, ErrorAction.NONE, message);
    }

    public BadGatewayException(ErrorCode errorCode, ErrorAction action, String message){
        super(errorCode, action, message);
    }
}
