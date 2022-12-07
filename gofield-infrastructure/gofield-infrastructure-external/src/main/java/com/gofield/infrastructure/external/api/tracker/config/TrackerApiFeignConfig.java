package com.gofield.infrastructure.external.api.tracker.config;

import com.gofield.common.exception.BadGatewayException;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import feign.FeignException;
import feign.Logger;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class TrackerApiFeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new TrackerApiErrorDecoder();
    }

    /**
     * <a href="https://tracker.delivery/guide"></a>
     */
    private static class TrackerApiErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            FeignException exception = FeignException.errorStatus(methodKey, response);
            switch (response.status()) {
                case 400: case 404: case 403:
                    throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, String.format("Tracker API 호출 중 필수 파라미터가 요청되지 않았습니다. status: (%s) message: (%s)", response.status(), response.body()));
                 case 500:
                    throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, String.format("Tracker API 호출 중 에러가 발생하였습니다. status: (%s) message: (%s)", response.status(), response.body()));
                default:
                    throw new BadGatewayException(String.format("Tracker API 호출중 에러(%s)가 발생하였습니다. message: (%s) ", response.status(), exception.getMessage()));
            }
        }

    }

}
