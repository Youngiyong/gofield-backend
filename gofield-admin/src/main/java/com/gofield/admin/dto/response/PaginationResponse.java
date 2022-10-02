package com.gofield.admin.dto.response;

import lombok.*;
import org.springframework.data.domain.Page;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationResponse<T> {
    private int size;
    private int currentPages;
    private int currentElements;
    private int totalPages;
    private Long totalElements;
    private Boolean last;
    private Boolean first;
    private Boolean empty;

    private PaginationResponse(Page<T> page){
        this.size = page.getSize();
        this.currentPages = page.getNumber()+1;
        this.currentElements = page.getNumberOfElements();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.last = page.isLast();
        this.first = page.isFirst();
        this.empty = page.isEmpty();
    }

    public static PaginationResponse of(Page page){
        return new PaginationResponse(page);
    }


}