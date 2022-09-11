package com.gofield.domain.rds.entity.userHasTerm;

import com.gofield.domain.rds.entity.term.Term;
import com.gofield.domain.rds.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_has_term")
public class UserHasTerm {

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
    private ZonedDateTime createDate;

    private UserHasTerm(User user, Term term){
        this.user = user;
        this.term = term;
    }

    public static UserHasTerm newInstance(User user, Term term){
        return new UserHasTerm(user, term);
    }
}
