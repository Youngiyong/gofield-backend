package com.gofield.domain.rds.entity.adminRole;


import com.gofield.domain.rds.entity.adminRoleHasMenu.AdminRoleHasMenu;
import com.gofield.domain.rds.enums.admin.EAdminRole;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "admin_role")
public class AdminRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private EAdminRole role;

    @Column
    private String description;

    @OneToMany(mappedBy = "menu")
    private List<AdminRoleHasMenu> menu;

}
