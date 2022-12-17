package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
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
@Table(	name = "order_change_comment")
public class OrderChangeComment extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "change_id")
    private OrderChange change;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    private OrderChangeComment(OrderChange change, User user, String content){
        this.change = change;
        this.user = user;
        this.content = content;
    }

    public static OrderChangeComment newInstance(OrderChange change, User user, String content){
        return OrderChangeComment.builder()
                .change(change)
                .user(user)
                .content(content)
                .build();
    }

}
