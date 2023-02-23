package com.gofield.api.service.auth.dto.request;

import com.gofield.api.service.user.dto.TermSelectionType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupRequest {
    private Integer weight;
    private Integer height;
    private List<Long> categoryList;
    @NotNull
    private List<Long> agreeList;
    private List<TermSelectionType> selectionList;

}
