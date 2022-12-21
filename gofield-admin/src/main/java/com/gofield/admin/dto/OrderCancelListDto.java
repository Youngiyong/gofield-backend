package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.order.OrderCancel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@NoArgsConstructor
public class OrderCancelListDto {

    private List<OrderCancelDto> list;
    private Page<OrderCancel> page;
    private int allCount;
    private Long receiptCount;
    private Long refuseCount;
    private Long completeCount;


    @Builder
    private OrderCancelListDto(List<OrderCancelDto> list, Page<OrderCancel> page, int allCount, Long receiptCount, Long refuseCount, Long completeCount){
        this.list = list;
        this.page = page;
        this.allCount = allCount;
        this.receiptCount = receiptCount;
        this.refuseCount = refuseCount;
        this.completeCount = completeCount;
    }

    public static OrderCancelListDto of(List<OrderCancelDto> list, Page<OrderCancel> page, int allCount, Long receiptCount, Long refuseCount, Long completeCount){
        return OrderCancelListDto.builder()
                .list(list)
                .page(page)
                .allCount(allCount)
                .receiptCount(receiptCount)
                .refuseCount(refuseCount)
                .completeCount(completeCount)
                .build();
    }

}
