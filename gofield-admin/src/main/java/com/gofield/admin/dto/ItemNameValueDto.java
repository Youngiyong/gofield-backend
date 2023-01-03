package com.gofield.admin.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.admin.util.AdminUtil;
import com.gofield.domain.rds.domain.item.ItemOptionGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ItemNameValueDto {
    private Long id;
    private String name;
    private String value;

    @Builder
    private ItemNameValueDto(Long id, String name, String value){
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public static ItemNameValueDto of(Long id, String name, String value){
        return ItemNameValueDto.builder()
                .id(id)
                .name(name)
                .value(value)
                .build();
    }

    public static ItemNameValueDto of(String name, String value){
        return ItemNameValueDto.builder()
                .name(name)
                .value(value)
                .build();
    }

    public static List<ItemNameValueDto> ofList(List<ItemOptionGroup> list){
        List<ItemNameValueDto> result = new ArrayList<>();
        for(ItemOptionGroup itemOptionGroup : list){
            List<ItemNamePriceDto> namePriceList = AdminUtil.strToObject(itemOptionGroup.getOptionGroup(), new TypeReference<List<ItemNamePriceDto>>(){});
            List<String> nameList = namePriceList.stream().map(p->p.getName()).collect(Collectors.toList());
            String name = String.join(",", nameList);
            ItemNameValueDto itemNameValueDto = ItemNameValueDto.of(itemOptionGroup.getId(), itemOptionGroup.getGroupTitle(), name);
            result.add(itemNameValueDto);
        }
        return result;
    }
}
