
package com.gofield.domain.rds.domain.search;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "search_log")
public class SearchLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userId;

    @Column
    private String keyword;

    @Column
    private Boolean isSearch;

    @Column
    private LocalDateTime createDate;

    private SearchLog(Long userId, String keyword, Boolean isSearch){
        this.userId = userId;
        this.keyword = keyword;
        this.isSearch = isSearch;
    }

    public static SearchLog newInstance(Long userId, String keyword, Boolean isSearch){
        return new SearchLog(userId, keyword, isSearch);
    }

}
