package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderChangeStatusFlag implements EnumCodeModel {
    ORDER_CHANGE_REQUEST("교환 신청", "600"),
    ORDER_CHANGE_HOLD("교환 신청 보류", "601"),
    ORDER_CHANGE_PROCESS("교환 신청 처리중", "603"),
    ORDER_CHANGE_COMPLETE("교환 완료", "604"),
    ORDER_CHANGE_DENIED("교환 거절/철회","605"),
    ;
    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
