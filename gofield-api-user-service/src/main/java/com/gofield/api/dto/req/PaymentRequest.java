package com.gofield.api.dto.req;

import com.gofield.api.dto.enums.PaymentType;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PaymentRequest {

    @Getter
    public static class PaymentCreate {
        @NotNull
        private PaymentType paymentType;
        @NotNull(message = "주문 코드는 필수값입니다.")
        private String code;
        @NotNull(message = "배송주소는 필수값입니다.")
        private UserRequest.ShippingAddress shippingAddress;
    }
}
