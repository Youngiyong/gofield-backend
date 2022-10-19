package com.gofield.api.dto.req;

import com.gofield.api.dto.enums.TermSelectionType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupRequest {
    //최초 회원가입시 수집 정보
    private String email;
    @NotNull
    private Integer height;
    @NotNull
    private Integer weight;
    private List<Long> categoryList;
    @NotNull
    private List<Long> agreeList;
    private List<TermSelectionType> selectionList;

}
