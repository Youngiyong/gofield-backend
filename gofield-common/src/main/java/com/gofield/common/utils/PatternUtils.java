package com.gofield.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatternUtils {

    public static Boolean validTel(String tel){
        return Pattern.matches( "(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})", tel);
    }
}