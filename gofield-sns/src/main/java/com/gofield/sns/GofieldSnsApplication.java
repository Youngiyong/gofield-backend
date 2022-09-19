package com.gofield.sns;

import com.gofield.infrastructure.external.GofieldExternalRoot;
import com.gofield.infrastructure.sqs.GofieldSqsRoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {
    GofieldSnsApplication.class , GofieldExternalRoot.class, GofieldSqsRoot.class
})
public class GofieldSnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GofieldSnsApplication.class, args);
    }

}
