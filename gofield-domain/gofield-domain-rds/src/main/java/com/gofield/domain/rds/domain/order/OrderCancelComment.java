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
@Table(	name = "order_cancel_comment")
public class OrderCancelComment extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    private OrderCancelComment(User user, String content){
        this.user = user;
        this.content = content;
    }

    public static OrderCancelComment newInstance(User user, String content){
        return OrderCancelComment.builder()
                .user(user)
                .content(content)
                .build();
    }

}
