package com.gofield.common.exception.model;

import com.gofield.common.exception.type.ErrorAction;
import com.gofield.common.exception.type.ErrorCode;

public class ServiceUnAvailableException extends GofieldCustomException {
    public ServiceUnAvailableException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public ServiceUnAvailableException(String message) {
        super(ErrorCode.E503_SERVICE_UNAVAILABLE, ErrorAction.NONE, message);
    }

}
