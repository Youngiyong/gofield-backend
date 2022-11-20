package com.gofield.domain.rds.entity.userAccount;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_account")
public class UserAccount extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 32)
    private String bankName;

    @Column(length = 32)
    private String bankCode;

    @Column(length = 30)
    private String bankHolderName;

    @Column( length = 64)
    private String bankAccountNumber;

    private UserAccount(User user){
        this.user = user;
    }

    public static UserAccount newInstance(User user){
        return new UserAccount(user);
    }
    public void updateAccountInfo(String bankName, String bankCode, String bankHolderName, String bankAccountNumber){
        this.bankName = bankName != null ? bankName : this.bankName;
        this.bankCode = bankCode != null ? bankCode : this.bankCode;
        this.bankHolderName = bankHolderName != null ? bankHolderName : this.bankHolderName;
        this.bankAccountNumber = bankAccountNumber != null ? bankAccountNumber : this.bankAccountNumber;

    }
}
