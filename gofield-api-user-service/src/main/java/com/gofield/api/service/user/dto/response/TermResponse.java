package com.gofield.api.service.user.dto.response;

import com.gofield.domain.rds.domain.user.Term;
import com.gofield.domain.rds.domain.user.ETermFlag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class TermResponse {
    private Long id;
    private String url;
    private Boolean isEssential;
    private ETermFlag type;
    private LocalDateTime termDate;

    @Builder
    private TermResponse(Long id, String url, Boolean isEssential, ETermFlag type, LocalDateTime termDate){
        this.id = id;
        this.url = url;
        this.isEssential = isEssential;
        this.type = type;
        this.termDate = termDate;
    }

    public static TermResponse of(Term term){
        return TermResponse.builder()
                .id(term.getId())
                .url(term.getUrl())
                .isEssential(term.getIsEssential())
                .type(term.getType())
                .termDate(term.getTermDate())
                .build();
    }

    public static List<TermResponse> of (List<Term> list){
        return list
                .stream()
                .map(TermResponse::of)
                .collect(Collectors.toList());
    }
}
