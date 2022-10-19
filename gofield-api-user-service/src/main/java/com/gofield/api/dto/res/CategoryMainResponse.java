package com.gofield.api.dto.res;

import com.gofield.domain.rds.entity.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CategoryMainResponse {

    private Long id;
    private String name;

    private String thumbnail;

    @Builder
    private CategoryMainResponse(Long id, String name, String thumbnail){
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public static CategoryMainResponse of(Long id, String name, String thumbnail){
        return CategoryMainResponse.builder()
                .id(id)
                .name(name)
                .thumbnail(thumbnail)
                .build();
    }

    public static List<CategoryMainResponse> of(List<Category> list){
        return list
                .stream()
                .map(p -> CategoryMainResponse.of(p.getId(), p.getName(), p.getThumbnail()))
                .collect(Collectors.toList());
    }
}
