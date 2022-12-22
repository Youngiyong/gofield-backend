package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.order.OrderCancel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@NoArgsConstructor
public class OrderReturnListDto {
    private List<OrderReturnDto> list;
    private Page<OrderCancel> page;
    private int allCount;
    private Long receiptCount;
    private Long refuseCount;
    private Long collectCount;
    private Long collectCompleteCount;
    private Long completeCount;

    @Builder
    private OrderReturnListDto(List<OrderReturnDto> list, Page<OrderCancel> page, int allCount, Long receiptCount, Long refuseCount, Long collectCount, Long collectCompleteCount, Long completeCount){
        this.list = list;
        this.page = page;
        this.allCount = allCount;
        this.receiptCount = receiptCount;
        this.refuseCount = refuseCount;
        this.collectCount = collectCount;
        this.collectCompleteCount = collectCompleteCount;
        this.completeCount = completeCount;
    }

    public static OrderReturnListDto of(List<OrderReturnDto> list, Page<OrderCancel> page, int allCount, Long receiptCount, Long refuseCount, Long collectCount, Long collectCompleteCount, Long completeCount){
        return OrderReturnListDto.builder()
                .list(list)
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
