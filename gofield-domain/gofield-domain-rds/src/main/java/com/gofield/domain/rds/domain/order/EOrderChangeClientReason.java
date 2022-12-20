package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderChangeClientReason implements EnumModel {
    CHANGE_REASON_301("상품이 마음에 들지 않음(단숨변심)"),
    CHANGE_REASON_302("상품이 문제 있음(불량)"),
    CHANGE_REASON_303("상품이 설명과 다름"),
    CHANGE_REASON_304("상품이 누락됨"),
    CHANGE_REASON_305("다른 상품이 배송됨"),
    CHANGE_REASON_306("다른 주소로 배송됨"),
    ;
    private String description;

    @Override
    public String getKey() {
        return name();
    }
}
