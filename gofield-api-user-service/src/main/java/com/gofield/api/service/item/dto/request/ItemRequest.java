package com.gofield.api.service.item.dto.request;

import com.gofield.domain.rds.domain.item.EItemChargeFlag;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import com.gofield.domain.rds.domain.item.EItemSpecFlag;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

    @Getter
    public static class ItemLike {
        private Boolean isLike;
    }

}
