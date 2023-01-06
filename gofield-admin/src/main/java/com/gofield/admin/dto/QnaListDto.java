package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.banner.Banner;
import com.gofield.domain.rds.domain.item.ItemQna;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@NoArgsConstructor
public class QnaListDto {

    private List<QnaDto> list;
    private Page<ItemQna> page;


    @Builder
    private QnaListDto(List<QnaDto> list, Page<ItemQna> page){
        this.list = list;
        this.page = page;
    }

    public static QnaListDto of(List<QnaDto> list, Page<ItemQna> page){
        return QnaListDto.builder()
                .list(list)
                .page(page)
                .build();
    }

}
