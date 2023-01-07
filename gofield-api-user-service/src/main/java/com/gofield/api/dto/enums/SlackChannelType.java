package com.gofield.api.dto.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum SlackChannelType {
    EXCEPTION, ORDER, CHANGE, RETURN, CANCEL, QNA
}
