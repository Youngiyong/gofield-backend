package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.banner.Banner;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BannerResponse {
    private String title;
    private String description;
    private String linkUrl;
    private String thumbnail;

    @Builder
    private BannerResponse(String title, String description, String linkUrl, String thumbnail){
        this.title = title;
        this.description = description;
        this.linkUrl = linkUrl;
        this.thumbnail = thumbnail;
    }

    public static BannerResponse of(Banner banner){
        return BannerResponse.builder()
                .title(banner.getTitle())
                .description(banner.getDescription())
                .linkUrl(banner.getLinkUrl())
                .thumbnail(banner.getThumbnail())
                .build();
    }

    public static List<BannerResponse> of(List<Banner> list){
        return list.stream()
                .map(BannerResponse::of)
                .collect(Collectors.toList());
    }
}
