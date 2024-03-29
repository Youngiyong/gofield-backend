package com.gofield.domain.rds.domain.user;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.common.EStatusFlag;
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

    @Column
    private String email;

    @Column
    private Integer weight;

    @Column
    private Integer height;

    @Column(name = "status_flag", nullable = false, length = 20)
    private EStatusFlag status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSns> sns;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAddress> address;

    @OneToMany(mappedBy = "term")
    private List<UserTerm> terms;

    @OneToMany(mappedBy = "category")
    private List<UserCategory> categories;

    @OneToMany(mappedBy = "user")
    private List<UserLikeItem> item;

    @Column
    private Boolean isAlertPush;
    @Column
    private Boolean isAlertPromotion;

    @Column
    private LocalDateTime deleteDate;

    private User(String uuid, String name, String email, String nickName){
        this.uuid = uuid;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.status = EStatusFlag.WAIT;
        this.isAlertPush = false;
        this.isAlertPromotion = false;
    }


    public static User newNonMemberInstance() { return new User("nonMember","비회원", "youn9354@naver.com", "비회원"); }
    public static User newInstance(String uuid, String name, String email, String nickName){
        return new User(uuid, name, email, nickName);
    }

    public void updateProfile(String name, String nickName, String thumbnail, Integer weight, Integer height){
        this.name = name != null ? name : this.name;
        this.nickName = nickName;
        this.thumbnail = thumbnail;
        this.weight = weight;
        this.height = height;

    }
    public void updateSign(Integer weight, Integer height){
        this.weight = weight;
        this.height = height;
        this.status = EStatusFlag.ACTIVE;
    }

    public void updateTel(String tel){
        this.tel = tel;
    }


    public void withDraw(){
        this.status = EStatusFlag.DELETE;
        this.deleteDate = LocalDateTime.now();
    }

    public void updateAdmin(String name, String nickName, String email, EStatusFlag status){
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.status = status;
    }


    public void updateUserAlertPromotion(Boolean isAlertPromotion){
        this.isAlertPromotion = isAlertPromotion;
    }
}
