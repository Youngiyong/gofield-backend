
package com.gofield.domain.rds.entity.userAccess;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.device.Device;
import com.gofield.domain.rds.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_access")
public class UserAccess extends BaseTimeEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(length = 64)
    private String accessKey;

    private UserAccess(User user, Device device, String accessKey){
        this.user = user;
        this.device = device;
        this.accessKey = accessKey;
    }

    public static UserAccess newInstance(User user, Device device, String accessKey){
        return new UserAccess(user, device, accessKey);
    }

    public void update(String accessKey) {
        this.accessKey = accessKey != null ? accessKey : this.accessKey;
    }
}
