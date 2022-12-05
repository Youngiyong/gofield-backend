package com.gofield.infrastructure.external.api.tracker;

import com.gofield.infrastructure.external.api.tracker.res.CarrierTrackResponse;

public interface TrackerApiClient {
    CarrierTrackResponse getCarrierTrackInfo(String carrierId, String trackId);
}
