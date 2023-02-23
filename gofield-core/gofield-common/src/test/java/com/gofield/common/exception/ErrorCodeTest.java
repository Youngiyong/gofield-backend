package com.gofield.common.exception;

import com.gofield.common.model.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.fail;

public class ErrorCodeTest {

    @DisplayName("에러 코드 중복 방지를 위한 테스트")
    @Test
    void 외부로_보여지는_에러코드는_유니크한_값이어야한다() {
        Set<String> errorCodes = new HashSet<>();

        for (ErrorCode errorCode: ErrorCode.values()){
            if(errorCodes.contains(errorCode.getCode())){
                fail(String.format("중복되는 에러 %s 코드(%s)가 존재합니다.", errorCode, errorCode.getCode()));
            }
            errorCodes.add(errorCode.getCode());
        }
    }
}
