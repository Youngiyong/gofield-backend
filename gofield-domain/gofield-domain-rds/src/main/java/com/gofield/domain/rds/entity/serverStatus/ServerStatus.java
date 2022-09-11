package com.gofield.domain.rds.entity.serverStatus;


import com.gofield.domain.rds.enums.EServerType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "server_status")
public class ServerStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private EServerType server;

    @Column
    private String message;

    @Column
    private Boolean isActive;

    @Column
    private LocalDateTime updateDate;
}

