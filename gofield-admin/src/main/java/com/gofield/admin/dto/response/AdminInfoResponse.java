package com.gofield.admin.dto.response;

import com.gofield.admin.dto.response.projection.AdminInfoProjectionResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class AdminInfoResponse {

    private List<AdminInfoProjectionResponse> list;
    private int totalElements;

    @Builder
    private AdminInfoResponse(List<AdminInfoProjectionResponse> list, int totalElements){
        this.list = list;
        this.totalElements = totalElements;
    }

    public static AdminInfoResponse of(List<AdminInfoProjectionResponse> list, int totalElements){
        return AdminInfoResponse.builder()
                .list(list)
                .totalElements(totalElements)
                .build();
    }

}
