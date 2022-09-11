package com.gofield.domain.rds.entity.serverErrorLog;

import com.gofield.domain.rds.enums.EServerType;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "server_error_log")
public class ServerErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private EServerType server;

    @Column
    private Long userId;

    @Column(name = "os_flag")
    private String os;

    @Column
    private String osVersion;

    @Column
    private String url;

    @Column
    private String version;

    @Column
    private String method;

    @Column(columnDefinition = "TEXT")
    private String request;

    @Column(columnDefinition = "TEXT")
    private String message;
}
