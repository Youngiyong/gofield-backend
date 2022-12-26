package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.search.PopularKeyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PopularKeywordResponse {
    private Long id;
    private String keyword;

    @Builder
    private PopularKeywordResponse(Long id, String keyword){
        this.id = id;
        this.keyword = keyword;
    }

    public static PopularKeywordResponse of(PopularKeyword popularKeyword){
        return PopularKeywordResponse.builder()
                .id(popularKeyword.getId())
                .keyword(popularKeyword.getKeyword())
                .build();
    }

    public static List<PopularKeywordResponse> of(List<PopularKeyword> list){
        return list
                .stream()
                .map(PopularKeywordResponse::of)
                .collect(Collectors.toList());
    }
}
