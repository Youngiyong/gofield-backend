package com.gofield.domain.rds.domain.code;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DynamicUpdate
@DynamicInsert
@Table(	name = "code")
public class Code extends BaseTimeEntity {

    @Column(length = 32)
    private String name;

    @Column(length = 32)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "group", nullable = false, length = 20)
    private ECodeGroup group;

    @Column
    private Short sort;

    @Column
    private Boolean isActive;
}
