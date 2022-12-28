package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.search.SearchLog;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RecentKeywordResponse {
    private Long id;
    private String keyword;

    @Builder
    private RecentKeywordResponse(Long id, String keyword){
        this.id = id;
        this.keyword = keyword;
    }

    public static RecentKeywordResponse of(SearchLog searchLog){
        return RecentKeywordResponse.builder()
                .id(searchLog.getId())
                .keyword(searchLog.getKeyword())
                .build();
    }

    public static List<RecentKeywordResponse> of(List<SearchLog> list){
        return list
                .stream()
                .map(RecentKeywordResponse::of)
                .collect(Collectors.toList());
    }
}
