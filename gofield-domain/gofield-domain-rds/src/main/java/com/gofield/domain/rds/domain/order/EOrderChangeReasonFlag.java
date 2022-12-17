package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderChangeReasonFlag implements EnumCodeModel {
    CHANGE_REASON_900("판매중지", "900"),
    CHANGE_REASON_300("기타","200"),

    CHANGE_REASON_101("상품이 마음에 들지 않음(단숨변심)", "101"),
    CHANGE_REASON_102("상품이 문제 있음(불량)", "102"),
    CHANGE_REASON_103("상품이 설명과 다름", "103"),
    CHANGE_REASON_104("상품이 누락됨", "104"),
    CHANGE_REASON_105("다른 상품이 배송됨", "105"),
    CHANGE_REASON_106("다른 주소로 배송됨", "106"),

    CHANGE_REASON_201("재고소진", "201"),
    CHANGE_REASON_202("연락두절","202"),
    CHANGE_REASON_203("주문미확정 대기취소", "203"),
    CHANGE_REASON_204("서비스미인지","204"),
    CHANGE_REASON_205("주문 상품 삭제","205"),
    CHANGE_REASON_206("주문 상품 가격 변동","206"),
    CHANGE_REASON_207("조기마감","208"),
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
