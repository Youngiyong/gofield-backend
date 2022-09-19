
package com.gofield.domain.rds.entity.topKeyword;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "top_keyword")
public class TopKeyword extends BaseTimeEntity {

    @Column
    private String keyword;
    @Column
    private int count;

    private TopKeyword(String keyword){
        this.keyword = keyword;
        this.count = 1;
    }

    public static TopKeyword newInstance(String keyword){
        return new TopKeyword(keyword);
    }

    public void updateCount(){
        this.count += 1;
    }
}
