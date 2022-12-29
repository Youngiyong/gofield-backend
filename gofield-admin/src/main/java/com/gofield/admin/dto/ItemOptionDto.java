package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemOptionDto {

    private List<String> values;
    private int option_price;
    private int option_inventory_count;
    private EItemStatusFlag status;
}
