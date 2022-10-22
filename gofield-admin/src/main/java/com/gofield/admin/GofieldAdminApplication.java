package com.gofield.admin;

import com.gofield.domain.rds.GofieldDomainRdsRoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackageClasses = {
        GofieldDomainRdsRoot.class,
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
