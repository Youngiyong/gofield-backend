package com.gofield.infrastructure.external;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackageClasses = {
    GofieldExternalRoot.class,
})
@Configuration
public interface GofieldExternalRoot {
}
