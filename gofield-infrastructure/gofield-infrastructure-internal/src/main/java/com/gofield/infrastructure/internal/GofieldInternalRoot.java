package com.gofield.infrastructure.internal;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackageClasses = {
    GofieldInternalRoot.class,
})
@Configuration
public interface GofieldInternalRoot {
}
