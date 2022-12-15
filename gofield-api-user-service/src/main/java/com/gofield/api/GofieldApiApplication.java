package com.gofield.api;

import com.gofield.domain.rds.GofieldDomainRdsRoot;
import com.gofield.infrastructure.external.GofieldExternalRoot;
import com.gofield.infrastructure.s3.GofieldS3Root;
import com.gofield.infrastructure.sqs.GofieldSqsRoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackageClasses = {
    GofieldDomainRdsRoot.class, GofieldExternalRoot.class, GofieldS3Root.class,
    GofieldApiApplication.class, GofieldSqsRoot.class
})
public class GofieldApiApplication {
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(GofieldApiApplication.class, args);
    }
}
