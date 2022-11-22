package com.gofield.api.service;

import com.gofield.api.dto.res.CategoryResponse;
import com.gofield.api.dto.res.CodeResponse;
import com.gofield.domain.rds.entity.category.Category;
import com.gofield.domain.rds.entity.category.CategoryRepository;
import com.gofield.domain.rds.entity.code.Code;
import com.gofield.domain.rds.entity.code.CodeRepository;
import com.gofield.domain.rds.enums.ECodeGroup;
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
    public List<CodeResponse> getCodeList(ECodeGroup group){
        List<Code> codeList = codeRepository.findByGroup(group);
        return CodeResponse.of(codeList);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryList(){
        List<Category> resultList = categoryRepository.findAllIsActiveAndIsAttentionOrderBySort();
        return CategoryResponse.of(resultList);
    }

}
