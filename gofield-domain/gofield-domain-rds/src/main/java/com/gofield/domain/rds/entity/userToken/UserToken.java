
package com.gofield.domain.rds.entity.userToken;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.entity.userAccess.UserAccess;
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
@Table(	name = "user_token")
public class UserToken extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private UserClientDetail client;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "access_id")
    private UserAccess access;

    @Column(length = 36)
    private String refreshToken;

    @Column
    private LocalDateTime expireDate;

    private UserToken(UserClientDetail client, User user, UserAccess access, String refreshToken, LocalDateTime expireDate){
        this.client = client;
        this.user = user;
        this.access = access;
        this.refreshToken = refreshToken;
        this.expireDate = expireDate;
    }

    public static UserToken newInstance(UserClientDetail client, User user, UserAccess access, String refreshToken, LocalDateTime expireDate){
        return new UserToken(client, user, access, refreshToken, expireDate);
    }

    public void updateToken(String refreshToken, LocalDateTime expireDate) {
        this.refreshToken = refreshToken != null ? refreshToken : this.refreshToken;
        this.expireDate = expireDate;
    }

}
