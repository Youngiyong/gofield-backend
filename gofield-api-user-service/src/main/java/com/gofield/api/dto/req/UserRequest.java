package com.gofield.api.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserRequest {



    @Getter
    public static class UserAccountTel {
        @NotNull(message = "전화번호는 필수값입니다.")
        @Pattern(regexp = "(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})", message = "전화번호 형식이 맞지 않습니다.")
        private String tel;
    }

    @Getter
    public static class UserAddress {
        @NotNull(message = "전화번호는 필수값입니다.")
        @Pattern(regexp = "(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})", message = "전화번호 형식이 맞지 않습니다.")
        private String tel;
        @NotNull(message = "이름은 필수값입니다.")
        private String name;
        @NotNull(message = "우편번호는 필수값입니다.")
        private String zipCode;
        @NotNull(message = "주소는 필수값입니다.")
        private String address;
        @NotNull(message = "상세주소는 필수값입니다.")
        private String addressExtra;
        private Boolean isMain;
    }

    @Getter
    public static class UserUpdateAddress {
        private String tel;
        private String name;
        private String zipCode;
        private String address;
        private String addressExtra;
        private Boolean isMain;
    }

    @Getter
    public static class ShippingAddress {
        @NotNull(message = "전화번호는 필수값입니다.")
        @Pattern(regexp = "(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})", message = "전화번호 형식이 맞지 않습니다.")
        private String tel;
        @NotNull(message = "이름은 필수값입니다.")
        private String name;
        @NotNull(message = "우편번호는 필수값입니다.")
        private String zipCode;
        @NotNull(message = "주소는 필수값입니다.")
        private String address;
        @NotNull(message = "상세주소는 필수값입니다.")
        private String addressExtra;
        @NotNull(message = "배송 코멘트 코드는 필수값입니다.")
        private String shippingCode;
        private String shippingComment;
    }

    @Getter
    public static class UserAccountCode {
        @NotNull(message = "전화번호는 필수값입니다.")
        @Size(min = 6, max = 6, message = "6자리 코드를 입력해주세요.")
        private String code;
    }


    @Getter
    public static class UserAccountInfo {
        @NotNull(message = "은행코드는 필수값입니다.")
        private String bankCode;
        @NotNull(message = "은행명은 필수값입니다.")
        private String bankName;
        @NotNull(message = "예금주명은 필수값입니다.")
        private String bankHolderName;
        @NotNull(message = "계좌번호는 필수값입니다.")
        @Size(min = 8, max = 32, message = "8자리 이상 입력해주세요.")
        private String bankAccountNumber;
        private List<Long> agreeList;
    }

    @Getter
    public static class UserProfile {
        private String name;
        private String nickName;
        private Integer weight;
        private Integer height;
        private Boolean isAlertPromotion;
    }
}
