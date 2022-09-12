package com.gofield.api;

import com.gofield.domain.rds.GofieldDomainRdsRoot;
import com.gofield.infrastructure.external.GofieldExternalRoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {
    GofieldDomainRdsRoot.class,
    GofieldExternalRoot.class,
    GofieldApiApplication.class,
})
public class GofieldApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GofieldApiApplication.class, args);
    }

}
