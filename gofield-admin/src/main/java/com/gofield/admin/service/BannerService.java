package com.gofield.admin.service;

import com.gofield.admin.dto.BannerDto;
import com.gofield.admin.dto.BannerListDto;
import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.banner.Banner;
import com.gofield.domain.rds.domain.banner.BannerRepository;
import com.gofield.infrastructure.s3.infra.S3FileStorageClient;
import com.gofield.infrastructure.s3.model.enums.FileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    private final S3FileStorageClient s3FileStorageClient;

    @Transactional(readOnly = true)
    public BannerListDto getBannerList(Pageable pageable) {
       Page<Banner> page =  bannerRepository.findAllPaging(pageable);
       List<BannerDto> bannerDtoList = BannerDto.of(page.getContent());
       return BannerListDto.of(bannerDtoList, page);
    }

    @Transactional
    public void updateBanner(MultipartFile image, BannerDto bannerDto){
        Banner banner = bannerRepository.findByBannerId(bannerDto.getId());
        String thumbnail = bannerDto.getThumbnail().replace(Constants.CDN_URL, "").replace(Constants.RESIZE_ADMIN, "");;
        if(image!=null && !image.isEmpty()){
            thumbnail = s3FileStorageClient.uploadFile(image, FileType.BANNER_IMAGE);
        }
        banner.update(bannerDto.getTitle(), bannerDto.getDescription(), bannerDto.getType(), bannerDto.getLinkUrl(), thumbnail, bannerDto.getSort());
    }

    @Transactional(readOnly = true)
    public BannerDto getBanner(Long id){
        return BannerDto.of(bannerRepository.findByBannerId(id));
    }

    @Transactional
    public void save(MultipartFile image, BannerDto bannerDto){
        Banner banner = Banner.newInstance(bannerDto.getTitle(), bannerDto.getDescription(), bannerDto.getType(), bannerDto.getLinkUrl(), s3FileStorageClient.uploadFile(image, FileType.BANNER_IMAGE), bannerDto.getSort());
        bannerRepository.save(banner);
    }

    @Transactional
    public void delete(Long id){
        Banner banner = bannerRepository.findByBannerId(id);
        banner.delete();
    }

}