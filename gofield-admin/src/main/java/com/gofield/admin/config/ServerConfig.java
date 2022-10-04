package com.gofield.admin.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
