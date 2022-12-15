package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.item.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@NoArgsConstructor
public class CategoryListDto {

    private List<Category> list;
    private Page<Category> page;

    @Builder
    private CategoryListDto(List<Category> list, Page<Category> page){
        this.list = list;
        this.page = page;
    }

    public static CategoryListDto of(List<Category> list, Page<Category> page){
        return CategoryListDto.builder()
                .list(list)
                .page(page)
                .build();
    }

}
