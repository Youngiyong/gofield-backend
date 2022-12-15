package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
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

    @Column(name = "type_flag", nullable = false, length = 20)
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

    @Builder
    private Category(ECategoryFlag type, Category parent, String name, Boolean isActive, Boolean isAttention){
        this.type = type;
        this.parent = parent;
        this.name = name;
        this.isActive = isActive;
        this.isAttention = isAttention;
    }

    public static Category newInstance(ECategoryFlag type, Category parent, String name, Boolean isActive, Boolean isAttention){
        return Category.builder()
                .type(type)
                .parent(parent)
                .name(name)
                .isActive(isActive)
                .isAttention(isAttention)
                .build();
    }

    public void update(Category parent, String name, Boolean isActive, Boolean isAttention){
        this.parent = parent;
        this.name = name != null ? name : this.name;
        this.isActive =  isActive;
        this.isAttention = isAttention;
    }
}
