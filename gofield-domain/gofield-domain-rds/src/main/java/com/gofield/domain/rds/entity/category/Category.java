
package com.gofield.domain.rds.entity.category;

import com.gofield.domain.rds.converter.CategoryFlagConverter;
import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.enums.ECategoryFlag;
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
@Table(	name = "category")
public class Category extends BaseTimeEntity {

    @Column(name = "type_flag", nullable = false, length = 15)
    private ECategoryFlag type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Category> children;

    @Column
    private String name;

    @Column
    private Short sort;

    @Column
    private String thumbnail;

    @Column
    private Boolean isActive;

    @Column
    private Boolean isAttention;
}
