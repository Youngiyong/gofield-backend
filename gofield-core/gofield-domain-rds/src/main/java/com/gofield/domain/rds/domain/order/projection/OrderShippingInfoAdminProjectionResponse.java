package com.gofield.domain.rds.domain.order.projection;

import com.gofield.domain.rds.domain.item.projection.ItemInfoProjection;
import com.gofield.domain.rds.domain.order.OrderShipping;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@ToString
@Getter
public class OrderShippingInfoAdminProjectionResponse {

    private Page<OrderShipping> page;
    private int allCount;
    private Long receiptCount;
    private Long readyCount;
    private Long deliveryCount;
    private Long deliveryCompleteCount;

    private Long cancelCompleteCount;

    @Builder
    private OrderShippingInfoAdminProjectionResponse(Page<OrderShipping> page, int allCount, Long receiptCount, Long readyCount, Long deliveryCount, Long deliveryCompleteCount, Long cancelCompleteCount){
        this.page = page;
        this.allCount = allCount;
        this.receiptCount = receiptCount;
        this.readyCount = readyCount;
        this.deliveryCount = deliveryCount;
        this.deliveryCompleteCount = deliveryCompleteCount;
        this.cancelCompleteCount = cancelCompleteCount;
    }

    public static OrderShippingInfoAdminProjectionResponse of(Page<OrderShipping> page, int allCount, Long receiptCount, Long readyCount, Long deliveryCount, Long deliveryCompleteCount, Long cancelCompleteCount){
        return OrderShippingInfoAdminProjectionResponse.builder()
                .page(page)
                .allCount(allCount)
                .receiptCount(receiptCount)
                .readyCount(readyCount)
                .deliveryCount(deliveryCount)
                .deliveryCompleteCount(deliveryCompleteCount)
                .cancelCompleteCount(cancelCompleteCount)
                .build();
    }
}
