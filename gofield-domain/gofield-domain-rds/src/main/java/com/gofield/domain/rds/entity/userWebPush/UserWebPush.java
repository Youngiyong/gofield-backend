package com.gofield.domain.rds.entity.userWebPush;

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
public class UserWebPush {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 256, nullable = false)
    private String pushKey;
    @Column
    private LocalDateTime createDate;

    @Column
    private LocalDateTime updateDate;
    private UserWebPush(User user, String pushKey){
        this.user = user;
        this.pushKey = pushKey;
        this.createDate = LocalDateTime.now();
    }

    public static UserWebPush newInstance(User user, String pushKey){
        return new UserWebPush(user, pushKey);
    }

    public void update(String pushKey){
        this.pushKey = pushKey != null ? pushKey : this.pushKey;
        this.updateDate = LocalDateTime.now();
    }

}
