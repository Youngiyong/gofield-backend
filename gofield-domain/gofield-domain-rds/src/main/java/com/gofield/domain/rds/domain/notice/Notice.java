package com.gofield.domain.rds.domain.notice;

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
@Table(	name = "notice")
public class Notice extends BaseTimeEntity {

    @Column(length = 200, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private ENoticeTypeFlag type;

    @Column
    private Boolean isVisible;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private LocalDateTime deleteDate;

    @Builder
    private Notice(String title, String description, ENoticeTypeFlag type, Boolean isVisible){
        this.title = title;
        this.description = description;
        this.type = type;
        this.isVisible = isVisible;
    }

    public static Notice newInstance(String title, String description, ENoticeTypeFlag type, Boolean isVisible){
        return Notice.builder()
                .title(title)
                .description(description)
                .type(type)
                .isVisible(isVisible)
                .build();
    }

    public void update(String title, String description, ENoticeTypeFlag type, Boolean isVisible){
        this.title = title;
        this.description = description;
        this.type = type;
        this.isVisible = isVisible;
    }

    public void delete(){
        this.deleteDate = LocalDateTime.now();
    }

}
