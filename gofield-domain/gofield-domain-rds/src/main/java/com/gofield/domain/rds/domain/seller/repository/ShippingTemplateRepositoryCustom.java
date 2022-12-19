package com.gofield.domain.rds.domain.seller.repository;


import com.gofield.domain.rds.domain.item.ShippingTemplate;

public interface ShippingTemplateRepositoryCustom {
    ShippingTemplate findByShippingTemplateId(Long id);

}
