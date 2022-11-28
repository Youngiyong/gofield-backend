package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.user.UserAccount;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAccountResponse {

    private String bankCode;
    private String bankName;
    private String bankHolderName;
    private String bankAccountNumber;

    @Builder
    private UserAccountResponse(String bankCode, String bankName, String bankHolderName, String bankAccountNumber){
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.bankHolderName = bankHolderName;
        this.bankAccountNumber = bankAccountNumber;
    }

    public static UserAccountResponse of(UserAccount userAccount){
        return UserAccountResponse.builder()
                .bankCode(userAccount.getBankCode())
                .bankName(userAccount.getBankName())
                .bankHolderName(userAccount.getBankHolderName())
                .bankAccountNumber(userAccount.getBankAccountNumber())
                .build();
    }
}
