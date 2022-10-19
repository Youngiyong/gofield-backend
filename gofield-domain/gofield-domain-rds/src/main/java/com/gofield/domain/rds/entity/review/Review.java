
package com.gofield.domain.rds.entity.review;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "review")
public class Review extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String content;

    @Column
    private Integer weight;

    @Column
    private Integer height;

    @Column
    private Float rate;

    private Review(Item item, User user, String content, Integer weight, Integer height, Float rate){
        this.item = item;
        this.user = user;
        this.content = content;
        this.weight = weight;
        this.height = height;
        this.rate = rate;
    }

    public static Review newInstance(Item item, User user, String content, Integer weight, Integer height, Float rate){
        return new Review(item, user, content, weight, height, rate);
    }


}
