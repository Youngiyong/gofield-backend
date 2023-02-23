package com.gofield.domain.rds.domain.user;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_has_term")
public class UserTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "term_id")
    private Term term;

    @Column
    private Boolean isAgree;

    @Column
    private LocalDateTime createDate;

    private UserTerm(User user, Term term){
        this.user = user;
        this.term = term;
    }

    public static UserTerm newInstance(User user, Term term){
        return new UserTerm(user, term);
    }
}
