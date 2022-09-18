package com.gofield.domain.rds.entity.userHasCategory;

import com.gofield.domain.rds.entity.category.Category;
import com.gofield.domain.rds.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_has_category")
public class UserHasCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private LocalDateTime createDate;

    private UserHasCategory(User user, Category category){
        this.user = user;
        this.category = category;
    }

    public static UserHasCategory newInstance(User user, Category category){
        return new UserHasCategory(user, category);
    }
}
