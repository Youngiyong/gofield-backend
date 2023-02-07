
package com.gofield.domain.rds.domain.order;


import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(	name = "order_sheet")
public class OrderSheet extends BaseTimeEntity {

    @Column
    private Long userId;

    @Column
    private String uuid;

    @Column
    private int totalPrice;

    @Column(columnDefinition = "TEXT")
    private String sheet;

    @Column
    private Boolean isOrder;

    @Builder
    private OrderSheet(Long userId, String sheet, String uuid, int totalPrice){
        this.userId = userId;
        this.sheet = sheet;
        this.uuid = uuid;
        this.totalPrice = totalPrice;
        this.isOrder = false;
    }

    public static OrderSheet newInstance(Long userId, String sheet, int totalPrice){
        return OrderSheet.builder()
                .userId(userId)
                .sheet(sheet)
                .totalPrice(totalPrice)
                .uuid(UUID.randomUUID().toString())
                .build();
    }

    public void isValidOrder(){
        if(this.isOrder){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 완료처리된 주문 번호입니다.");
        }
    }

    public void update(){
        this.isOrder = true;
    }
}
