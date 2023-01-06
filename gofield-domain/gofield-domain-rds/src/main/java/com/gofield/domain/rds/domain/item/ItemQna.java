
package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.user.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String name;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String answer;

    @Column(name = "status_flag")
    private EItemQnaStatusFlag status;

    @Column
    private Boolean isVisible;

    @Column
    private LocalDateTime answerDate;

    @Column
    private LocalDateTime deleteDate;


    private ItemQna(Item item, User user, String name, String title, String description, Boolean isVisible){
        this.item = item;
        this.user = user;
        this.name = name;
        this.title = title;
        this.description = description;
        this.isVisible = isVisible;
        this.status = EItemQnaStatusFlag.RECEIPT;
    }

    public static ItemQna newInstance(Item item, User user, String name, String title, String description, Boolean isVisible){
        return new ItemQna(item, user, name, title, description, isVisible);
    }

    public void updateReplyAnswer(String answer){
        this.answer = answer;
        this.answerDate = LocalDateTime.now();
        this.status = EItemQnaStatusFlag.COMPLETE;
    }

    public void delete(){
        this.deleteDate = LocalDateTime.now();
    }
}
