package com.gofield.common.exception.model;

import com.gofield.common.exception.type.ErrorCode;
import com.gofield.common.exception.type.ErrorAction;

public class BadGatewayException extends GofieldCustomException {

    public BadGatewayException(String message) {
        super(ErrorCode.BAD_GATEWAY_EXCEPTION, ErrorAction.NONE, message);
    }

    public BadGatewayException(ErrorCode errorCode, ErrorAction action, String message){
        super(errorCode, action, message);
    }
}
