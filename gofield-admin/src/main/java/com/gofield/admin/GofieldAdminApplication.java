package com.gofield.admin;

import com.gofield.domain.rds.GofieldDomainRdsRoot;
import com.gofield.infrastructure.external.GofieldExternalRoot;
import com.gofield.infrastructure.internal.GofieldInternalRoot;
import com.gofield.infrastructure.s3.GofieldS3Root;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackageClasses = {
        GofieldDomainRdsRoot.class,  GofieldExternalRoot.class, GofieldS3Root.class,
        GofieldAdminApplication.class
})
public class GofieldAdminApplication {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(GofieldAdminApplication.class, args);
    }
}
