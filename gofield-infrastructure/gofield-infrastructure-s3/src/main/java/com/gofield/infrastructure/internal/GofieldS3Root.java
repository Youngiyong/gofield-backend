package com.gofield.infrastructure.internal;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackageClasses = {
    GofieldS3Root.class,
})
@Configuration
public interface GofieldS3Root {
}
