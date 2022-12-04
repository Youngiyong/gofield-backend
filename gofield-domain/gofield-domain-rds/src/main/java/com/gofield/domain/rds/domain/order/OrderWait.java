
package com.gofield.domain.rds.domain.order;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.common.EEnvironmentFlag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "order_wait")
public class OrderWait {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String oid;

    @Column
    private String uuid;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String shippingAddress;

    @Column(name = "environment_flag")
    private EEnvironmentFlag environment;

    @Column
    private LocalDateTime createDate;

    @Builder
    private OrderWait(Long userId, String oid, String uuid, String content, String shippingAddress,  EEnvironmentFlag environment){
        this.userId = userId;
        this.oid = oid;
        this.uuid = uuid;
        this.shippingAddress = shippingAddress;
        this.content = content;
        this.environment = environment;
    }

    public static OrderWait newInstance(Long userId, String oid, String uuid, String content, String shippingAddress, EEnvironmentFlag environment){
        return OrderWait.builder()
                .userId(userId)
                .oid(oid)
                .uuid(uuid)
                .content(content)
                .shippingAddress(shippingAddress)
                .environment(environment)
                .build();
    }
}
