package com.gofield.sns;

import com.gofield.infrastructure.external.GofieldExternalRoot;
import com.gofield.infrastructure.sqs.GofieldSqsRoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackageClasses = {
    GofieldSnsApplication.class , GofieldExternalRoot.class, GofieldSqsRoot.class
})
public class GofieldSnsApplication {

    @PostConstruct
    public void started() {
        System.out.println("현재시각: " + new Date());
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Seoul")));
        System.out.println("현재시각: " + new Date());
    }
    public static void main(String[] args) {
        SpringApplication.run(GofieldSnsApplication.class, args);
    }
}
