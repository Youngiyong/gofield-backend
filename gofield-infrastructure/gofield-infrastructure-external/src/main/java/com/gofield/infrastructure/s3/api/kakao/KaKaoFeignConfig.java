package com.gofield.infrastructure.s3.api.kakao;

import com.gofield.common.exception.BadGatewayException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import feign.FeignException;
import feign.Logger;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class KaKaoFeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new KakaoApiErrorDecoder();
    }

    /**
     * <a href="https://developers.kakao.com/docs/latest/ko/reference/rest-api-reference#response-code">https://developers.kakao.com/docs/latest/ko/reference/rest-api-reference#response-code</a>
     */
    private static class KakaoApiErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            FeignException exception = FeignException.errorStatus(methodKey, response);
            switch (response.status()) {
                case 400:
                    throw new InvalidException(ErrorCode.E400_INVALID_MISSING_AUTH_TOKEN_PARAMETER, ErrorAction.NONE, String.format("카카오 API 호출 중 필수 파라미터가 요청되지 않았습니다. status: (%s) message: (%s)", response.status(), response.body()));
                case 401:
                case 403:
                    throw new InvalidException(ErrorCode.E400_INVALID_AUTH_TOKEN, ErrorAction.NONE, String.format("카카오 API 호출 중 잘못된 토큰이 입력되었습니다. status: (%s) message: (%s)", response.status(), response.body()));
                default:
                    throw new BadGatewayException(String.format("카카오 API 호출중 에러(%s)가 발생하였습니다. message: (%s) ", response.status(), exception.getMessage()));
            }
        }

    }

}
