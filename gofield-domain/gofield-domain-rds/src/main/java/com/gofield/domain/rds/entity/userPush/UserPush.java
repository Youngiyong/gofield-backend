package com.gofield.domain.rds.entity.userPush;

import com.gofield.domain.rds.converter.PlatformFlagConverter;
import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.enums.EPlatformFlag;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_push")
public class UserPush extends BaseTimeEntity {

    @Column
    private Long userId;

    @Column(length = 256, nullable = false)
    private String pushKey;

    @Column(name = "platform_flag", nullable = false, length = 10)
    private EPlatformFlag platform;
}
