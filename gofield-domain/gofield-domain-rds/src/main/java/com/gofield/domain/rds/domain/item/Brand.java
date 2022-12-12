
package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.common.EStatusFlag;
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
@Table(	name = "brand")
public class Brand extends BaseTimeEntity {

    @Column
    private String name;

    @Column
    private String thumbnail;

    @Column
    private Short sort;

    @Column
    private Boolean isVisible;

    @Column(name = "status_flag")
    private EStatusFlag status;

    @Builder
    private Brand(String name, String thumbnail, Boolean isVisible){
        this.name = name;
        this.thumbnail = thumbnail;
        this.isVisible = isVisible;
    }

    public static Brand newInstance(String name, String thumbnail, Boolean isVisible){
        return Brand.builder()
                .name(name)
                .thumbnail(thumbnail)
                .isVisible(isVisible)
                .build();
    }

    public void update(String name, String thumbnail, Boolean isVisible){
        this.name =  name != null ? name : this.name;
        this.thumbnail =  thumbnail != thumbnail ? name : this.thumbnail;
        this.isVisible =  isVisible != null ? isVisible : this.isVisible;
    }

    public void delete(){
        this.status = EStatusFlag.DELETE;
    }
}
