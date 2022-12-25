package com.gofield.api.dto.req;

import com.gofield.domain.rds.domain.common.EEnvironmentFlag;
import com.gofield.domain.rds.domain.order.EOrderCancelReasonFlag;
import com.gofield.domain.rds.domain.order.EOrderItemStatusFlag;
import com.gofield.domain.rds.domain.order.EPaymentType;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {

    @Getter
    public static class OrderPay {
        @NotNull(message = "companyCode는 필수값입니다.")
        private String companyCode;
        @NotNull(message = "paymentType은 필수값입니다.")
        private EPaymentType paymentType;
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

        @NotNull(message = "반품 사용자 이름은 필수값입니다.")
        private String username;

        @NotNull(message = "반품 사용자 전화번호는 필수값입니다.")
        private String userTel;

        @NotNull(message = "반품 우편번호는 필수값입니다.")
        private String zipCode;

        @NotNull(message = "반품 주소는 필수값입니다.")
        private String address;

        @NotNull(message = "반품 상세 주소는 필수값입니다.")
        private String addressExtra;
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
