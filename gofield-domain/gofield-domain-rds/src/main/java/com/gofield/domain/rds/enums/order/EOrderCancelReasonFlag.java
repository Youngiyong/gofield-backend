package com.gofield.domain.rds.enums.order;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderCancelReasonFlag implements CodeEnum {
    DECLINE_REASON_100("판매중지", "900"),
    DECLINE_REASON_101("재고소진", "201"),
    DECLINE_REASON_102("사용자구매취소", "202"),
    DECLINE_REASON_103("주문미확정 대기취소", "203"),
    DECLINE_REASON_104("기타","204"),
    DECLINE_REASON_105("연락두절","205"),
    DECLINE_REASON_106("조기마감","206"),
    DECLINE_REASON_107("서비스미인지","207"),
    DECLINE_REASON_108("주문 상품 삭제","208"),
    DECLINE_REASON_109("주문 상품 가격 변동","209"),
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
