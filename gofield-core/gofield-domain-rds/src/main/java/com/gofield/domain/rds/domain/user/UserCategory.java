package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.item.Category;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_has_category")
public class UserCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private LocalDateTime createDate;

    private UserCategory(User user, Category category){
        this.user = user;
        this.category = category;
    }

    public static UserCategory newInstance(User user, Category category){
        return new UserCategory(user, category);
    }
}
