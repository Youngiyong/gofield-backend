package com.gofield.domain.rds.entity.itemHasTag;

import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.entity.tag.Tag;
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
@Table(	name = "item_has_tag")
public class ItemHasTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Column
    private LocalDateTime createDate;

    private ItemHasTag(Item item, Tag tag){
        this.item = item;
        this.tag = tag;
    }

    public static ItemHasTag newInstance(Item item, Tag tag){
        return new ItemHasTag(item, tag);
    }
}
