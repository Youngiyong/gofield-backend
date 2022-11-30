
package com.gofield.domain.rds.domain.cart;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(	name = "cart_temp")
public class CartTemp  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String uuid;

    @Column(columnDefinition = "TEXT")
    private String temp;

    @Column
    private LocalDateTime createDate;

    @Builder
    private CartTemp(Long userId, String temp, String uuid){
        this.userId = userId;
        this.temp = temp;
        this.uuid = uuid;
    }

    public static CartTemp newInstance(Long userId, String temp){
        return CartTemp.builder()
                .userId(userId)
                .temp(temp)
                .uuid(UUID.randomUUID().toString())
                .build();
    }
}
