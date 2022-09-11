
package com.gofield.domain.rds.entity.userToken;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_token")
public class UserToken extends BaseTimeEntity {

    @Column
    private Long clientId;

    @Column
    private Long userId;

    @Column(length = 36)
    private String refreshToken;

    @Column
    private LocalDateTime expireDate;

    public void updateToken(String refreshToken, LocalDateTime expireDate) {
        this.refreshToken = refreshToken != null ? refreshToken : this.refreshToken;
        this.expireDate = expireDate;
    }

    public static UserToken newInstance(Long clientId, Long userId, String refreshToken, LocalDateTime expireDate){
        return new UserToken(clientId, userId, refreshToken, expireDate);
    }
    private UserToken(Long clientId, Long userId, String refreshToken, LocalDateTime expireDate){
        this.clientId = clientId;
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.expireDate = expireDate;
    }
}
