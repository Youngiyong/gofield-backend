package com.gofield.api.model.response;

import com.gofield.domain.rds.enums.ETermFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TermResponse {
    private Long id;
    private String url;
    private Boolean isEssential;
    private ETermFlag type;
    private LocalDateTime termDate;
}
