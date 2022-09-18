
package com.gofield.domain.rds.entity.tag;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "tag")
public class Tag extends BaseTimeEntity {
    @Column
    private String name;
}
