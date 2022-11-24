package com.gofield.api.dto.req;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ItemRequest {

    @Getter
    public static class ItemQna {
        @NotNull(message = "제목은 필수값입니다.")
        @Size(min = 2, message = "제목은 2자리 이상 입력해주세요.")
        private String title;

        @NotNull(message = "내용은 필수값입니다.")
        private String description;

        private Boolean isVisible;
    }
}
