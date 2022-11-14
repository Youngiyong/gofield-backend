package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CountResponse {
    private int totalCount;

    @Builder
    private CountResponse(int totalCount){
        this.totalCount = totalCount;
    }

    public static CountResponse of(int totalCount){
        return CountResponse.builder()
                .totalCount(totalCount)
                .build();
    }
}
