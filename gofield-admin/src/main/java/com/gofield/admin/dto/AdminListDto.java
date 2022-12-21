package com.gofield.admin.dto;

import com.gofield.admin.dto.response.projection.AdminInfoProjectionResponse;
import com.gofield.domain.rds.domain.admin.projection.AdminInfoProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@NoArgsConstructor
public class AdminListDto {

    private List<AdminInfoProjectionResponse> list;
    private Page<AdminInfoProjection> page;

    private String keyword;

    @Builder
    private AdminListDto(List<AdminInfoProjectionResponse> list, Page<AdminInfoProjection> page, String keyword){
        this.list = list;
        this.page = page;
        this.keyword = keyword;
    }

    public static AdminListDto of(List<AdminInfoProjectionResponse> list, Page<AdminInfoProjection> page, String keyword){
        return AdminListDto.builder()
                .list(list)
                .page(page)
                .keyword(keyword)
                .build();
    }

}
