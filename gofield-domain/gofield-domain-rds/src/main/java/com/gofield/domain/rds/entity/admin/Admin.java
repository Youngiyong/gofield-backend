package com.gofield.domain.rds.entity.admin;

import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.adminRole.AdminRole;
import com.gofield.domain.rds.enums.EStatusFlag;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@DynamicInsert
@Entity
@Table(	name = "admin")
public class Admin extends BaseTimeEntity {

    @Column(nullable = false, length = 32)
    private String name;

    @Column(nullable = false, length = 32)
    private String username;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(length = 64)
    private String email;

    @Column(length = 64)
    private String tel;

    @Column(nullable = false, length = 36)
    private String uuid;

    @Column(length = 128, name = "thumbnail_path")
    private String thumbnail;

    @Column(name = "status_flag", nullable = false, length = 20)
    private EStatusFlag status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private AdminRole adminRole;

    public static Admin newInstance(String name, String username, String password, String email, String tel, AdminRole adminRole) {
        return Admin.builder()
                .name(name)
                .username(username)
                .password(password)
                .email(email)
                .tel(tel)
                .adminRole(adminRole)
                .status(EStatusFlag.ACTIVE)
                .uuid(RandomUtils.makeRandomUuid())
                .build();
    }

}
