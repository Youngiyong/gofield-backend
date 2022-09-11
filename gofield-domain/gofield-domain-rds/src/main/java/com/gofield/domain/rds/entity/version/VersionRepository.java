package com.gofield.domain.rds.entity.version;

import com.gofield.domain.rds.entity.version.repository.VersionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VersionRepository extends JpaRepository<Version, Long>, VersionRepositoryCustom {
}
