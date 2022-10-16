package com.gofield.api.dto.res;

import com.gofield.domain.rds.entity.term.Term;
import com.gofield.domain.rds.enums.ETermFlag;
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

    public static TermResponse of(Long id, String url, Boolean isEssential, ETermFlag type, LocalDateTime termDate){
        return TermResponse.builder()
                .id(id)
                .url(url)
                .isEssential(isEssential)
                .type(type)
                .termDate(termDate)
                .build();
    }

    public static List<TermResponse> of (List<Term> list){
        return list
                .stream()
                .map(p -> TermResponse.of(p.getId(), p.getUrl(), p.getIsEssential(), p.getType(), p.getTermDate()))
                .collect(Collectors.toList());
    }
}
