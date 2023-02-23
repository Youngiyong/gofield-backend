package com.gofield.common.exception;

import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;

public class ServiceUnAvailableException extends GofieldBaseException {
    public ServiceUnAvailableException(ErrorCode errorCode, ErrorAction action, String message) {
        super(errorCode, action, message);
    }

    public ServiceUnAvailableException(String message) {
        super(ErrorCode.E503_SERVICE_UNAVAILABLE, ErrorAction.NONE, message);
    }

}
