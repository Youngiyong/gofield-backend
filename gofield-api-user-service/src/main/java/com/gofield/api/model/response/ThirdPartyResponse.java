package com.gofield.api.model.response;

import com.gofield.domain.rds.enums.EPlatformFlag;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ThirdPartyResponse {
    private EPlatformFlag platform;
    private String code;
    private String email;
    private Boolean isFirst;
}
