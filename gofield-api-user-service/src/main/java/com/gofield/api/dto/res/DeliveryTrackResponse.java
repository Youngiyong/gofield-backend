package com.gofield.api.dto.res;

import com.gofield.infrastructure.external.api.tracker.res.CarrierTrackResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryTrackResponse {
    private CarrierTrackResponse delivery;

    @Builder
    private DeliveryTrackResponse(CarrierTrackResponse delivery){
        this.delivery = delivery;
    }

    public static DeliveryTrackResponse of(CarrierTrackResponse delivery){
        return DeliveryTrackResponse.builder()
                .delivery(delivery)
                .build();
    }
}
