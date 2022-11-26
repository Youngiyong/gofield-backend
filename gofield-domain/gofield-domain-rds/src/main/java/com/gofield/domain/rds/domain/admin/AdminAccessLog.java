
package com.gofield.domain.rds.domain.admin;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "admin_access_log")
public class AdminAccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column
    private String userAgent;

    @Column
    private String ip;

    @Column
    @CreatedDate
    private LocalDateTime createDate;

    private AdminAccessLog(Admin admin, String userAgent, String ip){
        this.ip = ip;
        this.admin = admin;
        this.userAgent = userAgent;
    }

    public AdminAccessLog newInstance(Admin admin, String userAgent, String ip){
        return new AdminAccessLog(admin, userAgent, ip);
    }
}
