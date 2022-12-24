package com.gofield.domain.rds.domain.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
public class PaginationResponse {

    private int page;
    private int size;
    private int totalPages;
    private long totalElements;

    @Builder
    private PaginationResponse(int page, int size, int totalPages, long totalElements){
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public static PaginationResponse of(Page page){
        return PaginationResponse.builder()
                .page(page.getNumber()+1)
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();

    }
}
