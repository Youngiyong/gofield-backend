package com.gofield.admin.dto;

import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.banner.Banner;
import com.gofield.domain.rds.domain.banner.EBannerTypeFlag;
import com.gofield.domain.rds.domain.item.EItemQnaStatusFlag;
import com.gofield.domain.rds.domain.item.ItemQna;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class QnaDto {
    private Long id;
    private Long itemId;
    private String name;
    private String thumbnail;
    private Long userId;
    private String title;
    private String description;
    private String answer;
    private String status;
    private String createDate;
    private String answerDate;

    @Builder
    private QnaDto(Long id, Long itemId, String thumbnail, Long userId, String name, String title, String description, String answer, String status, String createDate, String answerDate){
        this.id = id;
        this.itemId = itemId;
        this.thumbnail = thumbnail;
        this.userId = userId;
        this.name = name;
        this.title = title;
        this.description = description;
        this.answer = answer;
        this.status = status;
        this.createDate = createDate;
        this.answerDate = answerDate;
    }

    public static QnaDto of(ItemQna qna){
        return QnaDto.builder()
                .id(qna.getId())
                .itemId(qna.getItem().getId())
                .thumbnail(qna.getItem().getThumbnail()==null ? null : CommonUtils.makeCloudFrontAdminUrl(qna.getItem().getThumbnail()))
                .userId(qna.getUser().getId())
                .name(qna.getName())
                .title(qna.getTitle())
                .description(qna.getDescription())
                .answer(qna.getAnswer())
                .status(qna.getStatus().getDescription())
                .createDate(qna.getCreateDate().toString())
                .answerDate(qna.getAnswerDate()==null ? null : qna.getAnswerDate().toString())
                .build();
    }

    public static List<QnaDto> of(List<ItemQna> list){
        return list.stream()
                .map(p -> QnaDto.of(p))
                .collect(Collectors.toList());
    }
}
