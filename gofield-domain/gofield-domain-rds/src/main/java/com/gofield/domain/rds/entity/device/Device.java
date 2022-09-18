package com.gofield.domain.rds.entity.device;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.userHasDevice.UserHasDevice;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DynamicUpdate
@DynamicInsert
@Table(	name = "device")
public class Device extends BaseTimeEntity {
    @Column(length = 64)
    private String deviceKey;

    @Column(length = 10)
    private String version;

    @Column(length = 100, name = "device")
    private String model;

    @Column(length = 10)
    private String platform;


    @Column(length = 100)
    private String adid;

    @Column
    private Boolean isRooted;

    @Column(length = 2)
    private String language;


    @OneToMany(mappedBy = "user")
    private List<UserHasDevice> user;

    private Device(String deviceKey, String version, String model, String platform){
        this.deviceKey = deviceKey;
        this.version = version;
        this.model = model;
        this.platform = platform;
    }

    public static Device newInstance(String deviceKey, String version, String model, String platform){
        return new Device(deviceKey, version, model, platform);
    }

}
