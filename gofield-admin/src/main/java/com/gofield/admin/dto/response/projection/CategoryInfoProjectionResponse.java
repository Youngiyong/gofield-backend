package com.gofield.admin.dto.response.projection;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.item.Brand;
import com.gofield.domain.rds.domain.item.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CategoryInfoProjectionResponse {
    private Long id;
    private String name;
    private String isActive;

    private String isAttention;
    private String createDate;


    @Builder
    private CategoryInfoProjectionResponse(Long id, String name, String isActive, String isAttention, String createDate){
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.isAttention = isAttention;
        this.createDate = createDate;
    }

    public static CategoryInfoProjectionResponse of(Long id, String name, String isActive, String isAttention, String createDate){
        return CategoryInfoProjectionResponse.builder()
                .id(id)
                .name(name)
                .isActive(isActive)
                .isAttention(isAttention)
                .createDate(createDate)
                .build();
    }

    public static List<CategoryInfoProjectionResponse> of(List<Category> list){
        return list.stream()
                .map(p -> CategoryInfoProjectionResponse.of(p.getId(), p.getName(), p.getIsActive() ? "활성" : "비활성", p.getIsAttention() ? "활성" : "비활성", p.getCreateDate().toLocalDate().toString()))
                .collect(Collectors.toList());
    }
}
