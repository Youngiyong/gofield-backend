package com.gofield.api.dto.res;

import com.gofield.domain.rds.entity.itemQna.ItemQna;
import com.gofield.domain.rds.enums.qna.EQnaStatusFlag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ItemQnaResponse {
    private Long id;
    private String title;
    private EQnaStatusFlag status;
    private Boolean isVisible;

    private Boolean isMe;
    private LocalDateTime createDate;

    @Builder
    private ItemQnaResponse(Long id, String title, EQnaStatusFlag status, Boolean isVisible, Boolean isMe, LocalDateTime createDate){
        this.id = id;
        this.title = title;
        this.status = status;
        this.isVisible = isVisible;
        this.isMe = isMe;
        this.createDate = createDate;
    }

    public static ItemQnaResponse of(Long id, String title, EQnaStatusFlag status, Boolean isVisible, Boolean isMe, LocalDateTime createDate){
        return ItemQnaResponse.builder()
                .id(id)
                .title(title)
                .status(status)
                .isVisible(isVisible)
                .isMe(isMe)
                .createDate(createDate)
                .build();
    }

    public static List<ItemQnaResponse> of(List<ItemQna> list, Long userId){
        List<ItemQnaResponse> result = new ArrayList<>();
        for(ItemQna qna: list){
            String title = qna.getTitle();
            Boolean isMe = false;
            if(!qna.getIsVisible()){
                if(!qna.getUser().getId().equals(userId)){
                    title = "비공개 문의입니다.";
                } else {
                    isMe = true;
                }
            }
            ItemQnaResponse response = ItemQnaResponse.of(qna.getId(), title, qna.getStatus(), qna.getIsVisible(), isMe, qna.getCreateDate());
            result.add(response);
        }
        return result;
    }
}
