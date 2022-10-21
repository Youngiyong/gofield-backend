package com.gofield.infrastructure.internal.api.sns;

import com.gofield.common.exception.BadGatewayException;
import com.gofield.common.exception.InvalidException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class GofieldSnsFeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new GofieldSnsErrorDecoder();
    }

    private static class GofieldSnsErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            FeignException exception = FeignException.errorStatus(methodKey, response);
            switch (response.status()) {
                case 400: case 401: case 403: case 404:
                    throw new InvalidException(String.format("내부 SNS API 호출 중 잘못된 파라미터가 입력되었습니다. status: (%s) message: (%s)", response.status(), response.body()));
                default:
                    throw new BadGatewayException(String.format("내부 SNS API 호출중 에러(%s)가 발생하였습니다. message: (%s) ", response.status(), exception.getMessage()));
            }
        }

    }

}
