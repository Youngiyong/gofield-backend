package com.gofield.domain.rds.domain.banner;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.common.EStatusFlag;
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

    @Column(nullable = false)
    private EStatusFlag status;

    @Column(length = 128)
    private String thumbnail;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

}
