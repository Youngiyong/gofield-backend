package com.gofield.infrastructure.external.api.tracker;

import com.gofield.infrastructure.external.api.tracker.config.TrackerApiFeignConfig;
import com.gofield.infrastructure.external.api.tracker.res.CarrierTrackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
        name = "TrackerApiClient",
        url = "${external.client.tracker.delivery.base-url}",
        configuration = {
                TrackerApiFeignConfig.class
        }
)
public interface TrackerApiClient {

    @GetMapping("${external.client.tracker.delivery.tracker-url}")
    CarrierTrackResponse getCarrierTrackInfo(@PathVariable String carrierId,
                                             @PathVariable String trackId);
}
