package com.gofield.api.config.enummapper;

import com.gofield.common.utils.EnumMapper;
import com.gofield.domain.rds.domain.order.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EnumMapperFactoryConfig {

    @Bean
    public EnumMapper createEnumMapperFactory() {
        EnumMapper enumMapperFactory = new EnumMapper();
        enumMapperFactory.putEnumModel("PAYMENT_TYPE", EPaymentType.class);
        enumMapperFactory.putEnumCodeModel("ORDER_STATUS", EOrderStatusFlag.class);
        enumMapperFactory.putEnumCodeModel("ORDER_SHIPPING_STATUS", EOrderShippingStatusFlag.class);
        enumMapperFactory.putEnumCodeModel("ORDER_ITEM_STATUS", EOrderItemStatusFlag.class);
        enumMapperFactory.putEnumCodeModel("ORDER_CANCEL_STATUS", EOrderCancelStatusFlag.class);
        enumMapperFactory.putEnumCodeModel("ORDER_CANCEL_REASON", EOrderCancelReasonFlag.class);
        enumMapperFactory.putEnumCodeModel("ORDER_CHANGE_STATUS", EOrderChangeStatusFlag.class);
        enumMapperFactory.putEnumCodeModel("ORDER_CHANGE_REASON", EOrderChangeReasonFlag.class);
        return enumMapperFactory;
    }

}