
package com.gofield.domain.rds.entity.userClientDetail;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_client_detail")
public class UserClientDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 256, nullable = false)
    private String clientId;

    @Column(length = 256, nullable = false)
    private String clientSecret;

    @Column(length = 300, nullable = false)
    private String scope;

    @Column(nullable = false)
    private Long accessTokenValidity;

    @Column(nullable = false)
    private Long refreshTokenValidity;
}
