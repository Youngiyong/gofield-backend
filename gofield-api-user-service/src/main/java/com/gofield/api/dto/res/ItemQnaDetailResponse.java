package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.item.ItemQna;
import com.gofield.domain.rds.domain.item.EItemQnaStatusFlag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ItemQnaDetailResponse {
    private Long qnaId;
    private String title;
    private String description;
    private String answer;
    private EItemQnaStatusFlag status;
    private Boolean isVisible;
    private Boolean isMe;
    private LocalDateTime answerDate;
    private LocalDateTime createDate;

    @Builder
    private ItemQnaDetailResponse(Long qnaId, String title, String description, String answer, EItemQnaStatusFlag status, Boolean isVisible, Boolean isMe, LocalDateTime answerDate, LocalDateTime createDate){
        this.qnaId = qnaId;
        this.title = title;
        this.description = description;
        this.answer = answer;
        this.status = status;
        this.isVisible = isVisible;
        this.isMe = isMe;
        this.answerDate = answerDate;
        this.createDate = createDate;
    }

    public static ItemQnaDetailResponse of(ItemQna qna, Long userId){
        String title = qna.getTitle();
        String description = qna.getDescription();
        String answer = qna.getAnswer();
        Boolean isMe = false;
        if(!qna.getIsVisible()){
            if(!qna.getUser().getId().equals(userId)){
                title = "비공개 문의입니다.";
                description = "비공개 내용입니다.";
                answer = "비공개 답변입니다.";
            } else {
                isMe = true;
            }
        }

        return ItemQnaDetailResponse.builder()
                .qnaId(qna.getId())
                .title(title)
                .description(description)
                .answer(answer)
                .status(qna.getStatus())
                .isVisible(qna.getIsVisible())
                .isMe(isMe)
                .answerDate(qna.getAnswerDate())
                .createDate(qna.getCreateDate())
                .build();
    }


}
