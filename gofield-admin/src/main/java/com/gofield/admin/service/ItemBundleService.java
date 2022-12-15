package com.gofield.admin.service;


import com.gofield.admin.dto.*;
import com.gofield.admin.dto.response.projection.ItemBundleInfoProjectionResponse;
import com.gofield.domain.rds.domain.item.*;
import com.gofield.infrastructure.s3.infra.S3FileStorageClient;
import com.gofield.infrastructure.s3.model.enums.FileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemBundleService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ItemBundleRepository itemBundleRepository;

    private final ItemBundleAggregationRepository itemBundleAggregationRepository;
    private final S3FileStorageClient s3FileStorageClient;

    @Transactional(readOnly = true)
    public ItemBundleListDto getBundleList(String keyword, Pageable pageable){
        Page<ItemBundle> page = itemBundleRepository.findAllByKeyword(keyword, pageable);
        List<ItemBundleInfoProjectionResponse> result = ItemBundleInfoProjectionResponse.of(page.getContent());
        return ItemBundleListDto.of(result, page);
    }

    @Transactional
    public void updateItemBundle(ItemBundleDto itemBundleDto){
    }

    @Transactional(readOnly = true)
    public ItemBundleDto getItemBundle(Long id){
        List<Category> categoryList = categoryRepository.findAllIsActiveOrderBySort();
        List<CategoryDto> categoryDtoList = CategoryDto.of(categoryList);
        List<Brand> brandList = brandRepository.findAllByActiveOrderBySort();
        List<BrandDto> brandDtoList = BrandDto.of(brandList);
        if(id==null){
            return ItemBundleDto.of(categoryDtoList, brandDtoList);
        }
        return null;
    }

    @Transactional
    public void save(MultipartFile image, List<MultipartFile> images, ItemBundleDto itemBundleDto){
        String thumbnailUrl = image==null && images.isEmpty() ? null : s3FileStorageClient.uploadFile(image, FileType.ITEM_BUNDLE_IMAGE);
        List<String> imagesUrl = new ArrayList<>();
        if(!images.isEmpty()){
            for(MultipartFile file: images){
                imagesUrl.add(s3FileStorageClient.uploadFile(file, FileType.ITEM_BUNDLE_IMAGE));
            }
        }
        Brand brand = brandRepository.findByBrandId(itemBundleDto.getBrandId());
        Category category = categoryRepository.findByCategoryId(itemBundleDto.getCategoryId());
        ItemBundle itemBundle = ItemBundle.newInstance(itemBundleDto.getName(), category, brand, itemBundleDto.getIsActive(), itemBundleDto.getIsRecommend(), thumbnailUrl);
        for(String imageUrl: imagesUrl){
            ItemBundleImage itemBundleImage = ItemBundleImage.newInstance(itemBundle, imageUrl);
            itemBundle.addBundleImage(itemBundleImage);
        }
        ItemBundleAggregation itemBundleAggregation = ItemBundleAggregation.newInstance(itemBundle);
        itemBundleRepository.save(itemBundle);
        itemBundleAggregationRepository.save(itemBundleAggregation);
    }

    @Transactional
    public void delete(Long id){

    }
}
