package com.gofield.domain.rds.entity.version.repository;

import com.gofield.domain.rds.entity.version.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VersionRepository extends JpaRepository<Version, Long>, VersionRepositoryCustom {
}
