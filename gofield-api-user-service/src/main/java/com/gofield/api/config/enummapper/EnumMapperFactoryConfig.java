package com.gofield.api.config.enummapper;

import com.gofield.common.utils.EnumMapper;
import com.gofield.domain.rds.domain.item.EItemBundleSort;
import com.gofield.domain.rds.domain.item.EItemSort;
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
        enumMapperFactory.putEnumModel("ORDER_CANCEL_REASON", EOrderCancelClientReason.class);
        enumMapperFactory.putEnumCodeModel("ORDER_CANCEL_ALL_REASON", EOrderCancelReasonFlag.class);
        enumMapperFactory.putEnumModel("ORDER_CHANGE_REASON", EOrderChangeClientReason.class);
        enumMapperFactory.putEnumModel("ORDER_RETURN_REASON", EOrderCancelClientReason.class);
        enumMapperFactory.putEnumModel("ITEM_BUNDLE_SORT", EItemBundleSort.class);
        enumMapperFactory.putEnumModel("ITEM_SORT", EItemSort.class);
        return enumMapperFactory;
    }

}