package com.gofield.domain.rds.entity.user;


import com.gofield.domain.rds.converter.StatusFlagConverter;
import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.userAddress.UserAddress;
import com.gofield.domain.rds.entity.userHasCategory.UserHasCategory;
import com.gofield.domain.rds.entity.userHasDevice.UserHasDevice;
import com.gofield.domain.rds.entity.userHasTerm.UserHasTerm;
import com.gofield.domain.rds.entity.userKeyword.UserKeyword;
import com.gofield.domain.rds.entity.userSns.UserSns;
import com.gofield.domain.rds.enums.EStatusFlag;
import lombok.*;
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
@Table(	name = "user")
public class User extends BaseTimeEntity {
    @Column(nullable = false, length = 32)
    private String uuid;

    @Column(length = 36)
    private String name;

    @Column(nullable = false, length = 50)
    private String nickName;

    @Column(length = 20)
    private String tel;

    @Column(length = 128, name = "thumbnail_path")
    private String thumbnail;

    @Column(name = "status_flag", nullable = false, length = 20)
    private EStatusFlag status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSns> sns;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserKeyword> keyword;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
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
    private LocalDateTime deleteDate;

    private User(String uuid, String nickName){
        this.uuid = uuid;
        this.nickName = nickName;
        this.status = EStatusFlag.WAIT;
        this.isAlertPush = false;
        this.isAlertPromotion = false;
    }

    public static User newInstance(String uuid, String nickName){
        return new User(uuid, nickName);
    }

    public void updateProfile(String name, String nickName, String thumbnail, Boolean isAlertPromotion){
        this.name =  name != null ? name : this.name;
        this.nickName = nickName != null ? nickName : this.nickName;
        this.thumbnail = thumbnail != null ? thumbnail : this.thumbnail;
        this.isAlertPromotion = isAlertPromotion != null ? isAlertPromotion : this.isAlertPromotion;

    }
    public void updateSign(){
        this.status = EStatusFlag.ACTIVE;
    }

    public void updateTel(String tel){
        this.tel = tel != null ? tel : this.tel;
    }

    public void update(String nickName, String thumbnail, Boolean isAlertPush, Boolean isAlertPromotion){
        this.nickName = nickName != null ? nickName : this.nickName;
        this.thumbnail = thumbnail != null ? thumbnail : this.thumbnail;
        this.isAlertPush = isAlertPush != null ? isAlertPush : this.isAlertPush;
        this.isAlertPromotion = isAlertPromotion != null ? isAlertPromotion : this.isAlertPromotion;
    }

    public void withDraw(){
        this.status = EStatusFlag.DELETE;
        this.deleteDate = LocalDateTime.now();
    }

}
