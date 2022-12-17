package com.gofield.api.config.enummapper;

import com.gofield.common.utils.EnumMapper;
import com.gofield.domain.rds.domain.order.EOrderStatusFlag;
import com.gofield.domain.rds.domain.order.EPaymentType;
import com.gofield.domain.rds.domain.user.ESocialFlag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EnumMapperFactoryConfig {

    @Bean
    public EnumMapper createEnumMapperFactory() {
        EnumMapper enumMapperFactory = new EnumMapper();
        enumMapperFactory.put("SOCIAL", ESocialFlag.class);
        enumMapperFactory.put("ORDER_STATUS", EOrderStatusFlag.class);
        enumMapperFactory.put("ORDER_STATUS", EOrderStatusFlag.class);
        enumMapperFactory.putEnumModel("PAYMENT_TYPE", EPaymentType.class);
        return enumMapperFactory;
    }

}