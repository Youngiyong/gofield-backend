package com.gofield.admin.service;

import com.gofield.admin.dto.CategoryDto;
import com.gofield.admin.dto.CategoryListDto;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.item.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CategoryListDto getCategoryList(String name, Pageable pageable) {
        Page<Category> page = categoryRepository.findAllByKeyword(name, pageable);
        List<Category> category = page.getContent();
        return CategoryListDto.of(category, page);
    }

    @Transactional
    public void updateCategory(CategoryDto categoryDto){
        Category category = categoryRepository.findByCategoryId(categoryDto.getId());
        Category parent = null;
        if(categoryDto.getParentId()!=null){
            parent = categoryRepository.findByCategoryId(categoryDto.getParentId());
        }
        category.update(parent, categoryDto.getName(), categoryDto.getIsActive(), categoryDto.getIsAttention());
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategory(Long id){
        List<Category> categoryList = categoryRepository.findAllIsActiveOrderBySort();
        List<CategoryDto> categoryDtoList = CategoryDto.of(categoryList);
        if(id==null){
            return CategoryDto.of(null, categoryDtoList);
        }
        return CategoryDto.of(categoryRepository.findByCategoryId(id), categoryDtoList);
    }

    @Transactional
    public void save(CategoryDto categoryDto){
        Category category = categoryRepository.findByParentIdAndName(categoryDto.getParentId(), categoryDto.getName());
        if(category!=null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 존재하는 카테고리입니다.");
        }
        Category parent = null;
        if(categoryDto.getParentId()!=null){
            parent = categoryRepository.findByCategoryId(categoryDto.getParentId());
        }
        category = Category.newInstance(ECategoryFlag.NORMAL, parent, categoryDto.getName(), categoryDto.getIsActive(), categoryDto.getIsAttention());
        categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id){
        Category category = categoryRepository.findByCategoryId(id);
        categoryRepository.delete(category);
    }

}