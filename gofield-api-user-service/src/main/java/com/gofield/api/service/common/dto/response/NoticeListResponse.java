package com.gofield.api.service.common.dto.response;

import com.gofield.domain.rds.domain.common.PaginationResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoticeListResponse {

    private List<NoticeResponse> list;
    private PaginationResponse page;

    @Builder
    public NoticeListResponse(List<NoticeResponse> list, PaginationResponse page) {
        this.list = list;
        this.page = page;
    }

    public static NoticeListResponse of(List<NoticeResponse> list, PaginationResponse page){
        return NoticeListResponse.builder()
                .list(list)
                .page(page)
                .build();
    }

}
