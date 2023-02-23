package com.gofield.api.service.cart.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

public class CartRequest {

    @Getter
    public static class Cart {
        @NotNull(message = "아이템 고유번호는 필수값입니다.")
        private String itemNumber;
        private Boolean isNow;
    }

    @Getter
    public static class CartQty {
        @NotNull(message = "아이템 고유번호는 필수값입니다.")
        private String itemNumber;
        private int qty;
    }

    @Getter
    public static class CartTemp {
        private Long cartId;
        private String itemNumber;
        private int qty;
    }

}
