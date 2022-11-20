package com.gofield.domain.rds.entity.seller;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.enums.EStatusFlag;
import lombok.AccessLevel;
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
@Table(	name = "seller")
public class Seller extends BaseTimeEntity {

    @Column(nullable = false, length = 36)
    private String uuid;

    @Column(length = 36)
    private String name;

    @Column(length = 20)
    private String tel;

    @Column(length = 50)
    private String email;

    @Column(name = "status_flag", nullable = false, length = 20)
    private EStatusFlag status;

}
