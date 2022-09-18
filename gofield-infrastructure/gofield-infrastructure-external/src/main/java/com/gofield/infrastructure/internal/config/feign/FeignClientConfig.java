package com.gofield.infrastructure.internal.config.feign;

import com.gofield.common.model.Constants;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@EnableFeignClients(basePackages = Constants.BASE_PACKAGE)
@Configuration
public class FeignClientConfig {

}
