package com.gofield.admin.dto;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.item.Brand;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.bcel.Const;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class BrandDto {
    private Long id;
    private String name;
    private String thumbnail;
    private Boolean isVisible;
    private String createDate;


    @Builder
    private BrandDto(Long id, String name, String thumbnail, Boolean isVisible, String createDate){
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.isVisible = isVisible;
        this.createDate = createDate;
    }

    public static BrandDto of(Brand brand){
        return BrandDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .thumbnail(brand.getThumbnail()==null ? null : Constants.CDN_URL.concat(brand.getThumbnail()).concat(Constants.RESIZE_150x150))
                .isVisible(brand.getIsVisible())
                .createDate(brand.getCreateDate().toLocalDate().toString())
                .build();
    }

    public static List<BrandDto> of(List<Brand> list){
        return list.stream()
                .map(p -> BrandDto.of(p))
                .collect(Collectors.toList());
    }

}
