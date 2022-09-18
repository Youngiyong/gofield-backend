package com.gofield.infrastructure.s3.model.enums;

import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import lombok.Getter;

@Getter
public enum FileContentType {

    IMAGE("image"),
    ;

    private static final String SEPARATOR = "/";
    private final String prefix;

    FileContentType(String prefix) {
        this.prefix = prefix;
    }

    private static String getContentTypePrefix(String contentType) {
        return contentType.split(SEPARATOR)[0];
    }

    public void validateAvailableContentType(String contentType) {
        if (contentType != null && contentType.contains(SEPARATOR) && prefix.equals(getContentTypePrefix(contentType))) {
            return;
        }
        throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("허용되지 않은 ContentType (%s) 입니다", contentType));
    }

}
