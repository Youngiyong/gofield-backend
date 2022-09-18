package com.gofield.domain.rds.entity.userSns;

import com.gofield.domain.rds.converter.SocialFlagConverter;
import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.enums.ESocialFlag;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_sns")
public class UserSns extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 128)
    private String uniqueId;

    @Column(name = "social_flag", nullable = false)
    private ESocialFlag social;

    private UserSns(User user, String uniqueId, ESocialFlag social){
        this.user = user;
        this.uniqueId = uniqueId;
        this.social = social;
    }

    public static UserSns newInstance(User user, String uniqueId, ESocialFlag social) {
        return new UserSns(user, uniqueId, social);
    }
}
