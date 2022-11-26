
package com.gofield.domain.rds.entity.popularKeyword;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "_popular_keyword")
public class PopularKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String keyword;

    @Column
    private Short sort;

    @Column
    private LocalDateTime createDate;

    private PopularKeyword(String keyword){
        this.keyword = keyword;
        this.createDate = LocalDateTime.now();
    }

    public static PopularKeyword newInstance(String keyword){
        return new PopularKeyword(keyword);
    }
}
