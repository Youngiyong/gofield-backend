package com.gofield.domain.rds.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum EItemBundleSort {
    RECOMMEND, POPULAR, NEWEST, REVIEW, HIGHER_PRICE, LOWER_PRICE
    ;
}
