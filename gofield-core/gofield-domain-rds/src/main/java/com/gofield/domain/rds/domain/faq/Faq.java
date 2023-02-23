package com.gofield.domain.rds.domain.faq;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "faq")
public class Faq extends BaseTimeEntity {

    @Column(length = 500, nullable = false)
    private String question;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String answer;

    @Column(nullable = false, name = "type_flag")
    private EFaqTypeFlag type;

    @Column
    private Boolean isUse;

    @Column
    private LocalDateTime deleteDate;

    @Builder
    private Faq(String question, String answer, EFaqTypeFlag type, Boolean isUse){
        this.question = question;
        this.answer = answer;
        this.type = type;
        this.isUse = isUse;
    }

    public static Faq newInstance(String question, String answer, EFaqTypeFlag type, Boolean isUse){
        return Faq.builder()
                .question(question)
                .answer(answer)
                .type(type)
                .isUse(isUse)
                .build();
    }

    public void update(String question, String answer, EFaqTypeFlag type, Boolean isUse){
        this.question = question;
        this.answer = answer;
        this.type = type;
        this.isUse = isUse;
    }

    public void delete(){
        this.deleteDate = LocalDateTime.now();
    }

}
