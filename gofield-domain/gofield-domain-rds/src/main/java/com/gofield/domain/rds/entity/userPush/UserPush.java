package com.gofield.domain.rds.entity.userPush;

import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.enums.EPlatformFlag;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_push")
public class UserPush {
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
    private LocalDateTime updateDate;
    private UserPush(User user, String pushKey, EPlatformFlag platform){
        this.user = user;
        this.pushKey = pushKey;
        this.platform = platform;
        this.createDate = LocalDateTime.now();
    }

    public static UserPush newInstance(User user, String pushKey, EPlatformFlag platform){
        return new UserPush(user, pushKey, platform);
    }

    public void update(String pushKey){
        this.pushKey = pushKey != null ? pushKey : this.pushKey;
        this.updateDate = LocalDateTime.now();
    }

}
