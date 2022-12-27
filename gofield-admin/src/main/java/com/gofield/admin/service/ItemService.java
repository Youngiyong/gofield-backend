package com.gofield.admin.service;


import com.gofield.admin.dto.*;
import com.gofield.admin.dto.response.projection.ItemBundleInfoProjectionResponse;
import com.gofield.admin.dto.response.projection.ItemInfoProjectionResponse;
import com.gofield.admin.util.AdminUtil;
import com.gofield.common.model.Constants;
import com.gofield.common.utils.CommonUtils;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ItemStockRepository itemStockRepository;
    private final ItemTempRepository itemTempRepository;
    private final ItemBundleRepository itemBundleRepository;
    private final ItemDetailRepository itemDetailRepository;
    private final ItemAggregationRepository itemAggregationRepository;
    private final ItemBundleImageRepository itemBundleImageRepository;
    private final ItemBundleAggregationRepository itemBundleAggregationRepository;
    private final ShippingTemplateRepository shippingTemplateRepository;
    private final S3FileStorageClient s3FileStorageClient;

    public String makeOption(ItemDto itemDto){
        List<ItemKeyValueDto> result = new ArrayList<>();
        if(itemDto.getManufacturer()!=null){
            result.add(ItemKeyValueDto.of("제조사", itemDto.getManufacturer()));
        }
        if(itemDto.getOrigin()!=null){
            result.add(ItemKeyValueDto.of("원산지", itemDto.getOrigin()));
        }
        if(itemDto.getLength()!=null){
            result.add(ItemKeyValueDto.of("길이", itemDto.getLength()));
        }
        if(itemDto.getWeight()!=null){
            result.add(ItemKeyValueDto.of("중량", itemDto.getWeight()));
        }
        if(itemDto.getIsAs()!=null){
            String as = itemDto.getIsAs() ? "가능" : "불가능";
            result.add(ItemKeyValueDto.of("AS 가능여부", as));
        }
        return AdminUtil.toJsonStr(result);
    }

    public String makeItemNumber(int itemTempNumber){
        return "G" + itemTempNumber;
    }


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
        Brand brand = brandRepository.findByBrandId(itemDto.getBrandId());
        Category category = categoryRepository.findByCategoryId(itemDto.getCategoryId());
        ItemBundle itemBundle = itemBundleRepository.findByBundleId(itemDto.getBundleId());
        String thumbnail = null ;
        if(!image.isEmpty() && !image.getOriginalFilename().equals("")){
            thumbnail =  s3FileStorageClient.uploadFile(image, FileType.ITEM_IMAGE);
        }
        String optionList = makeOption(itemDto);
        ItemDetail itemDetail = ItemDetail.newInstance(
                itemDto.getGender(),
                itemDto.getSpec(),
                optionList,
                null);

        Boolean isCondition = itemDto.getDelivery().equals(EItemDeliveryFlag.CONDITION) ? true : false;
        /*
        ToDo: 셀러 관리 나오면 셀러 선택 처리
         */
        ShippingTemplate shippingTemplate = ShippingTemplate.newInstance(
                1L,
                        isCondition,
                        itemDto.getShippingTemplate().getCondition(),
                        itemDto.getShippingTemplate().getChargeType(),
                        itemDto.getShippingTemplate().getCharge(),
                        itemDto.getShippingTemplate().getIsPaid(),
                        itemDto.getShippingTemplate().getExchangeCharge(),
                        itemDto.getShippingTemplate().getTakebackCharge(),
                        itemDto.getShippingTemplate().getIsFee(),
                        itemDto.getShippingTemplate().getFeeJeju(),
                        itemDto.getShippingTemplate().getFeeJejuBesides(),
                        itemDto.getShippingTemplate().getZipCode(),
                        itemDto.getShippingTemplate().getAddress(),
                        itemDto.getShippingTemplate().getAddressExtra(),
                        itemDto.getShippingTemplate().getReturnZipCode(),
                        itemDto.getShippingTemplate().getReturnAddress(),
                        itemDto.getShippingTemplate().getReturnAddressExtra());

        ItemTemp itemTemp = itemTempRepository.findById(1L).get();

        String tags = itemDto.getTags()==null ? null : AdminUtil.toJsonStr(itemDto.getTags());
        Item item = Item.newUsedItemInstance(
                itemBundle,
                brand,
                category,
                itemDetail,
                shippingTemplate,
                1L,
                thumbnail,
                makeItemNumber(itemTemp.getItemNumber()),
                itemDto.getName(),
                itemDto.getPrice(),
                itemDto.getDeliveryPrice(),
                itemDto.getPickup(),
                itemDto.getDelivery(),
                tags);

        if(images!=null && !images.isEmpty()){
            int sort = 10;
            for(MultipartFile file: images){
                if(!file.getOriginalFilename().equals("")) {
                    item.addImage(ItemImage.newInstance(item, s3FileStorageClient.uploadFile(file, FileType.ITEM_IMAGE), sort));
                }
                sort++;
            }
        }
        ItemStock itemStock = ItemStock.newInstance(item, EItemStatusFlag.SALE, EItemStockFlag.COMMON, item.getItemNumber(), 1L, itemDto.getQty());
        ItemAggregation itemAggregation = ItemAggregation.newInstance(item);
        itemDetailRepository.save(itemDetail);
        shippingTemplateRepository.save(shippingTemplate);
        itemRepository.save(item);
        itemAggregationRepository.save(itemAggregation);
        itemStockRepository.save(itemStock);
        itemTemp.update();

        //묶음 집계 업데이트
        ItemBundleAggregation itemBundleAggregation = itemBundleAggregationRepository.findByBundleId(item.getBundle().getId());
        Item resultItem = itemRepository.findLowestItemByBundleIdAndClassification(itemStock.getItem().getBundle().getId(), itemStock.getItem().getClassification());
        Item lowestPriceItem = itemRepository.findLowestItemByBundleId(itemStock.getItem().getBundle().getId());
        Item highestPriceItem = itemRepository.findHighestItemByBundleId(itemStock.getItem().getBundle().getId());
        int updatePrice = resultItem == null ? item.getPrice() : resultItem.getPrice();
        int lowestPrice = lowestPriceItem == null ? item.getPrice() : resultItem.getPrice();
        int highestPrice = highestPriceItem == null ? item.getPrice() : resultItem.getPrice();
        itemBundleAggregation.updateAggregationPrice(itemStock.getItem().getClassification(), updatePrice, lowestPrice, highestPrice);
        itemBundleAggregation.updateItemPlusOne();
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
