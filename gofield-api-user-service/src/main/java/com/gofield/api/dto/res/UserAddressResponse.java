package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.user.UserAddress;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class UserAddressResponse {
    private Long id;
    private String tel;
    private String name;
    private String zipCode;
    private String address;
    private String addressExtra;
    private Boolean isMain;


    @Builder
    private UserAddressResponse(Long id, String tel, String name, String zipCode, String address, String addressExtra, Boolean isMain){
        this.id = id;
        this.tel = tel;
        this.name = name;
        this.zipCode = zipCode;
        this.address = address;
        this.addressExtra = addressExtra;
        this.isMain = isMain;
    }

    public static UserAddressResponse of(Long id, String tel, String name, String zipCode, String address, String addressExtra, Boolean isMain){
        return UserAddressResponse.builder()
                .id(id)
                .tel(tel)
                .name(name)
                .zipCode(zipCode)
                .address(address)
                .addressExtra(addressExtra)
                .isMain(isMain)
                .build();
    }

    public static List<UserAddressResponse> of(List<UserAddress> list){
        return list.stream()
                .map(p -> UserAddressResponse.of(p.getId(), p.getTel(), p.getName(), p.getZipCode(), p.getAddress(), p.getAddressExtra(), p.getIsMain()))
                .collect(Collectors.toList());
    }
}
