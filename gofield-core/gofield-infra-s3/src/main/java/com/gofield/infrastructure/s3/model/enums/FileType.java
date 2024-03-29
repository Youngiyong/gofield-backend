package com.gofield.infrastructure.s3.model.enums;

import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.common.utils.DateTimeUtils;
import com.gofield.common.utils.FileUtils;
import com.gofield.common.utils.RandomUtils;
import lombok.Getter;

@Getter
public enum FileType {

    USER_IMAGE("유저 프로필 이미지", "user/", FileContentType.IMAGE),

    BANNER_IMAGE("배너 이미지", "banner/", FileContentType.IMAGE),
    STORE_IMAGE("매장 이미지", "store/", FileContentType.IMAGE),
    ITEM_IMAGE("상품 이미지", "item/", FileContentType.IMAGE),
    ITEM_CONTENT_IMAGE("상품 컨텐츠 이미지", "content/", FileContentType.IMAGE),
    BRAND_IMAGE("브랜드 이미지", "brand/", FileContentType.IMAGE),
    ITEM_BUNDLE_IMAGE("묶음상품 이미지", "bundle/", FileContentType.IMAGE),
    ITEM_BUNDLE_REVIEW_IMAGE("리뷰 이미지", "review/", FileContentType.IMAGE),
    ;
    private final String description;
    private final String directory;
    private final FileContentType contentType;

    FileType(String description, String directory, FileContentType contentType) {
        this.description = description;
        this.directory = directory;
        this.contentType = contentType;
    }

    public void validateAvailableContentType(String contentType) {
        this.contentType.validateAvailableContentType(contentType);
    }

    public String makeDirectory(String folder){
        return folder + "/" +DateTimeUtils.getTodayString() + "/";
    }

    /**
     * 파일의 기존의 확장자를 유지하면서 유니크한 파일의 이름을 반환합니다.
     */
    public String createUniqueFileNameWithExtension(String originalFileName) {
        if (originalFileName == null) {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "파일의 이름이 null 이어서는 안됩니다");
        }
        String extension = FileUtils.getFileExtension(originalFileName);
        return getFileNameWithDirectory(RandomUtils.makeRandomUuid() + extension);
    }

    public String getFileNameWithDirectory(String fileName) {
        return this.directory + DateTimeUtils.getTodayString() + "/" +  fileName;
    }

    public String getKey() {
        return name();
    }

}
