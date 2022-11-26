package com.gofield.api.dto.res;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.item.ItemBundleReview;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ItemBundleReviewResponse {

    private Long id;
    private String name;
    private String nickName;
    private String optionName;
    private Integer weight;
    private Integer height;
    private Double reviewScroe;
    private String description;
    private LocalDateTime createDate;
    private List<String> images;

    @Builder
    public ItemBundleReviewResponse(Long id, String name, String nickName, String optionName, Integer weight, Integer height, Double reviewScroe, String description, LocalDateTime createDate, List<String> images) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.optionName = optionName;
        this.weight = weight;
        this.height = height;
        this.reviewScroe = reviewScroe;
        this.description = description;
        this.createDate = createDate;
        this.images = images;
    }

    public static ItemBundleReviewResponse of(Long id, String name, String nickName, String optionName, Integer weight, Integer height, Double reviewScroe, String description, LocalDateTime createDate, List<String> images){
        return ItemBundleReviewResponse.builder()
                .id(id)
                .name(name)
                .nickName(nickName)
                .optionName(optionName)
                .weight(weight)
                .height(height)
                .reviewScroe(reviewScroe)
                .description(description)
                .createDate(createDate)
                .images(images)
                .build();
    }

    public static List<ItemBundleReviewResponse> of(List<ItemBundleReview> list){
        return list
                .stream()
                .map(p -> ItemBundleReviewResponse.of(
                        p.getId(), p.getName(), p.getNickName(), p.getOptionName(),
                        p.getWeight(), p.getHeight(), p.getReviewScore(), p.getDescription(), p.getCreateDate(),
                        p.getImages().isEmpty() ?
                                new ArrayList<>() :
                                p.getImages().stream()
                                        .map(k -> Constants.CDN_URL.concat(k.getImage()))
                                        .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

}
