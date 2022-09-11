package com.gofield.domain.rds.entity.userHasDevice;

import com.gofield.domain.rds.entity.device.Device;
import com.gofield.domain.rds.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_has_device")
public class UserHasDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @Column
    private LocalDateTime createDate;

    private UserHasDevice(User user, Device device){
        this.user = user;
        this.device = device;
    }

    public static UserHasDevice newInstance(User user, Device device){
        return new UserHasDevice(user, device);
    }
}
