package com.gofield.api.service;

import com.gofield.api.model.enums.TermType;
import com.gofield.api.model.response.CategoryResponse;
import com.gofield.api.model.response.TermResponse;
import com.gofield.api.model.response.VersionResponse;
import com.gofield.api.util.ApiUtil;
import com.gofield.common.exception.InternalRuleException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.entity.category.Category;
import com.gofield.domain.rds.entity.category.CategoryRepository;
import com.gofield.domain.rds.entity.serverStatus.ServerStatus;
import com.gofield.domain.rds.entity.serverStatus.ServerStatusRepository;
import com.gofield.domain.rds.entity.term.Term;
import com.gofield.domain.rds.entity.term.TermRepository;
import com.gofield.domain.rds.entity.termGroup.TermGroup;
import com.gofield.domain.rds.entity.termGroup.TermGroupRepository;
import com.gofield.domain.rds.entity.version.Version;
import com.gofield.domain.rds.entity.version.VersionRepository;
import com.gofield.domain.rds.enums.EClientType;
import com.gofield.domain.rds.enums.EPlatformFlag;
import com.gofield.domain.rds.enums.EServerType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class AppService {
    private final TermRepository termRepository;
    private final TermGroupRepository termGroupRepository;
    private final VersionRepository versionRepository;
    private final ServerStatusRepository serverStatusRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryList(){
        List<Category> resultList = categoryRepository.findAllIsActiveTrueOrderBySort();
        return CategoryResponse.of(resultList);
    }

    @Transactional(readOnly = true)
    public List<TermResponse> getTermList(TermType type){
        TermGroup termGroup = null;
        if(type.equals(TermType.SIGNUP)){
            termGroup = termGroupRepository.findByGroupId(2L);
        } else if(type.equals(TermType.PRIVACY)){
            termGroup = termGroupRepository.findByGroupId(3L);
        }
        return TermResponse.of(termGroup.getTerms());
    }

    @Transactional(readOnly = true)
    public void healthCheck(){
        ServerStatus serverStatus = serverStatusRepository.findByServerType(EServerType.U);
        if(!serverStatus.getIsActive()){
            throw new InternalRuleException(ErrorCode.E502_BAD_GATEWAY, ErrorAction.TOAST, serverStatus.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public VersionResponse versionCheck(String appVersion, EPlatformFlag platform, String osVersion){
        if(appVersion==null || platform==null) throw new InternalRuleException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "앱 버전과 OS를 입력해주세요.");

        String[] arrayVersion = appVersion.split("[.]");
        if(arrayVersion.length!=3) throw new InternalRuleException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "앱 버전 형식이 잘못되었습니다.");

        int intVersion = ApiUtil.stringConvertToIntVersion(arrayVersion);

        Version result = versionRepository.findByPlatformAndType(platform, EClientType.U);

        if(intVersion>result.getMaxTrans()){
            log.error(platform.name() + " 지원되지 않는 최대 버전입니다.");
            throw new InternalRuleException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "지원되지 않는 최대 버전입니다.\"");
        }

        String type;
        String message = "";

        if (intVersion == result.getMaxTrans()) {
            type = "NEWEST";
            message = "최신 버전입니다.";
        } else if (intVersion < result.getMinTrans()){
            type = "UPDATE";
            message = "안정적인 서비스 사용을 위해\n" +
                    "최신 버전으로 업데이트해주세요.";
        } else {
            type = "VALID";
            message = "지금 업데이트를 설치하시겠습니까?";
        }

        return VersionResponse.of(type, message, result.getMarketUrl());
    }
}
