package com.gofield.domain.rds.entity.userAccountSmsHistory;

import com.gofield.domain.rds.entity.userAccount.UserAccount;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_account_sms_history")
public class UserAccountSmsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String tel;

    @Column
    private String code;

    @Column
    private LocalDateTime createDate;

    private UserAccountSmsHistory(Long userId, String tel, String code){
        this.tel = tel;
        this.code = code;
        this.userId = userId;
    }

    public static UserAccountSmsHistory newInstance(Long userId, String tel, String code){
        return new UserAccountSmsHistory(userId, tel, code);
    }
}
