package com.gofield.infrastructure.external.api.apple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.utils.LocalDateTimeUtils;
import com.gofield.infrastructure.external.api.apple.dto.model.AppleIdTokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;


/**
 * <a href="https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_rest_api/verifying_a_user">https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_rest_api/verifying_a_user</a>
 */
@RequiredArgsConstructor
@Component
public class AppleTokenDecoderImpl implements AppleTokenDecoder {

    /**
     * 애플 토큰에서 제공되는 만료시간 (1일)을 사용하지 않고, 발급시간 후 5분을 자체 만료시간으로 사용한다.
     */
    private static final Duration EXPIRED_DURATION = Duration.ofMinutes(5);

    private final ObjectMapper objectMapper;

    @Override
    public String getSocialIdFromAppleIdToken(String idToken) {
        try {
            String payload = idToken.split("\\.")[1];
            String decodedPayload = new String(Base64.getDecoder().decode(payload));
            AppleIdTokenPayload appleIdTokenPayload = objectMapper.readValue(decodedPayload, AppleIdTokenPayload.class);
            validateToken(appleIdTokenPayload);
            return appleIdTokenPayload.getSub();
        } catch (IOException | IllegalArgumentException e) {
            throw new InvalidException(ErrorCode.E400_INVALID_AUTH_TOKEN, ErrorAction.NONE, String.format("잘못된 토큰 (%s) 입니다", idToken));
        }
    }

    private void validateToken(AppleIdTokenPayload payload) {
        if (!payload.getIss().equals("https://appleid.apple.com")) {
            throw new InvalidException(ErrorCode.E400_INVALID_AUTH_TOKEN, ErrorAction.NONE, String.format("잘못된 애플 토큰 입니다 - issuer가 일치하지 않습니다 payload: (%s)", payload));
        }
        if (!payload.getAud().equals("추후 나오면 변경")) {
            throw new InvalidException(ErrorCode.E400_INVALID_AUTH_TOKEN, ErrorAction.NONE, String.format("잘못된 애플 토큰 입니다 - clientId가 일치하지 않습니다 payload: (%s)", payload));
        }
        LocalDateTime authDateTime = LocalDateTimeUtils.epochToLocalDateTime(payload.getAuthTime());
        if (authDateTime.plus(EXPIRED_DURATION).isBefore(LocalDateTime.now())) {
            throw new InvalidException(ErrorCode.E400_INVALID_AUTH_TOKEN, ErrorAction.NONE, String.format("발급 후 5분애플 토큰 (%s) 입니다 만료시간: (%s)", payload, authDateTime));
        }
    }

}
