package com.gofield.domain.rds.entity.banner;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.enums.EStatusFlag;
import com.gofield.domain.rds.enums.banner.EBannerTypeFlag;
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

    @Column
    private String description;

    @Column
    private EBannerTypeFlag type;

    @Column
    private String linkUrl;

    @Column
    private EStatusFlag status;

    @Column
    private String thumbnail;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

}
