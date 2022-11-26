
package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
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
@Table(	name = "user_token")
public class UserToken extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private UserClientDetail client;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 312)
    private String accessToken;
    @Column(length = 36)
    private String refreshToken;

    @Column
    private LocalDateTime expireDate;

    private UserToken(UserClientDetail client, User user, String accessToken, String refreshToken, LocalDateTime expireDate){
        this.client = client;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireDate = expireDate;
    }

    public static UserToken newInstance(UserClientDetail client, User user, String accessToken, String refreshToken, LocalDateTime expireDate){
        return new UserToken(client, user, accessToken, refreshToken, expireDate);
    }

    public void updateToken(String accessToken, String refreshToken, LocalDateTime expireDate) {
        this.accessToken = accessToken != null ? accessToken : this.accessToken;
        this.refreshToken = refreshToken != null ? refreshToken : this.refreshToken;
        this.expireDate = expireDate;
    }

}
