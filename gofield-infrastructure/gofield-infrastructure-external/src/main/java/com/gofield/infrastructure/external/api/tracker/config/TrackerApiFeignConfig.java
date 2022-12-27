package com.gofield.infrastructure.external.api.tracker.config;

import com.gofield.common.exception.BadGatewayException;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
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
                case 400: throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "잘못된 운송사 코드입니다.");
                case 404: throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, "잘못된 운송장 번호입니다.");
                case 500: throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, String.format("Tracker API 호출 중 에러가 발생하였습니다. status: (%s) message: (%s)", response.status(), response.body()));
                default: throw new BadGatewayException(String.format("Tracker API 호출중 에러(%s)가 발생하였습니다. message: (%s) ", response.status(), exception.getMessage()));
            }
        }

    }

}
