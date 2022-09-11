package com.gofield.infrastructure.external;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = {
    GofieldExternalRoot.class,
})
public interface GofieldExternalRoot {
}
