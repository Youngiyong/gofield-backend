
package com.gofield.domain.rds.domain.user;

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
public class UserAccessLog {

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

    private UserAccessLog(Long userId, String userAgent, String ip){
        this.userId = userId;
        this.userAgent = userAgent;
        this.ip = ip;
    }

    public static UserAccessLog newInstance(Long userId, String userAgent, String ip){
        return new UserAccessLog(userId, userAgent, ip);
    }

}
