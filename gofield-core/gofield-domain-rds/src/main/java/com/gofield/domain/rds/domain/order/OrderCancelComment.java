package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "order_cancel_comment")
public class OrderCancelComment extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 20)
    private String tel;

    @Column(nullable = false, length = 20)
    private String zipCode;

    @Column(nullable = false, length = 128)
    private String address;

    @Column(nullable = false, length = 128)
    private String addressExtra;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    private OrderCancelComment(User user, String name, String tel, String zipCode, String address, String addressExtra,  String content){
        this.user = user;
        this.name = name;
        this.tel = tel;
        this.zipCode = zipCode;
        this.address = address;
        this.addressExtra = addressExtra;
        this.content = content;
    }

    public static OrderCancelComment newInstance(User user, String name, String tel, String zipCode, String address, String addressExtra, String content){
        return OrderCancelComment.builder()
                .user(user)
                .name(name)
                .tel(tel)
                .zipCode(zipCode)
                .address(address)
                .addressExtra(addressExtra)
                .content(content)
                .build();
    }

}
