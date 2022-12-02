
package com.gofield.domain.rds.domain.order;


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
public class OrderSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String uuid;

    @Column
    private int totalPrice;

    @Column(columnDefinition = "TEXT")
    private String sheet;

    @Column
    private LocalDateTime createDate;

    @Builder
    private OrderSheet(Long userId, String sheet, String uuid, int totalPrice){
        this.userId = userId;
        this.sheet = sheet;
        this.uuid = uuid;
        this.totalPrice = totalPrice;
    }

    public static OrderSheet newInstance(Long userId, String sheet, int totalPrice){
        return OrderSheet.builder()
                .userId(userId)
                .sheet(sheet)
                .totalPrice(totalPrice)
                .uuid(UUID.randomUUID().toString())
                .build();
    }
}
