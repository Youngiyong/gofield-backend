
package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "item_detail")
public class ItemDetail extends BaseTimeEntity {

    @Column(name = "gender_flag", nullable = false)
    private EItemGenderFlag gender;

    @Column(name = "spec_flag", nullable = false)
    private EItemSpecFlag spec;

    @Column(columnDefinition = "TEXT")
    private String itemOption;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private LocalDateTime deleteDate;

    @Builder
    private ItemDetail(EItemGenderFlag gender, EItemSpecFlag spec, String option, String description){
        this.gender = gender;
        this.spec = spec;
        this.itemOption = option;
        this.description = description;
    }

    public static ItemDetail newInstance(EItemGenderFlag gender, EItemSpecFlag spec, String option, String description){
        return ItemDetail.builder()
                .gender(gender)
                .spec(spec)
                .option(option)
                .description(description)
                .build();
    }

    public void update(EItemGenderFlag gender, EItemSpecFlag spec, String description, String itemOption){
        this.gender = gender;
        this.spec = spec;
        this.description = description;
        this.itemOption = itemOption;
    }

    public void delete(){
        this.deleteDate = LocalDateTime.now();
    }
}
