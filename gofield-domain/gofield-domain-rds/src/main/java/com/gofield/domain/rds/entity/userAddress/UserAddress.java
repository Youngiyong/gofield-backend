package com.gofield.domain.rds.entity.userAddress;


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
@Table(name = "user_address")
public class UserAddress extends BaseTimeEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 32)
    private String tel;

    @Column(length = 32)
    private String name;

    @Column(length = 10)
    private String zipCode;

    @Column(length = 128)
    private String address;

    @Column(length = 128)
    private String addressExtra;

    @Column
    private Boolean isMain;

    private UserAddress(User user, String tel, String name, String zipCode, String address, String addressExtra, Boolean isMain){
        this.user = user;
        this.tel = tel;
        this.name = name;
        this.zipCode = zipCode;
        this.address = address;
        this.addressExtra = addressExtra;
        this.isMain = isMain;
    }

    public static UserAddress newInstance(User user, String tel, String name, String zipCode, String address, String addressExtra, Boolean isMain){
        return new UserAddress(user, tel, name, zipCode, address, addressExtra, isMain);
    }

    public void update(String tel, String name, String zipCode, String address, String addressExtra, Boolean isMain){
        this.tel =  tel != null ? tel : this.tel;
        this.name =  name != null ? name : this.name;
        this.zipCode = zipCode != null ? zipCode : this.zipCode;
        this.address = address != null ? address : this.address;
        this.addressExtra = addressExtra != null ? addressExtra : this.addressExtra;
        this.isMain = isMain != null ? isMain : this.isMain;
    }
    public void updateMain(Boolean isMain){
        this.isMain = isMain;
    }
}

