package com.gofield.api.service.order.dto.request;

import com.gofield.api.service.user.dto.request.UserRequest;
import com.gofield.domain.rds.domain.common.EEnvironmentFlag;
import com.gofield.domain.rds.domain.order.EOrderCancelReasonFlag;
import com.gofield.domain.rds.domain.order.EPaymentType;
import com.gofield.domain.rds.domain.order.OrderWait;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {

    @Getter
    public static  class OrderCreate {
        private String orderId;
        private String paymentKey;
        private OrderWait orderWait;
        private OrderSheet orderSheet;
        private String paymentCompany;
        private EPaymentType paymentType;
        private String cardNumber;
        private String cardType;
        private int installmentPlanMonth;
    }

    @Getter
    public static class OrderPay {
        private String companyCode;
        @NotNull(message = "paymentType은 필수값입니다.")
        private EPaymentType paymentType;
        @NotNull(message = "uuid는 필수값입니다.")
        private String uuid;
        @NotNull(message = "배송주소는 필수값입니다.")
        private UserRequest.ShippingAddress shippingAddress;
        private String bankCode;
        private String customerName;
        private String customerEmail;
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

    @Getter
    public static class OrderReview {
        @NotNull(message = "몸무게는 필수값입니다.")
        private Integer weight;
        @NotNull(message = "키는 필수값입니다.")
        private Integer height;
        @NotNull(message = "평점은 필수값입니다.")
        private Double reviewScore;
        @NotNull(message = "리뷰 내용은 필수값입니다.")
        private String content;
    }

    @Getter
    public static class OrderCancel {
        @NotNull(message = "reason은 필수값입니다.")
        private EOrderCancelReasonFlag reason;
    }

    @Getter
    public static class OrderReturn {
        @NotNull(message = "reason은 필수값입니다.")
        private EOrderCancelReasonFlag reason;

        @NotNull(message = "반품 내용은 필수값입니다.")
        private String content;
    }

    @Getter
    public static class OrderChange {
        @NotNull(message = "reason은 필수값입니다.")
        private EOrderCancelReasonFlag reason;

        @NotNull(message = "교환 사용자 이름은 필수값입니다.")
        private String username;

        @NotNull(message = "교환 사용자 전화번호는 필수값입니다.")
        private String userTel;

        @NotNull(message = "교환 우편번호는 필수값입니다.")
        private String zipCode;

        @NotNull(message = "교환 주소는 필수값입니다.")
        private String address;

        @NotNull(message = "교환 상세 주소는 필수값입니다.")
        private String addressExtra;

        @NotNull(message = "교환 상품 정보를 입력해주세요.")
        private String content;
    }
}
