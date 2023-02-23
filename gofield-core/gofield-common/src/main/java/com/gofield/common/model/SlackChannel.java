package com.gofield.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum SlackChannel {
    EXCEPTION, ORDER, CHANGE, RETURN, CANCEL, QNA
}
