package com.gofield.domain.rds.domain.order.projection;

import com.gofield.domain.rds.domain.order.OrderCancel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@ToString
@Getter
public class OrderCancelInfoAdminProjectionResponse {

    private Page<OrderCancel> page;
    private int allCount;
    private Long receiptCount;

    private Long refuseCount;
    private Long completeCount;

    @Builder
    private OrderCancelInfoAdminProjectionResponse(Page<OrderCancel> page, int allCount, Long receiptCount, Long refuseCount, Long completeCount){
        this.page = page;
        this.allCount = allCount;
        this.receiptCount = receiptCount;
        this.refuseCount = refuseCount;
        this.completeCount = completeCount;
    }

    public static OrderCancelInfoAdminProjectionResponse of(Page<OrderCancel> page, int allCount, Long receiptCount, Long refuseCount, Long completeCount){
        return OrderCancelInfoAdminProjectionResponse.builder()
                .page(page)
                .allCount(allCount)
                .receiptCount(receiptCount)
                .refuseCount(refuseCount)
                .completeCount(completeCount)
                .build();
    }
}
