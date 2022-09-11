
package com.gofield.domain.rds.entity.tag;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "tag")
public class Tag extends BaseTimeEntity {

    @Column
    private String name;
}
