package com.gofield.api.service.common.dto.response;

import com.gofield.domain.rds.domain.faq.EFaqTypeFlag;
import com.gofield.domain.rds.domain.faq.Faq;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class FaqResponse {
    private String question;
    private String answer;
    private EFaqTypeFlag type;
    private LocalDateTime createDate;

    @Builder
    private FaqResponse(String question, String answer, EFaqTypeFlag type, LocalDateTime createDate){
        this.question = question;
        this.answer = answer;
        this.type = type;
        this.createDate = createDate;
    }

    public static FaqResponse of(Faq faq){
        return FaqResponse.builder()
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .type(faq.getType())
                .createDate(faq.getCreateDate())
                .build();
    }

    public static List<FaqResponse> of(List<Faq> list){
        return list.stream()
                .map(FaqResponse::of)
                .collect(Collectors.toList());
    }
}
