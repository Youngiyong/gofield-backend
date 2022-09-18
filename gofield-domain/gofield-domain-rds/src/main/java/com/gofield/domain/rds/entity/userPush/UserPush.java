package com.gofield.domain.rds.entity.userPush;

import com.gofield.domain.rds.converter.PlatformFlagConverter;
import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.enums.EPlatformFlag;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_push")
public class UserPush extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 256, nullable = false)
    private String pushKey;

    @Column(name = "platform_flag", nullable = false, length = 10)
    private EPlatformFlag platform;

    @Column
    private LocalDateTime createDate;

    @Column
    private LocalDateTime lastLoginDate;

    private UserPush(User user, String pushKey, EPlatformFlag platform){
        this.user = user;
        this.pushKey = pushKey;
        this.platform = platform;
    }

    public static UserPush newInstance(User user, String pushKey, EPlatformFlag platform){
        return new UserPush(user, pushKey, platform);
    }

    public void update(String pushKey){
        this.pushKey = pushKey != null ? pushKey : this.pushKey;
        this.lastLoginDate = LocalDateTime.now();
    }

}
