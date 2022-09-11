package com.gofield.domain.rds.entity.serverStatus.repository;

import com.gofield.domain.rds.entity.serverStatus.ServerStatus;
import com.gofield.domain.rds.enums.EServerType;

public interface ServerStatusRepositoryCustom {

    ServerStatus findByServerType(EServerType serverType);
}
