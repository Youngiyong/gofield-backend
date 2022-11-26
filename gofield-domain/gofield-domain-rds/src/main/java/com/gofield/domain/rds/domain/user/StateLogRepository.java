package com.gofield.domain.rds.domain.user;
import com.gofield.domain.rds.domain.user.repository.StateLogRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateLogRepository extends JpaRepository<StateLog, Long>, StateLogRepositoryCustom {
}
