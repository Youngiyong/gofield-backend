package com.gofield.domain.rds.entity.version.repository;

import com.gofield.domain.rds.entity.version.Version;
import com.gofield.domain.rds.enums.EPlatformFlag;
import com.gofield.domain.rds.enums.EClientType;

public interface VersionRepositoryCustom {

    Version findByPlatformAndType(EPlatformFlag platform, EClientType type);
}
