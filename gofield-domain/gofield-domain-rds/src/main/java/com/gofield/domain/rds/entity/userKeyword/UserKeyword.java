
package com.gofield.domain.rds.entity.userKeyword;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "user_keyword")
public class UserKeyword extends BaseTimeEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String keyword;
    @Column
    private int count;

    private UserKeyword(User user, String keyword){
        this.user = user;
        this.keyword = keyword;
        this.count = 1;
    }

    public static UserKeyword newInstance(User user, String keyword){
        return new UserKeyword(user, keyword);
    }

    public void updateCount(){
        this.count += 1;
    }
}
