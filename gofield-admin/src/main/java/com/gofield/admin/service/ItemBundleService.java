package com.gofield.admin.service;


import com.gofield.admin.dto.*;
import com.gofield.admin.dto.response.projection.ItemBundleInfoProjectionResponse;
import com.gofield.common.exception.ConvertException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.Constants;
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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemBundleService {

    private final ItemService itemService;

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ItemBundleRepository itemBundleRepository;
    private final ItemBundleImageRepository itemBundleImageRepository;
    private final ItemBundleAggregationRepository itemBundleAggregationRepository;
    private final S3FileStorageClient s3FileStorageClient;

    @Transactional(readOnly = true)
    public ItemBundleListDto getBundleList(String keyword, Long parentId, Long childId, Pageable pageable){
        Page<ItemBundle> page = itemBundleRepository.findAllByKeywordAndCategoryId(keyword, parentId, childId, pageable);
        List<ItemBundleInfoProjectionResponse> result = ItemBundleInfoProjectionResponse.of(page.getContent());
        return ItemBundleListDto.of(result, page);
    }

    @Transactional(readOnly = true)
    public List<ItemBundleInfoProjectionResponse> downloadBundles(String keyword){
        return ItemBundleInfoProjectionResponse.of(itemBundleRepository.findAllByKeyword(keyword));
    }

    @Transactional
    public void updateItemBundle(MultipartFile image, List<MultipartFile> images, ItemBundleDto itemBundleDto){
        Brand brand = brandRepository.findByBrandId(itemBundleDto.getBrandId());
        Category category = categoryRepository.findByCategoryId(itemBundleDto.getCategoryId());
        ItemBundle itemBundle = itemBundleRepository.findByBundleIdNotFetch(itemBundleDto.getId());
        String thumbnail = itemBundleDto.getThumbnail() == null ? null : itemBundleDto.getThumbnail().replace(Constants.CDN_URL, "").replace(Constants.RESIZE_ADMIN, "");;
        if(!image.isEmpty() && !image.getOriginalFilename().equals("")){
            thumbnail =  s3FileStorageClient.uploadFile(image, FileType.ITEM_BUNDLE_IMAGE);
        }
        ItemBundleImage itemBundleImage = itemBundleImageRepository.findByBundleIdOrderBySortDesc(itemBundle.getId());
        int sort = itemBundleImage==null ? 10 : itemBundleImage.getSort() + 1;
        if(itemBundle.getIsActive()!=itemBundleDto.getIsActive()){
            if(itemBundleDto.getIsActive()){
                itemService.updateItemBundleAggregation(itemBundle.getId(), false);
            }
        }
        itemBundle.update(brand, category, itemBundleDto.getName(), itemBundleDto.getIsRecommend(), itemBundleDto.getIsActive(), thumbnail, itemBundleDto.getDescription());
        if(images!=null && !images.isEmpty()){
            for(MultipartFile file: images){
                if(!file.getOriginalFilename().equals("")){
                    itemBundle.addBundleImage(ItemBundleImage.newInstance(itemBundle, s3FileStorageClient.uploadFile(file, FileType.ITEM_BUNDLE_IMAGE), sort));
                    sort++;
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public ItemBundleDto getItemBundle(Long id){
        List<CategoryDto> categoryDtoList = CategoryDto.of(categoryRepository.findAllChildrenIsActiveOrderBySort());
        List<BrandDto> brandDtoList = BrandDto.of(brandRepository.findAllByActiveOrderBySort());
        if(id==null){
            return ItemBundleDto.of(categoryDtoList, brandDtoList);
        }
        return null;
    }

    @Transactional
    public void updateItemBundleImageSort(Long id, Long imageId, String type){
        ItemBundleImage itemBundleImage = itemBundleImageRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException(String.format("존재하지 않는 <%s> id입니다.", imageId)));
        if(type.equals("increase")){
            itemBundleImage.updateSortDecrease();
        } else {
            itemBundleImage.updateSortInCrease();
        }
    }

    @Transactional(readOnly = true)
    public ItemBundleEditDto getItemBundleImage(Long id){
        List<CategoryDto> categoryDtoList = CategoryDto.of(categoryRepository.findAllChildrenIsActiveOrderBySort());
        List<BrandDto> brandDtoList = BrandDto.of(brandRepository.findAllByActiveOrderBySort());
        ItemBundle itemBundle = itemBundleRepository.findByBundleIdFetchJoin(id);
        List<ItemBundleImageDto> itemBundleImages = ItemBundleImageDto.of(itemBundle.getImages());
        ItemBundleDto itemBundleDto = ItemBundleDto.of(categoryDtoList, brandDtoList, itemBundle);
        return ItemBundleEditDto.of(itemBundleDto, itemBundleImages);
    }
    @Transactional
    public void save(MultipartFile image, List<MultipartFile> images, ItemBundleDto itemBundleDto){
        Brand brand = brandRepository.findByBrandId(itemBundleDto.getBrandId());
        Category category = categoryRepository.findByCategoryId(itemBundleDto.getCategoryId());
        String thumbnailUrl = image==null && images.isEmpty() ? null : s3FileStorageClient.uploadFile(image, FileType.ITEM_BUNDLE_IMAGE);
        ItemBundle itemBundle = ItemBundle.newInstance(itemBundleDto.getName(), category, brand, true, itemBundleDto.getIsRecommend(), thumbnailUrl, itemBundleDto.getDescription());
        if(images!=null && !images.isEmpty()){
            int sort = 10;
            for(MultipartFile file: images){
                itemBundle.addBundleImage(ItemBundleImage.newInstance(itemBundle, s3FileStorageClient.uploadFile(file, FileType.ITEM_BUNDLE_IMAGE), sort));
                sort++;
            }
        }
        ItemBundleAggregation itemBundleAggregation = ItemBundleAggregation.newInstance(itemBundle);
        itemBundleRepository.save(itemBundle);
        itemBundleAggregationRepository.save(itemBundleAggregation);
    }

    @Transactional
    public void delete(Long id){
        ItemBundle itemBundle = itemBundleRepository.findItemBundleImagesByBundleIdFetch(id);
        ItemBundleAggregation itemBundleAggregation = itemBundleAggregationRepository.findByBundleId(itemBundle.getId());
        itemBundle.delete();
        itemBundleAggregationRepository.delete(itemBundleAggregation);
    }

    @Transactional
    public void deleteImage(Long id, Long imageId){
        ItemBundleImage itemBundleImage = itemBundleImageRepository.findItemBundleImageById(imageId);
        itemBundleImageRepository.delete(itemBundleImage);
    }
}
