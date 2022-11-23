
package com.gofield.domain.rds.entity.itemDetail;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.enums.item.EItemGenderFlag;
import com.gofield.domain.rds.enums.item.EItemSpecFlag;
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
@Table(	name = "item_detail")
public class ItemDetail extends BaseTimeEntity {

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String option;

    @Column(name = "gender_flag", nullable = false)
    private EItemGenderFlag gender;

    @Column(name = "spec_flag", nullable = false)
    private EItemSpecFlag spec;
}
