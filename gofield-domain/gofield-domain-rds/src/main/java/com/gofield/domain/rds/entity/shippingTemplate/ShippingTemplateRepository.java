package com.gofield.domain.rds.entity.shippingTemplate;

import com.gofield.domain.rds.entity.shippingTemplate.repository.ShippingTemplateRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingTemplateRepository extends JpaRepository<ShippingTemplate, Long>, ShippingTemplateRepositoryCustom {
}
