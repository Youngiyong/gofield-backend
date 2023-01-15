package com.gofield.infrastructure.external.api.slack.dto;

import com.gofield.common.model.SlackChannel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

@Getter
@NoArgsConstructor
public class SlackRequest {
    private JSONObject json;
    private SlackChannel channel;

    @Builder
    private SlackRequest(JSONObject json, SlackChannel channel){
        this.json = json;
        this.channel = channel;
    }

    public static SlackRequest of(JSONObject json, SlackChannel channel){
        return SlackRequest.builder()
                .json(json)
                .channel(channel)
                .build();
    }
}
