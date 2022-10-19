package com.gofield.domain.rds.entity.userLikeItem;

import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.entity.term.Term;
import com.gofield.domain.rds.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_like_item")
public class UserLikeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column
    private LocalDateTime createDate;

    private UserLikeItem(User user, Item item){
        this.user = user;
        this.item = item;
    }

    public static UserLikeItem newInstance(User user, Item item){
        return new UserLikeItem(user, item);
    }
}
