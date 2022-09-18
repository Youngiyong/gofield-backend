package com.gofield.domain.rds.entity.deviceModel;

import com.gofield.domain.rds.converter.PlatformFlagConverter;
import com.gofield.domain.rds.enums.EPlatformFlag;
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
@Table(	name = "device_model")
public class DeviceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "platform_flag", nullable = false, length = 10)
    private EPlatformFlag platform;
    @Column
    private String brand;

    @Column
    private String name;

    @Column
    private String device;

    @Column
    private String model;

    @Column
    private LocalDateTime createDate;
}
