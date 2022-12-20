package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderChangeReasonFlag implements EnumCodeModel {
    CANCEL_REASON_900("판매중지", "900"),
    CANCEL_REASON_300("기타","200"),

    CANCEL_REASON_101("상품이 마음에 들지 않음(단숨변심)", "101"),
    CANCEL_REASON_102("상품이 문제 있음(불량)", "102"),
    CHANGE_REASON_104("상품이 설명과 다름", "103"),
    CANCEL_REASON_103("다른 상품 추가 후 재주문 예정", "104"),
    CANCEL_REASON_105("오배송 및 배송 누락", "105"),

    //관리자, 셀러
    CANCEL_REASON_201("재고소진", "201"),
    CANCEL_REASON_202("연락두절","202"),
    CANCEL_REASON_203("주문 미확정 대기 취소", "203"),
    CANCEL_REASON_204("서비스 미인지","204"),
    CANCEL_REASON_205("주문 상품 삭제","205"),
    CANCEL_REASON_206("주문 상품 가격 변동","206"),
    CANCEL_REASON_207("조기마감","208"),

    CHANGE_REASON_301("상품이 마음에 들지 않음(단숨변심)", "301"),
    CHANGE_REASON_302("상품이 문제 있음(불량)", "302"),
    CHANGE_REASON_303("상품이 설명과 다름", "303"),
    CHANGE_REASON_304("상품이 누락됨", "304"),
    CHANGE_REASON_305("다른 상품이 배송됨", "305"),
    CHANGE_REASON_306("다른 주소로 배송됨", "306"),
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
