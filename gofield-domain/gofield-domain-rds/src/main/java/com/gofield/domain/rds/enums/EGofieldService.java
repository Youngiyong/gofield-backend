package com.gofield.domain.rds.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EGofieldService {
    GOFIELD_API, GOFIELD_BACK_OFFICE  ;
}
