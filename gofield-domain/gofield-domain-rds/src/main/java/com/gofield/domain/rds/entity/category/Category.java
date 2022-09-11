
package com.gofield.domain.rds.entity.category;

import com.gofield.domain.rds.converter.CategoryFlagConverter;
import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.enums.ECategoryFlag;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "category")
public class Category extends BaseTimeEntity {

    @Convert(converter = CategoryFlagConverter.class)
    @Column(name = "type_flag", nullable = false, length = 15)
    private ECategoryFlag type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Category> children;

    @Column
    private String name;

    @Column
    private Short sort;

    @Column
    private Boolean isActive;

//    private Category(ELoginFlag route, User user, String uniqueId, String code, String state, String email, String nickName) {
//        this.route = route;
//        this.user = user;
//        this.uniqueId = uniqueId;
//        this.code = code;
//        this.state = state;
//        this.email = email;
//        this.nickName = nickName;
//    }
//
//    public static Category newInstance(ELoginFlag route, User user, String uniqueId, String code, String state, String email, String nickName) {
//        return new Category(route, user, uniqueId, code, state, email, nickName);
//    }

}
