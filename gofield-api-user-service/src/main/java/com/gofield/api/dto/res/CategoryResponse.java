package com.gofield.api.dto.res;

import com.gofield.api.util.ApiUtil;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.Category;
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

    private String thumbnail;

    @Builder
    private CategoryResponse(Long id, String name, String thumbnail){
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public static CategoryResponse of(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .thumbnail(CommonUtils.makeCloudFrontUrl(category.getThumbnail()))
                .build();
    }

    public static List<CategoryResponse> of(List<Category> list){
        return list
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }
}
