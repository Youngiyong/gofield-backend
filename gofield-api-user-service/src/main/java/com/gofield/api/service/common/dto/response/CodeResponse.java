package com.gofield.api.service.common.dto.response;

import com.gofield.domain.rds.domain.code.Code;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CodeResponse {
    private String name;
    private String code;
    private ECodeGroup group;

    @Builder
    private CodeResponse(String name, String code, ECodeGroup group){
        this.name = name;
        this.code = code;
        this.group = group;
    }

    public static CodeResponse of(Code code){
        return CodeResponse.builder()
                .name(code.getName())
                .code(code.getCode())
                .group(code.getGroup())
                .build();
    }

    public static List<CodeResponse> of(List<Code> list){
        return list.stream()
                .map(CodeResponse::of)
                .collect(Collectors.toList());
    }
}
