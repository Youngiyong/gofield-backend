package com.gofield.infrastructure.external.api.apple;

import com.gofield.common.exception.model.InvalidException;
import com.gofield.common.exception.type.ErrorAction;
import com.gofield.common.exception.type.ErrorCode;
import com.gofield.infrastructure.external.api.apple.dto.response.IdTokenPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class AppleTokenDecoderImpl implements AppleTokenDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public IdTokenPayload getUserInfoFromToken(String idToken) {
        try {
            String payload = idToken.split("\\.")[1];
            String decodedPayload = new String(Base64.getDecoder().decode(payload));
            return objectMapper.readValue(decodedPayload, IdTokenPayload.class);
        } catch (IOException | IllegalArgumentException e) {
            throw new InvalidException(ErrorCode.E400_INVALID_APPLE_TOKEN_EXCEPTION, ErrorAction.NONE , String.format("잘못된 토큰 (%s) 입니다", idToken));
        }
    }

}
