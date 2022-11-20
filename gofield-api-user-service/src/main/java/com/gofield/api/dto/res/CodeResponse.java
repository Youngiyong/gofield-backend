package com.gofield.api.dto.res;

import com.gofield.domain.rds.entity.code.Code;
import com.gofield.domain.rds.enums.ECodeGroup;
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

    public static CodeResponse of(String name, String code, ECodeGroup group){
        return CodeResponse.builder()
                .name(name)
                .code(code)
                .group(group)
                .build();
    }

    public static List<CodeResponse> of(List<Code> list){
        return list.stream()
                .map(p -> CodeResponse.of(p.getName(), p.getCode(), p.getGroup()))
                .collect(Collectors.toList());
    }
}
