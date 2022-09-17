package com.gofield.api.model.request;

import com.gofield.domain.rds.enums.EPlatformFlag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserRequest {

    @Getter
    public static class PushKey{
        @NotNull(message = "푸쉬키는 필수값입니다.")
        @ApiModelProperty(value = "푸쉬키", example = "fSt6Fu3ERtaxMhTxcaIt68:APA91bGuv8011hBRNL_r4Sz_4mgHzdvHsQXuy81JBb8a1_ehIDZJLk1qXwR0xH_zcqzsMepCU9t7jRMFZS6dhPOcGt657usiZlF9-3SvljmCOgvCcrzWyS0JtsdxQdAFqgeIEDeWOqu_", dataType = "java.lang.String")
        private String pushKey;
        @NotNull(message = "플랫폼은 필수값입니다.")
        @ApiModelProperty(value = "ANDROID/IOS", example = "ANDROID", dataType = "java.lang.String")
        private EPlatformFlag platform;
    }

    @Getter
    public static class UserAccountTel {
        @NotNull(message = "전화번호는 필수값입니다.")
        @Pattern(regexp = "(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})", message = "전화번호 형식이 맞지 않습니다.")
        private String tel;
    }

    @Getter
    public static class UserAccountCode {
        @NotNull(message = "전화번호는 필수값입니다.")
        @Size(min = 6, max = 6, message = "6자리 코드를 입력해주세요.")
        private String code;
    }

    @Getter
    public static class UserAccountInfo {
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
        private String thumbnail;
        private Integer weight;
        private Integer height;
        private Boolean isAlertPromotion;
    }
}
