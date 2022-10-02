package com.gofield.domain.rds.entity.version;


import com.gofield.domain.rds.converter.PlatformFlagConverter;
import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.enums.EPlatformFlag;
import com.gofield.domain.rds.enums.EClientType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "version")
public class Version extends BaseTimeEntity {

    @Column(name = "platform_flag", nullable = false, length = 10)
    private EPlatformFlag platform;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_flag")
    private EClientType type;

    @Column
    private String minVersion;

    @Column
    private String maxVersion;

    @Column
    private Integer minTrans;

    @Column
    private Integer maxTrans;

    @Column
    private String marketUrl;
}
