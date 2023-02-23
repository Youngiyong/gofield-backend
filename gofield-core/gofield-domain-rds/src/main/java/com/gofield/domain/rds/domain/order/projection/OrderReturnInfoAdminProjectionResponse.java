package com.gofield.domain.rds.domain.order.projection;

import com.gofield.domain.rds.domain.order.OrderCancel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@ToString
@Getter
public class OrderReturnInfoAdminProjectionResponse {

    private Page<OrderCancel> page;
    private int allCount;
    private Long receiptCount;
    private Long refuseCount;
    private Long collectCount;
    private Long collectCompleteCount;
    private Long completeCount;

    @Builder
    private OrderReturnInfoAdminProjectionResponse(Page<OrderCancel> page, int allCount, Long receiptCount, Long refuseCount, Long collectCount, Long collectCompleteCount, Long completeCount){
        this.page = page;
        this.allCount = allCount;
        this.receiptCount = receiptCount;
        this.refuseCount = refuseCount;
        this.collectCount = collectCount;
        this.collectCompleteCount = collectCompleteCount;
        this.completeCount = completeCount;
    }

    public static OrderReturnInfoAdminProjectionResponse of(Page<OrderCancel> page, int allCount, Long receiptCount, Long refuseCount, Long collectCount, Long collectCompleteCount, Long completeCount){
        return OrderReturnInfoAdminProjectionResponse.builder()
                .page(page)
                .allCount(allCount)
                .receiptCount(receiptCount)
                .refuseCount(refuseCount)
                .collectCount(collectCount)
                .collectCompleteCount(collectCompleteCount)
                .completeCount(completeCount)
                .build();
    }
}
