package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.order.OrderCancel;
import com.gofield.domain.rds.domain.order.OrderChange;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@NoArgsConstructor
public class OrderChangeListDto {
    private List<OrderChangeDto> list;
    private Page<OrderCancel> page;
    private int allCount;
    private Long receiptCount;
    private Long refuseCount;
    private Long collectCount;
    private Long collectCompleteCount;
    private Long reDeliveryCount;
    private Long completeCount;

    @Builder
    private OrderChangeListDto(List<OrderChangeDto> list, Page<OrderCancel> page, int allCount, Long receiptCount, Long refuseCount, Long collectCount, Long collectCompleteCount, Long reDeliveryCount, Long completeCount){
        this.list = list;
        this.page = page;
        this.allCount = allCount;
        this.receiptCount = receiptCount;
        this.refuseCount = refuseCount;
        this.collectCount = collectCount;
        this.collectCompleteCount = collectCompleteCount;
        this.reDeliveryCount = reDeliveryCount;
        this.completeCount = completeCount;
    }

    public static OrderChangeListDto of(List<OrderChangeDto> list, Page<OrderCancel> page, int allCount, Long receiptCount, Long refuseCount, Long collectCount, Long collectCompleteCount, Long reDeliveryCount, Long completeCount){
        return OrderChangeListDto.builder()
                .list(list)
                .page(page)
                .allCount(allCount)
                .receiptCount(receiptCount)
                .refuseCount(refuseCount)
                .collectCount(collectCount)
                .collectCompleteCount(collectCompleteCount)
                .reDeliveryCount(reDeliveryCount)
                .completeCount(completeCount)
                .build();
    }

}
