
package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
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

    @Builder
    private ItemDetail(String description, String option, EItemGenderFlag gender, EItemSpecFlag spec){
        this.description = description;
        this.option = option;
        this.gender = gender;
        this.spec = spec;
    }

    public static ItemDetail newInstance(String description, String option, EItemGenderFlag gender, EItemSpecFlag spec){
        return ItemDetail.builder()
                .description(description)
                .option(option)
                .gender(gender)
                .spec(spec)
                .build();
    }
}
