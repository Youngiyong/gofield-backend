package com.gofield.domain.rds.entity.user;


import com.gofield.domain.rds.converter.StatusFlagConverter;
import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.userAddress.UserAddress;
import com.gofield.domain.rds.entity.userHasCategory.UserHasCategory;
import com.gofield.domain.rds.entity.userHasDevice.UserHasDevice;
import com.gofield.domain.rds.entity.userHasTerm.UserHasTerm;
import com.gofield.domain.rds.entity.userSns.UserSns;
import com.gofield.domain.rds.enums.EStatusFlag;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user")
public class User extends BaseTimeEntity {
    @Column(nullable = false, length = 36)
    private String uuid;

    @Column(nullable = false, length = 50)
    private String nickName;

    @Column(length = 128, name = "thumbnail_path")
    private String thumbnail;

    @Convert(converter = StatusFlagConverter.class)
    @Column(name = "status_flag", nullable = false, length = 20)
    private EStatusFlag status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<UserSns> sns;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<UserAddress> address;

    @OneToMany(mappedBy = "term")
    private List<UserHasTerm> terms;

    @OneToMany(mappedBy = "category")
    private List<UserHasCategory> categories;

    @OneToMany(mappedBy = "device")
    private List<UserHasDevice> devices;
    @Column
    private Boolean isAlertPush;
    @Column
    private Boolean isAlertPromotion;

    @Column
    private Long createId;

    @Column
    private Long updateId;

    @Column
    private Long deleteId;

    private User(String uuid, String nickName){
        this.uuid = uuid;
        this.nickName = nickName;
        this.status = EStatusFlag.WAIT;
        this.isAlertPush = false;
        this.isAlertPromotion = false;
    }

    public void updateSign(){
        this.status = EStatusFlag.ACTIVE;
    }
    public static User newInstance(String uuid, String nickName){
        return new User(uuid, nickName);
    }

    public void update(String nickName, String thumbnail, Boolean isAlertPush, Boolean isAlertPromotion){
        this.nickName = nickName != null ? nickName : this.nickName;
        this.thumbnail = thumbnail != null ? thumbnail : this.thumbnail;
        this.isAlertPush = isAlertPush != null ? isAlertPush : this.isAlertPush;
        this.isAlertPromotion = isAlertPromotion != null ? isAlertPromotion : this.isAlertPromotion;
    }

}
