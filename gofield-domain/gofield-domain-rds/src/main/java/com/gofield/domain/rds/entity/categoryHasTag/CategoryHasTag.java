package com.gofield.domain.rds.entity.categoryHasTag;

import com.gofield.domain.rds.entity.category.Category;
import com.gofield.domain.rds.entity.tag.Tag;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "category_has_tag")
public class CategoryHasTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Tag tag;

    @Column
    private LocalDateTime createDate;

}
