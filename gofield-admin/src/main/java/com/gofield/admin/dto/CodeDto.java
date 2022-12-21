package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.code.Code;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CodeDto {
    private String name;
    private String code;
    private ECodeGroup group;

    @Builder
    private CodeDto(String name, String code, ECodeGroup group){
        this.name = name;
        this.code = code;
        this.group = group;
    }

    public static CodeDto of(String name, String code, ECodeGroup group){
        return CodeDto.builder()
                .name(name)
                .code(code)
                .group(group)
                .build();
    }

    public static List<CodeDto> of(List<Code> list){
        return list.stream()
                .map(p -> CodeDto.of(p.getName(), p.getCode(), p.getGroup()))
                .collect(Collectors.toList());
    }
}
