package com.gofield.api.service.user.dto.response;

import com.gofield.domain.rds.domain.search.SearchLog;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UserSearchResponse {
    private Long id;
    private String keyword;

    @Builder
    private UserSearchResponse(Long id, String keyword){
        this.id = id;
        this.keyword = keyword;
    }

    public static UserSearchResponse of(Long id, String keyword){
        return UserSearchResponse.builder()
                .id(id)
                .keyword(keyword)
                .build();
    }

    public static List<UserSearchResponse> of(List<SearchLog> list){
        return list
                .stream()
                .map(p -> UserSearchResponse.of(p.getId(), p.getKeyword()))
                .collect(Collectors.toList());
    }
}
