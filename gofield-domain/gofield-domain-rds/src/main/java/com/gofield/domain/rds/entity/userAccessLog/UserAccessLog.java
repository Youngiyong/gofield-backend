
package com.gofield.domain.rds.entity.userAccessLog;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_access_log")
public class UserAccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long deviceId;

    @Column
    private String userAgent;

    @Column
    private String ip;

    @Column
    private LocalDateTime createDate;

    private UserAccessLog(Long userId, Long deviceId, String userAgent, String ip){
        this.userId = userId;
        this.deviceId = deviceId;
        this.userAgent = userAgent;
        this.ip = ip;
    }

    public static UserAccessLog newInstance(Long userId, Long deviceId, String userAgent, String ip){
        return new UserAccessLog(userId, deviceId, userAgent, ip);
    }

}
