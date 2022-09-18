package com.gofield.sns;

import com.gofield.infrastructure.external.GofieldExternalRoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {
    GofieldSnsApplication.class , GofieldExternalRoot.class
})
public class GofieldSnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GofieldSnsApplication.class, args);
    }

}
