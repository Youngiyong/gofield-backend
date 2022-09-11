package com.gofield.domain.rds.config.jpa;

import com.gofield.domain.rds.GofieldDomainRdsRoot;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {GofieldDomainRdsRoot.class})
@EnableJpaRepositories(basePackageClasses = {GofieldDomainRdsRoot.class})
@EnableJpaAuditing
public class JpaConfig {

}
