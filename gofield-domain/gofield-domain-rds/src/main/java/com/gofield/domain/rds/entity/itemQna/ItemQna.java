
package com.gofield.domain.rds.entity.itemQna;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.enums.qna.EQnaStatusFlag;
import lombok.AccessLevel;
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
@Table(	name = "item_qna")
public class ItemQna extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status_flag")
    private EQnaStatusFlag status;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String name;

    @Column
    private Boolean isVisible;

    @Column
    private String answer;

    @Column
    private Long answerId;

    @Column
    private LocalDateTime answerDate;

    private ItemQna(Item item, User user, String title, String content, String name, Boolean isVisible){
        this.item = item;
        this.user = user;
        this.title = title;
        this.content = content;
        this.name = name;
        this.isVisible = isVisible;
    }

    public static ItemQna newInstance(Item item, User user, String title, String content, String name, Boolean isVisible){
        return new ItemQna(item, user, title, content, name, isVisible);
    }

    public void updateVisible(Boolean isVisible){
        this.isVisible = isVisible;
    }

    public void updateReplyAnswer(String answer, Long answerId){
        this.answer = answer;
        this.answerId = answerId;
        this.answerDate = LocalDateTime.now();
        this.status = EQnaStatusFlag.COMPLETE;
    }
}
