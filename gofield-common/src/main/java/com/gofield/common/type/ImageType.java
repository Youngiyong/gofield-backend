package com.gofield.common.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ImageType {

    USER_PROFILE("user"),
    PARTNER_PROFILE("partner"),
    STORE_IMAGE("store");
    private final String directory;
    public String getFileNameWithDirectory(String fileName, Long id) {

        if(id == null){
            return this.directory.concat("/").concat(fileName);
        }

        return this.directory.concat("/").concat(id + "/").concat(fileName);
    }

}
