package com.gofield.domain.rds.entity.userAddress;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_address")
public class UserAddress extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String zipCode;

    @Column(length = 128)
    private String roadAddress;

    @Column(length = 128)
    private String jibunAddress;

    @Column(length = 100)
    private String addressExtra;

    @Column
    private String lat;

    @Column
    private String lng;

}

