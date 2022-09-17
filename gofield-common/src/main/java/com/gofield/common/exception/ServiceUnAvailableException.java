package com.gofield.common.exception;

import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;

public class ServiceUnAvailableException extends GofieldCustomException {
    public ServiceUnAvailableException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public ServiceUnAvailableException(String message) {
        super(ErrorCode.E503_SERVICE_UNAVAILABLE, ErrorAction.NONE, message);
    }

}
