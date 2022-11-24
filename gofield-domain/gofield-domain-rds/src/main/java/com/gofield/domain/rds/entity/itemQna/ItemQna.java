
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

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String answer;

    @Column(name = "status_flag")
    private EQnaStatusFlag status;

    @Column
    private Boolean isVisible;

    @Column
    private LocalDateTime answerDate;


    private ItemQna(Item item, User user, String title, String description, Boolean isVisible){
        this.item = item;
        this.user = user;
        this.title = title;
        this.description = description;
        this.isVisible = isVisible;
        this.status = EQnaStatusFlag.RECEIPT;
    }

    public static ItemQna newInstance(Item item, User user, String title, String description, Boolean isVisible){
        return new ItemQna(item, user, title, description, isVisible);
    }

    public void updateReplyAnswer(String answer){
        this.answer = answer;
        this.answerDate = LocalDateTime.now();
        this.status = EQnaStatusFlag.COMPLETE;
    }
}
