package com.gofield.api.service.item.dto.response;

import com.gofield.domain.rds.domain.item.ItemQna;
import com.gofield.domain.rds.domain.item.EItemQnaStatusFlag;
import com.gofield.domain.rds.domain.seller.Seller;
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
    private String sellerName;
    private LocalDateTime answerDate;
    private LocalDateTime createDate;

    @Builder
    private ItemQnaDetailResponse(Long qnaId, String title, String description, String answer, EItemQnaStatusFlag status, Boolean isVisible, Boolean isMe, String sellerName, LocalDateTime answerDate, LocalDateTime createDate){
        this.qnaId = qnaId;
        this.title = title;
        this.description = description;
        this.answer = answer;
        this.status = status;
        this.isVisible = isVisible;
        this.isMe = isMe;
        this.sellerName = sellerName;
        this.answerDate = answerDate;
        this.createDate = createDate;
    }

    public static ItemQnaDetailResponse of(ItemQna qna, Long userId, Seller seller){
        String title = qna.getTitle();
        String description = qna.getDescription();
        String answer = qna.getAnswer();
        Boolean isMe = false;
        if(!qna.getIsVisible()){
            if(qna.getUser().getId().equals(userId)){
                isMe = true;
            } else {
                title = "비공개 문의입니다.";
                description = "비공개 내용입니다.";
                answer = "비공개 답변입니다.";
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
                .sellerName(seller.getName())
                .answerDate(qna.getAnswerDate())
                .createDate(qna.getCreateDate())
                .build();
    }


}
