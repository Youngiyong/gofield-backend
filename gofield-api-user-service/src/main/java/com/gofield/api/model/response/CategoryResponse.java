package com.gofield.api.model.response;

import com.gofield.domain.rds.entity.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;

    @Builder
    private CategoryResponse(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public static CategoryResponse of(Long id, String name){
        return CategoryResponse.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static List<CategoryResponse> of(List<Category> list){
        return list
                .stream()
                .map(p -> CategoryResponse.of(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }
}
