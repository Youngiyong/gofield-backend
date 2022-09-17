package com.gofield.common.exception;

import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.model.enums.ErrorAction;

public class BadGatewayException extends GofieldCustomException {

    public BadGatewayException(String message) {
        super(ErrorCode.E502_BAD_GATEWAY, ErrorAction.NONE, message);
    }

    public BadGatewayException(ErrorCode errorCode, ErrorAction action, String message){
        super(errorCode, action, message);
    }
}
