
package com.gofield.domain.rds.entity.term;

import com.gofield.domain.rds.converter.TermFlagConverter;
import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.enums.ETermFlag;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "term")
public class Term extends BaseTimeEntity {

    @Convert(converter = TermFlagConverter.class)
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
