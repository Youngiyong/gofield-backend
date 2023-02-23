package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderCancelStatusFlag implements EnumCodeModel {
    ORDER_CANCEL_REQUEST("취소신청", "500"),
    ORDER_CANCEL_PROCESS("취소처리중", "503"),
    ORDER_CANCEL_COMPLETE("취소완료", "504"),
    ORDER_CANCEL_DENIED("취소처리실패","505"),



    ORDER_CHANGE_REQUEST("교환신청", "600"),
    ORDER_CHANGE_COLLECT_PROCESS("수거중", "601"),
    ORDER_CHANGE_COLLECT_PROCESS_COMPLETE("수거완료", "602"),
    ORDER_CHANGE_REDELIVERY("재배송", "603"),
    ORDER_CHANGE_DENIED("교환처리실패","605"),
    ORDER_CHANGE_COMPLETE("교환완료", "610"),



    ORDER_RETURN_REQUEST("반품신청", "700"),
    ORDER_RETURN_COLLECT_PROCESS("수거중", "701"),
    ORDER_RETURN_COLLECT_PROCESS_COMPLETE("수거완료", "702"),
    ORDER_RETURN_DENIED("반품처리실패", "705"),
    ORDER_RETURN_COMPLETE("반품완료", "710"),

    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
