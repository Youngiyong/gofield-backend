package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.seller.Seller;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SellerShippingResponse {
    private Long id;
    private String tel;
    private String name;
    private ShippingTemplateResponse shippingTemplate;

    @Builder
    private SellerShippingResponse(Long id, String tel, String name, ShippingTemplateResponse shippingTemplate){
        this.id = id;
        this.tel = tel;
        this.name = name;
        this.shippingTemplate = shippingTemplate;
    }

    public static SellerShippingResponse of(Seller seller, ShippingTemplateResponse shippingTemplate){
        return SellerShippingResponse.builder()
                .id(seller.getId())
                .tel(seller.getTel())
                .name(seller.getName())
                .shippingTemplate(shippingTemplate)
                .build();
    }
}
