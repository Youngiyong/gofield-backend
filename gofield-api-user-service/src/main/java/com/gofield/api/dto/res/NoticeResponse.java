package com.gofield.api.dto.res;

import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.banner.Banner;
import com.gofield.domain.rds.domain.notice.ENoticeTypeFlag;
import com.gofield.domain.rds.domain.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class NoticeResponse {
    private String title;
    private String description;
    private ENoticeTypeFlag type;
    private LocalDateTime createDate;

    @Builder
    private NoticeResponse(String title, String description, ENoticeTypeFlag type, LocalDateTime createDate){
        this.title = title;
        this.description = description;
        this.type = type;
        this.createDate = createDate;
    }

    public static NoticeResponse of(Notice notice){
        return NoticeResponse.builder()
                .title(notice.getTitle())
                .description(notice.getDescription())
                .type(notice.getType())
                .createDate(notice.getCreateDate())
                .build();
    }

    public static List<NoticeResponse> of(List<Notice> list){
        return list.stream()
                .map(NoticeResponse::of)
                .collect(Collectors.toList());
    }
}
