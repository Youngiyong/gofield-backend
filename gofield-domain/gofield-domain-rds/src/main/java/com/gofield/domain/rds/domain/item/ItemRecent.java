package com.gofield.domain.rds.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "item_recent")
public class ItemRecent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long itemId;

    @Column
    private String itemNumber;

    @Column
    private LocalDateTime createDate;

    @Builder
    private ItemRecent(Long userId, Long itemId, String itemNumber){
        this.userId = userId;
        this.itemId = itemId;
        this.itemNumber = itemNumber;
        this.createDate = LocalDateTime.now();
    }

    public static ItemRecent newInstance(Long userId, Long itemId, String itemNumber){
         return ItemRecent.builder()
                 .userId(userId)
                 .itemId(itemId)
                 .itemNumber(itemNumber)
                 .build();
    }
}
