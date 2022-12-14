package com.gofield.infrastructure.external.api.naver.config;

import com.gofield.common.exception.BadGatewayException;
import com.gofield.common.exception.InvalidException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class NaverProfileFeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new NaverProfileApiErrorDecoder();
    }

    /**
     * <a href="https://developers.naver.com/docs/login/profile/profile.md">https://developers.naver.com/docs/login/profile/profile.md</a>
     */
    private static class NaverProfileApiErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            FeignException exception = FeignException.errorStatus(methodKey, response);
            switch (response.status()) {
                case 401:
                case 403:
                case 404:
                    throw new InvalidException(String.format("네이버 프로필 API 호출 중 잘못된 토큰이 입력되었습니다. status: (%s) message: (%s)", response.status(), response.body()));
                default:
                    throw new BadGatewayException(String.format("네이버 프로필 API 호출중 에러(%s)가 발생하였습니다. message: (%s) ", response.status(), exception.getMessage()));
            }
        }
    }

}
