
package com.gofield.domain.rds.entity.userWebAccessLog;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_web_access_log")
public class UserWebAccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String userAgent;

    @Column
    private String ip;

    @CreatedDate
    private LocalDateTime createDate;

    private UserWebAccessLog(Long userId, String userAgent, String ip){
        this.userId = userId;
        this.userAgent = userAgent;
        this.ip = ip;
    }

    public static UserWebAccessLog newInstance(Long userId, String userAgent, String ip){
        return new UserWebAccessLog(userId, userAgent, ip);
    }

}
