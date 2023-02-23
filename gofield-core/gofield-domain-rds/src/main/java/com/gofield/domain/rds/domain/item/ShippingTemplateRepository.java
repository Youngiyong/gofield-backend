package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ShippingTemplateRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingTemplateRepository extends JpaRepository<ShippingTemplate, Long>, ShippingTemplateRepositoryCustom {
}
