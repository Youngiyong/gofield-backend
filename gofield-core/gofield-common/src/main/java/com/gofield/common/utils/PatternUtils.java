package com.gofield.common.utils;

import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatternUtils {

    public static void validContactsNumber(String contactNumber){
        Pattern pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");
        if(!pattern.matcher(contactNumber).matches()){
            throw new InvalidException(ErrorCode.E400_INVALID_CONTACTS_NUMBER_FORMAT, ErrorAction.TOAST, String.format("잘못된 전화번호 (%s) 형식 입니다. 올바른 연락처 형식은 01X-0000-0000 입니다.", contactNumber));
        }
    }
}