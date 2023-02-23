
package com.gofield.domain.rds.domain.admin;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.common.EStatusFlag;
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
@Table(	name = "admin_menu")
public class AdminMenu extends BaseTimeEntity {

    @Column(length = 20, name = "menu_name")
    private String name;

    @Column(length = 30, name = "menu_code")
    private String code;

    @Column
    private Integer sort;

    @Column(name = "status_flag", nullable = false, length = 20)
    private EStatusFlag status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private AdminMenu parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<AdminMenu> children;

    @OneToMany(mappedBy = "role")
    private List<AdminRoleHasMenu> role;

}
