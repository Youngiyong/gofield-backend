package com.gofield.common.utils;

import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    public static String getFileExtension(String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf("."));
            if (extension.length() < 2) {
                throw new InvalidException(ErrorCode.E400_INVALID_UPLOAD_FILE_EXTENSION, ErrorAction.TOAST, String.format("잘못된 확장자 형식의 파일 (%s) 입니다", fileName));
            }
            return extension;
        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidException(ErrorCode.E400_INVALID_UPLOAD_FILE_EXTENSION, ErrorAction.TOAST, String.format("잘못된 확장자 형식의 파일 (%s) 입니다", fileName));
        }
    }

}
