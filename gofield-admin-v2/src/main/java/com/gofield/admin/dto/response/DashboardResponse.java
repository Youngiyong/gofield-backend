package com.gofield.admin.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class DashboardResponse {
    private int userCount;
    private int orderCount;
    private int cancelCount;
    private int exchangeCount;

    @Builder
    private DashboardResponse(int userCount, int orderCount, int cancelCount, int exchangeCount){
        this.userCount = userCount;
        this.orderCount = orderCount;
        this.cancelCount = cancelCount;
        this.exchangeCount = exchangeCount;
    }

    public static DashboardResponse of(int userCount, int orderCount, int cancelCount, int exchangeCount){
        return DashboardResponse.builder()
                .userCount(userCount)
                .orderCount(orderCount)
                .cancelCount(cancelCount)
                .exchangeCount(exchangeCount)
                .build();
    }
}
