package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.item.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private List<CategoryDto> categoryList;
    private Long parentId;
    private String parentName;
    private Boolean isActive;
    private Boolean isAttention;
    private String createDate;


    @Builder
    private CategoryDto(Long id, String name, List<CategoryDto> categoryList, Long parentId, String parentName,  Boolean isActive, Boolean isAttention, String createDate){
        this.id = id;
        this.name = name;
        this.categoryList = categoryList;
        this.parentId = parentId;
        this.parentName = parentName;
        this.isActive = isActive;
        this.isAttention = isAttention;
        this.createDate = createDate;
    }

    public static CategoryDto of(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParent() == null ? null : category.getParent().getId())
                .parentName(category.getParent() == null ? null : category.getParent().getName())
                .isActive(category.getIsActive())
                .isAttention(category.getIsAttention())
                .createDate(category.getCreateDate().toLocalDate().toString())
                .build();
    }

    public static CategoryDto of(Category category, List<CategoryDto> categoryList){
        if(category==null){
            return CategoryDto.builder()
                    .categoryList(categoryList)
                    .build();
        }

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParent() == null ? null : category.getParent().getId())
                .parentName(category.getParent() == null ? null : category.getParent().getName())
                .isActive(category.getIsActive())
                .isAttention(category.getIsAttention())
                .createDate(category.getCreateDate().toLocalDate().toString())
                .categoryList(categoryList)
                .build();
    }

    public static List<CategoryDto> of(List<Category> list){
        return list.stream()
                .map(p->CategoryDto.of(p))
                .collect(Collectors.toList());
    }

}
