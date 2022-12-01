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

    public static UserAddressResponse of(UserAddress userAddress) {
        if (userAddress == null) return null;
        return UserAddressResponse.builder()
                .id(userAddress.getId())
                .tel(userAddress.getTel())
                .name(userAddress.getName())
                .zipCode(userAddress.getZipCode())
                .address(userAddress.getAddress())
                .addressExtra(userAddress.getAddressExtra())
                .isMain(userAddress.getIsMain())
                .build();
    }

    public static List<UserAddressResponse> of(List<UserAddress> list){
        return list.stream()
                .map(p -> UserAddressResponse.of(p))
                .collect(Collectors.toList());
    }
}
