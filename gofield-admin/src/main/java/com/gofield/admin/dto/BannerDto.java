package com.gofield.admin.dto;

import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.banner.Banner;
import com.gofield.domain.rds.domain.banner.EBannerTypeFlag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class BannerDto {
    private Long id;
    private String title;
    private String description;
    private EBannerTypeFlag type;
    private String linkUrl;
    private String thumbnail;
    private Integer sort;
    private String createDate;


    @Builder
    private BannerDto(Long id, String title, String description, EBannerTypeFlag type, String linkUrl, String thumbnail,  Integer sort, String createDate){
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.linkUrl = linkUrl;
        this.thumbnail = thumbnail;
        this.sort = sort;
        this.createDate = createDate;
    }

    public static BannerDto of(Banner banner){
        return BannerDto.builder()
                .id(banner.getId())
                .title(banner.getTitle())
                .type(banner.getType())
                .description(banner.getDescription())
                .thumbnail(banner.getThumbnail()==null ? null : CommonUtils.makeCloudFrontAdminUrl(banner.getThumbnail()))
                .linkUrl(banner.getLinkUrl())
                .sort(banner.getSort())
                .createDate(banner.getCreateDate().toLocalDate().toString())
                .build();
    }

    public static List<BannerDto> of(List<Banner> list){
        return list.stream()
                .map(p -> BannerDto.of(p))
                .collect(Collectors.toList());
    }

}
