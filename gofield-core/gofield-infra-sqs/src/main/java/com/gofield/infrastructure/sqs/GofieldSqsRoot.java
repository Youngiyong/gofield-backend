package com.gofield.infrastructure.sqs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackageClasses = {
        GofieldSqsRoot.class,
})
@Configuration
public interface GofieldSqsRoot {
}
