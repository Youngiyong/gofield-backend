package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "order_cancel_comment")
public class OrderCancelComment extends BaseTimeEntity {

    @Column
    private Long cancelId;

    @Column
    private Long userId;

    @Column
    private String content;

    @Column
    private LocalDateTime deleteDate;

    @Builder
    private OrderCancelComment(Long cancelId, Long userId, String content){
        this.cancelId = cancelId;
        this.userId = userId;
        this.content = content;
    }

    public static OrderCancelComment newInstance(Long cancelId, Long userId, String content){
        return OrderCancelComment.builder()
                .cancelId(cancelId)
                .userId(userId)
                .content(content)
                .build();
    }

}
