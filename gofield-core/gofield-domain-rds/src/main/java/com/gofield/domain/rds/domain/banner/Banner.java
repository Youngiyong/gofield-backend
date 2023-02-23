package com.gofield.domain.rds.domain.banner;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.common.EStatusFlag;
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
@Table(	name = "banner")
public class Banner extends BaseTimeEntity {

    @Column(length = 50)
    private String title;

    @Column(length = 256)
    private String description;

    @Column(nullable = false)
    private EBannerTypeFlag type;

    @Column(length = 1024)
    private String linkUrl;

    @Column(length = 128)
    private String thumbnail;

    @Column
    private Integer sort;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private LocalDateTime deleteDate;

    @Builder
    private Banner(String title, String description, EBannerTypeFlag type, String linkUrl, String thumbnail, Integer sort){
        this.title = title;
        this.description = description;
        this.type = type;
        this.linkUrl = linkUrl;
        this.thumbnail = thumbnail;
        this.sort = sort;
    }

    public static Banner newInstance(String title, String description, EBannerTypeFlag type, String linkUrl, String thumbnail, Integer sort){
        return Banner.builder()
                .title(title)
                .description(description)
                .type(type)
                .linkUrl(linkUrl)
                .thumbnail(thumbnail)
                .sort(sort)
                .build();
    }

    public void update(String title, String description, EBannerTypeFlag type, String linkUrl, String thumbnail, Integer sort){
        this.title = title;
        this.description = description;
        this.type = type;
        this.linkUrl = linkUrl;
        this.thumbnail = thumbnail;
        this.sort = sort;
    }

    public void delete(){
        this.deleteDate = LocalDateTime.now();
    }

}
