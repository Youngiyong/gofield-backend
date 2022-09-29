package com.gofield.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EntityScan(basePackages = {"com.tsid.domain"})
@EnableJpaRepositories(basePackages = {"com.tsid.domain"})
@SpringBootApplication
@EnableFeignClients
public class TSIDAdminApplication {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(TSIDAdminApplication.class, args);
    }
}
