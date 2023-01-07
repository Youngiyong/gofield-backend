package com.gofield.api.service;

import com.gofield.api.dto.res.*;
import com.gofield.domain.rds.domain.common.PaginationResponse;
import com.gofield.domain.rds.domain.faq.Faq;
import com.gofield.domain.rds.domain.faq.FaqRepository;
import com.gofield.domain.rds.domain.item.Category;
import com.gofield.domain.rds.domain.item.CategoryRepository;
import com.gofield.domain.rds.domain.code.Code;
import com.gofield.domain.rds.domain.code.CodeRepository;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import com.gofield.domain.rds.domain.item.ItemBundleReview;
import com.gofield.domain.rds.domain.notice.Notice;
import com.gofield.domain.rds.domain.notice.NoticeRepository;
import com.gofield.domain.rds.domain.user.User;
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
public class CommonService {

    private final FaqRepository faqRepository;
    private final CodeRepository codeRepository;
    private final NoticeRepository noticeRepository;
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

    @Transactional(readOnly = true)
    public FaqListResponse getFaqList(Pageable pageable){
        Page<Faq> result = faqRepository.findAllPaging(pageable, true);
        List<FaqResponse> list = FaqResponse.of(result.getContent());
        return FaqListResponse.of(list, PaginationResponse.of(result));
    }

    @Transactional(readOnly = true)
    public NoticeListResponse getNoticeList(Pageable pageable){
        Page<Notice> result = noticeRepository.findAllPaging(pageable, true);
        List<NoticeResponse> list = NoticeResponse.of(result.getContent());
        return NoticeListResponse.of(list, PaginationResponse.of(result));
    }

}
