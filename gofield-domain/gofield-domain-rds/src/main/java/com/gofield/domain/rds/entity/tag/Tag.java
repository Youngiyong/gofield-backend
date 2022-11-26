
package com.gofield.domain.rds.entity.tag;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.itemHasTag.ItemHasTag;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "tag")
public class Tag extends BaseTimeEntity {
    @Column
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<ItemHasTag> item;
}
