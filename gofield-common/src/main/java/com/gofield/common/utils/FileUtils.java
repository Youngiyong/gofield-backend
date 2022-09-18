package com.gofield.common.utils;

import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    /**
     * 파일의 확장자를 반환합니다.
     * 잘못된 파일의 확장자인경우 throws ValidationException
     *
     * @param fileName ex) image.png
     * @return ex) .png
     */
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
