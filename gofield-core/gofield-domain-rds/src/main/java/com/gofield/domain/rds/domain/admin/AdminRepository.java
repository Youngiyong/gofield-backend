package com.gofield.domain.rds.domain.admin;

import com.gofield.domain.rds.domain.admin.repository.AdminRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, AdminRepositoryCustom {
}
