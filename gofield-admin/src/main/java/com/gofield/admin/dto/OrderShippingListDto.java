package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.order.OrderShipping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@NoArgsConstructor
public class OrderShippingListDto {

    private List<OrderShippingDto> list;
    private Page<OrderShipping> page;

    private int allCount;
    private Long receiptCount;
    private Long readyCount;
    private Long deliveryCount;
    private Long deliveryCompleteCount;

    @Builder
    private OrderShippingListDto(List<OrderShippingDto> list, Page<OrderShipping> page, int allCount, Long receiptCount, Long readyCount, Long deliveryCount, Long deliveryCompleteCount){
        this.list = list;
        this.page = page;
        this.allCount = allCount;
        this.receiptCount = receiptCount;
        this.readyCount = readyCount;
        this.deliveryCount = deliveryCount;
        this.deliveryCompleteCount = deliveryCompleteCount;
    }

    public static OrderShippingListDto of(List<OrderShippingDto> list, Page<OrderShipping> page, int allCount, Long receiptCount, Long readyCount, Long deliveryCount, Long deliveryCompleteCount){
        return OrderShippingListDto.builder()
                .list(list)
                .page(page)
                .allCount(allCount)
                .receiptCount(receiptCount)
                .readyCount(readyCount)
                .deliveryCount(deliveryCount)
                .deliveryCompleteCount(deliveryCompleteCount)
                .build();
    }

}
