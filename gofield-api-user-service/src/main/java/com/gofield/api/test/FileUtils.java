//package com.gofield.api.test;
//
//import com.gofield.common.exception.model.InvalidException;
//import com.gofield.common.exception.type.ErrorAction;
//import com.gofield.common.exception.type.ErrorCode;
//import com.gofield.common.utils.type.ImageType;
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//public class FileUtils {
//
//    private static final List<String> imageContentTypes = Arrays.asList("image/png", "image/jpeg");
//
//    public static String createFileUuidNameWithExtension(ImageType type, String originalFileName) {
//        String extension = getFileExtension(originalFileName);
//        return type.getFileNameWithDirectory(UUID.randomUUID().toString().concat(extension));
//    }
//    private static String getFileExtension(String fileName) {
//        try {
//            return fileName.substring(fileName.lastIndexOf("."));
//        } catch (StringIndexOutOfBoundsException e) {
//            throw new InvalidException(ErrorCode.E400_INVALID_FILE_FORMAT_EXCEPTION, ErrorAction.TOAST, String.format("잘못된 형식의 파일 (%s) 입니다", fileName));
//        }
//    }
//
//    public static void validateImageFile(String contentType) {
//        if (!imageContentTypes.contains(contentType)) {
//            throw new InvalidException(ErrorCode.E400_INVALID_FILE_FORMAT_EXCEPTION, ErrorAction.TOAST, String.format("허용되지 않은 파일 형식 (%s) 입니다", contentType));
//        }
//    }
//
//}
