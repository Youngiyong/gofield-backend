package com.gofield.api.dto.req;

import com.gofield.api.dto.enums.PaymentType;
import com.gofield.domain.rds.domain.common.EEnvironmentFlag;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public class OrderRequest {

    @Getter
    public static class Order {
        @NotNull(message = "companyCode는 필수값입니다.")
        private String companyCode;
        @NotNull(message = "paymentType은 필수값입니다.")
        private PaymentType paymentType;
        @NotNull(message = "uuid는 필수값입니다.")
        private String uuid;
        @NotNull(message = "배송주소는 필수값입니다.")
        private UserRequest.ShippingAddress shippingAddress;
        private EEnvironmentFlag environment;
    }

    @Getter
    public static class OrderSheet {
        private Boolean isCart;
        private int totalDelivery;
        private int totalPrice;
        private List<OrderSheetItem> items;
        @Getter
        public static class OrderSheetItem {
            private Long cartId;
            @NotNull
            private String itemNumber;
            private int qty;
        }
    }
}
