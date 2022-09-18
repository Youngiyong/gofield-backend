
package com.gofield.domain.rds.entity.term;

import com.gofield.domain.rds.converter.TermFlagConverter;
import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.termGroup.TermGroup;
import com.gofield.domain.rds.enums.ETermFlag;
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
@Table(	name = "term")
public class Term extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private TermGroup termGroup;

    @Column(nullable = false, name = "type_flag")
    private ETermFlag type;

    @Column(length = 256)
    private String url;

    @Column(length = 100)
    private String name;

    @Column
    private Boolean isEssential;

    @Column
    private Boolean isActive;

    @Column
    private Short sort;

    @Column
    private LocalDateTime termDate;
}
