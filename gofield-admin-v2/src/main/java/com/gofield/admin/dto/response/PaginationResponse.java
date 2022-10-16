package com.gofield.admin.dto.response;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationResponse<T> {

    private Page<T> data;
    private int totalPage;
    private List<Integer> pageNumber;

    private PaginationResponse(Page<T> page){
        this.data = page;
        this.totalPage = page.getTotalPages();
        this.pageNumber = IntStream.rangeClosed(1, totalPage)
                .boxed()
                .collect(Collectors.toList());
    }

    public static PaginationResponse of(Page page){
        return new PaginationResponse(page);
    }


}