package com.gofield.infrastructure.s3;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackageClasses = {
    GofieldExternalRoot.class,
})
@Configuration
public interface GofieldExternalRoot {
}
