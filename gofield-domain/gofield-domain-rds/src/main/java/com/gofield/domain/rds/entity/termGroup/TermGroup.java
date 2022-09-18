
package com.gofield.domain.rds.entity.termGroup;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.term.Term;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "term_group")
public class TermGroup extends BaseTimeEntity {

    @OneToMany(mappedBy = "termGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Term> terms;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 300, nullable = false)
    private String description;
}
