package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonCodeResponse {

    private String code;

    @Builder
    private CommonCodeResponse(String code){
        this.code = code;
    }

    public static CommonCodeResponse of(String code){
        return CommonCodeResponse.builder()
                .code(code)
                .build();
    }
}
