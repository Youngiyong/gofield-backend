
package com.gofield.domain.rds.entity.userWebToken;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.entity.userClientDetail.UserClientDetail;
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
@Table(	name = "user_web_token")
public class UserWebToken extends BaseTimeEntity {

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

    private UserWebToken(UserClientDetail client, User user, String accessToken, String refreshToken, LocalDateTime expireDate){
        this.client = client;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireDate = expireDate;
    }

    public static UserWebToken newInstance(UserClientDetail client, User user, String accessToken, String refreshToken, LocalDateTime expireDate){
        return new UserWebToken(client, user, accessToken, refreshToken, expireDate);
    }

    public void updateToken(String accessToken, String refreshToken, LocalDateTime expireDate) {
        this.accessToken = accessToken != null ? accessToken : this.accessToken;
        this.refreshToken = refreshToken != null ? refreshToken : this.refreshToken;
        this.expireDate = expireDate;
    }

}
