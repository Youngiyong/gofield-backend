
package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.user.Term;
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
@Table(	name = "term_group")
public class TermGroup extends BaseTimeEntity {

    @OneToMany(mappedBy = "termGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Term> terms;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 300, nullable = false)
    private String description;
}
