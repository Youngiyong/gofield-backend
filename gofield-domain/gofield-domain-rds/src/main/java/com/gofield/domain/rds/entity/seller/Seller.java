package com.gofield.domain.rds.entity.seller;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.userAddress.UserAddress;
import com.gofield.domain.rds.entity.userHasCategory.UserHasCategory;
import com.gofield.domain.rds.entity.userHasDevice.UserHasDevice;
import com.gofield.domain.rds.entity.userHasTerm.UserHasTerm;
import com.gofield.domain.rds.entity.userKeyword.UserKeyword;
import com.gofield.domain.rds.entity.userSns.UserSns;
import com.gofield.domain.rds.enums.ESocialFlag;
import com.gofield.domain.rds.enums.EStatusFlag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "seller")
public class Seller extends BaseTimeEntity {
    @Column(nullable = false, length = 36)
    private String uuid;

    @Column(length = 128)
    private String uniqueId;

    @Column(name = "social_flag", nullable = false)
    private ESocialFlag social;

    @Column(length = 36)
    private String name;
    @Column(nullable = false, length = 50)
    private String nickName;

    @Column(length = 20)
    private String tel;

    @Column(length = 50)
    private String email;

    @Column(length = 128, name = "thumbnail_path")
    private String thumbnail;

    @Column(name = "status_flag", nullable = false, length = 20)
    private EStatusFlag status;

    private Seller(String uniqueId, ESocialFlag social, String uuid, String nickName){
        this.uniqueId = uniqueId;
        this.social = social;
        this.uuid = uuid;
        this.nickName = nickName;
        this.status = EStatusFlag.WAIT;
    }

    public static Seller newInstance(String uniqueId, ESocialFlag social, String uuid, String nickName){
        return new Seller(uniqueId, social, uuid, nickName);
    }


}
