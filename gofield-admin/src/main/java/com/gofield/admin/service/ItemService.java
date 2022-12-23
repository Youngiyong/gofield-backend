package com.gofield.admin.service;


import com.gofield.admin.dto.*;
import com.gofield.admin.dto.response.projection.ItemBundleInfoProjectionResponse;
import com.gofield.admin.dto.response.projection.ItemInfoProjectionResponse;
import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.code.CodeRepository;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.item.projection.ItemInfoAdminProjectionResponse;
import com.gofield.domain.rds.domain.item.projection.ItemInfoProjection;
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
public class ItemService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ItemBundleRepository itemBundleRepository;
    private final ItemBundleImageRepository itemBundleImageRepository;
    private final ItemBundleAggregationRepository itemBundleAggregationRepository;
    private final S3FileStorageClient s3FileStorageClient;

    @Transactional(readOnly = true)
    public ItemListDto getItemList(String keyword, EItemStatusFlag status, Pageable pageable){
        ItemInfoAdminProjectionResponse page = itemRepository.findAllByKeyword(keyword, status, pageable);
        List<ItemInfoProjectionResponse> result = ItemInfoProjectionResponse.of(page.getPage().getContent());
        return ItemListDto.of(result, page.getPage(), page.getAllCount(), page.getSalesCount(), page.getHideCount(), page.getSoldOutCount());
    }

    @Transactional(readOnly = true)
    public List<ItemInfoProjectionResponse> downloadItems(String keyword, EItemStatusFlag status){
        return ItemInfoProjectionResponse.of(itemRepository.findAllByKeyword(keyword, status));
    }

//    @Transactional
//    public void updateItemBundle(MultipartFile image, List<MultipartFile> images, ItemBundleDto itemBundleDto){
//        Brand brand = brandRepository.findByBrandId(itemBundleDto.getBrandId());
//        Category category = categoryRepository.findByCategoryId(itemBundleDto.getCategoryId());
//        ItemBundle itemBundle = itemBundleRepository.findByBundleId(itemBundleDto.getId());
//        String thumbnail = itemBundleDto.getThumbnail()==null ? null : itemBundleDto.getThumbnail().replace(Constants.CDN_URL, "").replace(Constants.RESIZE_150x150, "");
//        if(!image.isEmpty() && image.getOriginalFilename().equals("")){
//            thumbnail =  s3FileStorageClient.uploadFile(image, FileType.ITEM_BUNDLE_IMAGE);
//        }
//        itemBundle.update(brand, category, itemBundleDto.getName(), itemBundleDto.getIsRecommend(), thumbnail);
//        if(images!=null && !images.isEmpty()){
//            for(MultipartFile file: images){
//                if(!file.getOriginalFilename().equals("")){
//                    itemBundle.addBundleImage(ItemBundleImage.newInstance(itemBundle, s3FileStorageClient.uploadFile(file, FileType.ITEM_BUNDLE_IMAGE)));
//                }
//            }
//        }
//    }
//
    @Transactional(readOnly = true)
    public ItemDto getItem(Long id){
        List<CategoryDto> categoryDtoList = CategoryDto.of(categoryRepository.findAllIsActiveOrderBySort());
        List<BrandDto> brandDtoList = BrandDto.of(brandRepository.findAllByActiveOrderBySort());
        List<ItemBundleDto> bundleDtoList = ItemBundleDto.of(itemBundleRepository.findAllActive());
        if(id==null){
            return ItemDto.of(categoryDtoList, brandDtoList,  bundleDtoList);
        }
        return null;
    }

    @Transactional
    public void saveNewItem(MultipartFile image, List<MultipartFile> images, ItemDto itemDto){
//        Brand brand = brandRepository.findByBrandId(itemDto.getBrandId());
//        Category category = categoryRepository.findByCategoryId(itemDto.getCategoryId());
//        ItemBundle itemBundle = itemBundleRepository.findByBundleId(itemDto.getBundleId());
//        String thumbnailUrl = image==null && images.isEmpty() ? null : s3FileStorageClient.uploadFile(image, FileType.ITEM_IMAGE);
//        ItemDetail itemDetail = ItemDetail.newInstance(itemDto.getDescription(), null, itemDto.getGender(), itemDto.getSpec());
    }
    @Transactional
    public void saveUsedItem(MultipartFile image, List<MultipartFile> images, ItemDto itemDto){
//        Brand brand = brandRepository.findByBrandId(itemDto.getBrandId());
//        Category category = categoryRepository.findByCategoryId(itemDto.getCategoryId());
//        ItemBundle itemBundle = itemBundleRepository.findByBundleId(itemDto.getBundleId());
//        String thumbnailUrl = image==null && images.isEmpty() ? null : s3FileStorageClient.uploadFile(image, FileType.ITEM_IMAGE);
//        ItemDetail itemDetail = ItemDetail.newInstance(itemDto.getDescription(), null, itemDto.getGender(), itemDto.getSpec());
//        ShippingTemplate shippingTemplate = ShippingTemplate.newInstance(1L,  itemDto.getShippingTemplate().getChargeType(), itemDto.getShippingTemplate().getCharge(), true,, )
//        Item item = Item.usedItemInstance(itemBundle, brand, category)
    }

//
//    @Transactional(readOnly = true)
//    public ItemBundleEditDto getItemBundleImage(Long id){
//        List<CategoryDto> categoryDtoList = CategoryDto.of(categoryRepository.findAllIsActiveOrderBySort());
//        List<BrandDto> brandDtoList = BrandDto.of(brandRepository.findAllByActiveOrderBySort());
//        ItemBundle itemBundle = itemBundleRepository.findByBundleIdFetchJoin(id);
//        List<ItemBundleImageDto> itemBundleImages = ItemBundleImageDto.of(itemBundle.getImages());
//        ItemBundleDto itemBundleDto = ItemBundleDto.of(categoryDtoList, brandDtoList, itemBundle);
//        return ItemBundleEditDto.of(itemBundleDto, itemBundleImages);
//    }
//    @Transactional
//    public void save(MultipartFile image, List<MultipartFile> images, ItemBundleDto itemBundleDto){
//        Brand brand = brandRepository.findByBrandId(itemBundleDto.getBrandId());
//        Category category = categoryRepository.findByCategoryId(itemBundleDto.getCategoryId());
//        String thumbnailUrl = image==null && images.isEmpty() ? null : s3FileStorageClient.uploadFile(image, FileType.ITEM_BUNDLE_IMAGE);
//        ItemBundle itemBundle = ItemBundle.newInstance(itemBundleDto.getName(), category, brand, true, itemBundleDto.getIsRecommend(), thumbnailUrl);
//        if(images!=null && !images.isEmpty()){
//            for(MultipartFile file: images){
//                itemBundle.addBundleImage(ItemBundleImage.newInstance(itemBundle, s3FileStorageClient.uploadFile(file, FileType.ITEM_BUNDLE_IMAGE)));
//            }
//        }
//        ItemBundleAggregation itemBundleAggregation = ItemBundleAggregation.newInstance(itemBundle);
//        itemBundleRepository.save(itemBundle);
//        itemBundleAggregationRepository.save(itemBundleAggregation);
//    }
//
//    @Transactional
//    public void delete(Long id){
//        ItemBundle itemBundle = itemBundleRepository.findByBundleId(id);
//        itemBundle.updateInActive();
//    }
//
//    @Transactional
//    public void deleteImage(Long id, Long imageId){
//        ItemBundleImage itemBundleImage = itemBundleImageRepository.findItemBundleImageById(imageId);
//        itemBundleImageRepository.delete(itemBundleImage);
//    }
}
