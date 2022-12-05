package com.gofield.infrastructure.external.api.tracker;

import com.gofield.common.exception.BadGatewayException;
import com.gofield.common.exception.InvalidException;
import com.gofield.infrastructure.external.api.tracker.res.CarrierTrackResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class TrackerApiClientCaller implements TrackerApiClient {

    private final WebClient webClient;

    @Override
    public CarrierTrackResponse getCarrierTrackInfo(String carrierId, String trackId) {
        return webClient.get()
                .uri(String.format("https://apis.tracker.delivery/carriers/%s/tracks/%s", carrierId, trackId))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, errResponse -> Mono.error(new InvalidException(String.format("배송 API 오류 : 잘못된 CarrierId, TrackId (%s) (%s) 입니다", carrierId, trackId))))
                .onStatus(HttpStatus::is5xxServerError, errorResponse -> Mono.error(new BadGatewayException(String.format("배송 API 호출 중 에러가 발생하였습니다."))))
                .bodyToMono(new ParameterizedTypeReference<CarrierTrackResponse>() {
                })
                .block();
    }
}