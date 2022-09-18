package com.gofield.domain.rds.entity.userAccess;

import com.gofield.domain.rds.entity.userAccess.repository.UserAccessRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccessRepository extends JpaRepository<UserAccess, Long>, UserAccessRepositoryCustom {
}
