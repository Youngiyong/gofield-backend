package com.gofield.domain.rds.domain.admin;

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
@Table(	name = "admin_role_has_menu")
public class AdminRoleHasMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private AdminRole role;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private AdminMenu menu;

    @Column
    private LocalDateTime createDate;
}
