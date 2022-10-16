package com.gofield.api.dto.res;

import com.gofield.domain.rds.entity.banner.Banner;
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

    public static BannerResponse of(String title, String description, String linkUrl, String thumbnail){
        return BannerResponse.builder()
                .title(title)
                .description(description)
                .linkUrl(linkUrl)
                .thumbnail(thumbnail)
                .build();
    }

    public static List<BannerResponse> of(List<Banner> list){
        return list.stream()
                .map(p -> BannerResponse.of(p.getTitle(), p.getDescription(), p.getLinkUrl(), p.getThumbnail()))
                .collect(Collectors.toList());
    }
}
