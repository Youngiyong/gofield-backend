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

    @Column(length = 30)
    private String bankHolderName;

    @Column( length = 64)
    private String bankAccountNumber;

    @Column(length = 20)
    private String tel;

    @Column(length = 36)
    private String name;

    @Column(length = 50)
    private String email;

    @Column
    private Integer weight;

    @Column
    private Integer height;

    private UserAccount(User user, String email, Integer weight, Integer height){
        this.user = user;
        this.email = email;
        this.weight = weight;
        this.height = height;
    }

    public static UserAccount newInstance(User user, String email, Integer weight, Integer height){
        return new UserAccount(user, email, weight, height);
    }
    public void updateSign(String email, Integer weight, Integer height){
        this.email = email != null ? email : this.email;
        this.weight = weight != null ? weight : this.weight;
        this.height = height != null ? height : this.height;
    }

//    public void update(String bankName, String bankHolderName, String bankAccountNumber, String tel ,String name, String email){
//        this.tel = tel != null ? tel : this.tel;
//        this.name = name != null ? name : this.name;
//        this.gender = gender != null ? gender : this.gender;
//        this.loginFlag = loginFlag != null ? loginFlag : this.loginFlag;
//    }
}
