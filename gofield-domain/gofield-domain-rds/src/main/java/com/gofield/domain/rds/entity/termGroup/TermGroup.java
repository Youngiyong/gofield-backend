
package com.gofield.domain.rds.entity.termGroup;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.term.Term;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "term_group")
public class TermGroup extends BaseTimeEntity {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Set<Term> terms;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 300, nullable = false)
    private String description;
}
