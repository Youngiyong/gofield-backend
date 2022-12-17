package com.gofield.api.service;

import com.gofield.api.dto.res.CategoryResponse;
import com.gofield.api.dto.res.CodeResponse;
import com.gofield.domain.rds.domain.item.Category;
import com.gofield.domain.rds.domain.item.CategoryRepository;
import com.gofield.domain.rds.domain.code.Code;
import com.gofield.domain.rds.domain.code.CodeRepository;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    private final CodeRepository codeRepository;

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CodeResponse> getCodeList(ECodeGroup group, Boolean isHide){
        List<Code> codeList = codeRepository.findAllByGroupByIsHide(group, isHide);
        return CodeResponse.of(codeList);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryList(){
        List<Category> resultList = categoryRepository.findAllIsActiveAndIsAttentionOrderBySort();
        return CategoryResponse.of(resultList);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getSubCategoryList(Long categoryId){
        List<Category> resultList = categoryRepository.findAllSubCategoryByCategoryId(categoryId);
        return CategoryResponse.of(resultList);
    }

}
