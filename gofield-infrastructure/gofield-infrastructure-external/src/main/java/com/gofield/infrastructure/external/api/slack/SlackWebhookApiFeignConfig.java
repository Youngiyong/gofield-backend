package com.gofield.infrastructure.external.api.slack;

import com.gofield.common.exception.BadGatewayException;
import com.gofield.common.exception.InternalServerException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class SlackWebhookApiFeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new SlackApiErrorDecoder();
    }


    private static class SlackApiErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            FeignException exception = FeignException.errorStatus(methodKey, response);
            switch (response.status()) {
                case 400: case 403: case 404: case 410:
                    throw new InternalServerException(String.format("Slack API 호출 중 클라이언트 에러가 발생하였습니다. status: (%s) message: (%s)", response.status(), response.body()));
                default:
                    throw new BadGatewayException(String.format("슬랙 API 호출중 에러가 발생하였습니다. status: (%s) message: (%s) ", response.status(), exception.getMessage()));
            }
        }

    }

}
