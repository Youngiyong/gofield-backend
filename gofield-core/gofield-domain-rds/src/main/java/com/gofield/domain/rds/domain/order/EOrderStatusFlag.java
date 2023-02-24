package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderStatusFlag implements EnumCodeModel {
    ORDER_DEPOSIT_WAIT("입금대기", "199"),
    ORDER_CREATE("주문생성", "200"),
    ORDER_CANCEL("주문취소", "201"),
    ORDER_APPROVAL("주문승인", "202"),
    ORDER_COMPLETE("주문완료","210"),
    ORDER_DELETE("주문삭제", "299"),
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
