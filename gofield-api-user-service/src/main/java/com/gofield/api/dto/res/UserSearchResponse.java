package com.gofield.api.dto.res;

import com.gofield.domain.rds.entity.userKeyword.UserKeyword;
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

    public static List<UserSearchResponse> of(List<UserKeyword> list){
        return list
                .stream()
                .map(p -> UserSearchResponse.of(p.getId(), p.getKeyword()))
                .collect(Collectors.toList());
    }
}
