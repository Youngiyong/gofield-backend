package com.gofield.admin.service;


import com.fasterxml.jackson.core.type.TypeReference;
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
import com.gofield.domain.rds.domain.item.projection.ItemOptionProjection;
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
import java.util.stream.Collectors;

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

    private final ItemOptionGroupRepository itemOptionGroupRepository;

    private final ItemOptionRepository itemOptionRepository;
    private final ItemAggregationRepository itemAggregationRepository;
    private final ItemBundleAggregationRepository itemBundleAggregationRepository;
    private final ShippingTemplateRepository shippingTemplateRepository;
    private final S3FileStorageClient s3FileStorageClient;



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
    public ItemDto getUsedItem(Long id){
        List<ItemBundleDto> bundleDtoList = ItemBundleDto.of(itemBundleRepository.findAllActive());
        Item item = itemRepository.findByItemId(id);
        ItemStock itemStock = itemStockRepository.findByItemNumber(item.getItemNumber());
        List<ItemOptionProjection> itemOptionList = null;
        if(item.getIsOption()){
            itemOptionList = itemOptionRepository.findAllItemOptionByItemId(item.getId());
        }
        return ItemDto.of(bundleDtoList, item, itemStock, itemOptionList);
    }

    @Transactional(readOnly = true)
    public ItemDto getNewItem(Long id){
        List<ItemBundleDto> bundleDtoList = ItemBundleDto.of(itemBundleRepository.findAllActive());
        Item item = itemRepository.findByItemId(id);
        ItemStock itemStock = itemStockRepository.findByItemNumber(item.getItemNumber());
        List<ItemOptionProjection> itemOptionList = null;
        if(item.getIsOption()){
            itemOptionList = itemOptionRepository.findAllItemOptionByItemId(item.getId());
        }
        return ItemDto.of(bundleDtoList, item, itemStock, itemOptionList);
    }

    @Transactional
    public void updateUsedItem(MultipartFile image, List<MultipartFile> images, ItemDto itemDto){
        Item item = itemRepository.findByItemId(itemDto.getId());
        ItemDetail itemDetail = item.getDetail();
        ShippingTemplate shippingTemplate = item.getShippingTemplate();
        String optionList = AdminUtil.makeOption(itemDto);

        itemDetail.update(itemDto.getGender(), itemDto.getSpec(), itemDto.getDescription(), optionList);

        Boolean isCondition = itemDto.getDelivery().equals(EItemDeliveryFlag.CONDITION) ? true : false;

        shippingTemplate.update(
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

        String tags = itemDto.getTags()==null ? null : AdminUtil.toJsonStr(itemDto.getTags());
        Boolean isOption = itemDto.getOptionInfo()==null ? false : true;
        item.update(itemDto.getName(), item.getPrice(), item.getDeliveryPrice(), item.getDelivery(), item.getPickup(), tags, isOption);



        String thumbnail = itemDto.getThumbnail()==null ? null : itemDto.getThumbnail().replace(Constants.CDN_URL, "").replace(Constants.RESIZE_ADMIN, "");
        if(!image.isEmpty() && !image.getOriginalFilename().equals("")){
            thumbnail =  s3FileStorageClient.uploadFile(image, FileType.ITEM_BUNDLE_IMAGE);
        }
        if(images!=null && !images.isEmpty()){
            for(MultipartFile file: images){
                if(!file.getOriginalFilename().equals("")){
                    item.addImage(ItemImage.newInstance(item, s3FileStorageClient.uploadFile(file, FileType.ITEM_IMAGE), null));
                }
            }
        }

        item.updateThumbnail(thumbnail);
    }

    @Transactional
    public void updateNewItem(MultipartFile image, List<MultipartFile> images, ItemDto itemDto){
        Item item = itemRepository.findByItemId(itemDto.getId());
        ItemDetail itemDetail = item.getDetail();
        ShippingTemplate shippingTemplate = item.getShippingTemplate();
        String optionList = AdminUtil.makeOption(itemDto);

        itemDetail.update(itemDto.getGender(), itemDto.getSpec(), itemDto.getDescription(), optionList);

        Boolean isCondition = itemDto.getDelivery().equals(EItemDeliveryFlag.CONDITION) ? true : false;

        shippingTemplate.update(
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

        String tags = itemDto.getTags()==null ? null : AdminUtil.toJsonStr(itemDto.getTags());
        Boolean isOption = itemDto.getOptionInfo()==null ? false : true;
        item.update(itemDto.getName(), item.getPrice(), item.getDeliveryPrice(), item.getDelivery(), item.getPickup(), tags, isOption);

        ItemTemp itemTemp = itemTempRepository.findById(1L).get();
        ItemOptionManagerDto optionManager = null;

        if(itemDto.getOptionInfo()!=null){
            optionManager = AdminUtil.strToObject(itemDto.getOptionInfo(), new TypeReference<ItemOptionManagerDto>(){});
            List<ItemOptionItemDto> optionItemList = ItemOptionItemDto.ofEdit(optionManager.getOptionItemList());
            List<ItemNameValueDto> optionNameValueList = optionManager.getOptionGroupList();
            List<ItemOptionGroupDto> optionGroupDtoList = null;

            //groupTitle 생성
            for(ItemNameValueDto optionGroup: optionNameValueList) {
                ItemOptionGroupDto optionGroupDto = ItemOptionGroupDto.ofOption(optionGroup);
                if(optionGroup.getId()==null){
                    ItemOptionGroup itemOptionGroup = ItemOptionGroup.newInstance(item, optionManager.getOptionType(), optionGroupDto.getGroupTitle(), AdminUtil.toJsonStr(optionGroupDto.getOptionGroupList()), null);
                    item.addOptionGroup(itemOptionGroup);
                } else {
                    ItemOptionGroup itemOptionGroup = itemOptionGroupRepository.findByGroupIdAndItemId(optionGroup.getId(), item.getId());
                    itemOptionGroup.update(optionManager.getOptionType(), optionGroupDto.getGroupTitle(), AdminUtil.toJsonStr(optionGroupDto.getOptionGroupList()));
                }
            }

            for(ItemOptionItemDto optionItem: optionItemList) {
                //새로추가 되는것들
                if(optionItem.getItemNumber().equals("")){
                    List<String> optionNameList = optionItem.getValues().stream().collect(Collectors.toList());
                    List<String> viewName = new ArrayList<>();
                    for (int i = 0; i < optionNameList.size(); i++) {
                        if (i == 0) {
                            viewName.add(String.format("%s(%s)", optionGroupDtoList.get(i).getGroupTitle().trim(), optionNameList.get(i).trim()));
                        } else {
                            viewName.add(String.format(", %s(%s)", optionGroupDtoList.get(i).getGroupTitle().trim(), optionNameList.get(i).trim()));
                        }
                    }
                    ItemOption itemOption = ItemOption.newInstance(item, makeItemNumber(itemTemp.getItemNumber()), optionManager.getOptionType(), AdminUtil.toJsonStr(optionItem.getValues()), AdminUtil.toJsonStr(viewName), item.getPrice(), optionItem.getPrice());

                    int qty = optionItem.getQty();
                    if (optionManager.getOptionGroupList().equals(EItemOptionTypeFlag.SIMPLE)) {
                        //단독형은 999개로 임시로 넣기
                        qty = 999;
                    }
                    ItemStock itemStock = ItemStock.newInstance(item, optionItem.getStatus(), EItemStockFlag.OPTION, itemOption.getItemNumber(), 1L, qty);
                    item.addOption(itemOption);
                    itemTemp.update();
                    item.addStock(itemStock);
                } else {

                }

            }
            item.updateOption();
        } else {
            ItemStock itemStock = ItemStock.newInstance(item, itemDto.getStatus(), EItemStockFlag.COMMON, item.getItemNumber(), 1L, itemDto.getQty());
            item.addStock(itemStock);
            itemTemp.update();
            item.updateOption();
        }



    }

    @Transactional(readOnly = true)
    public ItemDto getItem(Long id){
        List<ItemBundleDto> bundleDtoList = ItemBundleDto.of(itemBundleRepository.findAllActive());
        if(id==null){
            return ItemDto.of(bundleDtoList);
        }
        return null;
    }

    @Transactional
    public void saveNewItem(MultipartFile image, List<MultipartFile> images, ItemDto itemDto){
        ItemBundle itemBundle = itemBundleRepository.findByBundleIdNotFetch(itemDto.getBundleId());
        Brand brand = brandRepository.findByBrandId(itemBundle.getBrand().getId());
        Category category = categoryRepository.findByCategoryId(itemBundle.getCategory().getId());
        String thumbnail = null ;
        if(!image.isEmpty() && !image.getOriginalFilename().equals("")){
            thumbnail =  s3FileStorageClient.uploadFile(image, FileType.ITEM_IMAGE);
        }

        if(thumbnail==null){
            thumbnail = itemBundle.getThumbnail();
        }
        String optionList = AdminUtil.makeOption(itemDto);
        ItemDetail itemDetail = ItemDetail.newInstance(
                itemDto.getGender(),
                itemDto.getSpec(),
                optionList,
                itemDto.getDescription());

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

        ItemOptionManagerDto optionManager = null;

        Item item = Item.newNewItemInstance(
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
                tags,
                false);

        if(images!=null && !images.isEmpty()){
            int sort = 10;
            for(MultipartFile file: images){
                if(!file.getOriginalFilename().equals("")) {
                    item.addImage(ItemImage.newInstance(item, s3FileStorageClient.uploadFile(file, FileType.ITEM_IMAGE), sort));
                    sort++;
                }
            }
        }

        if(itemDto.getOptionInfo()!=null){
            optionManager = AdminUtil.strToObject(itemDto.getOptionInfo(), new TypeReference<ItemOptionManagerDto>(){});
            List<ItemOptionItemDto> optionItemList = ItemOptionItemDto.of(optionManager.getOptionItemList());
            List<ItemOptionGroupDto> optionGroupDtoList = ItemOptionGroupDto.of(optionManager.getOptionGroupList());

            for(ItemOptionGroupDto optionGroup: optionGroupDtoList) {
                ItemOptionGroup itemOptionGroup = ItemOptionGroup.newInstance(item, optionManager.getOptionType(), optionGroup.getGroupTitle(), AdminUtil.toJsonStr(optionGroup.getOptionGroupList()), null);
                item.addOptionGroup(itemOptionGroup);
            }
            for(ItemOptionItemDto optionItem: optionItemList) {
                List<String> optionNameList = optionItem.getValues().stream().collect(Collectors.toList());
                List<String> viewName = new ArrayList<>();
                for (int i = 0; i < optionNameList.size(); i++) {
                    if (i == 0) {
                        viewName.add(String.format("%s(%s)", optionGroupDtoList.get(i).getGroupTitle().trim(), optionNameList.get(i).trim()));
                    } else {
                        viewName.add(String.format(", %s(%s)", optionGroupDtoList.get(i).getGroupTitle().trim(), optionNameList.get(i).trim()));
                    }
                }
                ItemOption itemOption = ItemOption.newInstance(item, makeItemNumber(itemTemp.getItemNumber()), optionManager.getOptionType(), AdminUtil.toJsonStr(optionItem.getValues()), AdminUtil.toJsonStr(viewName), item.getPrice(), optionItem.getPrice());

                int qty = optionItem.getQty();
                if (optionManager.getOptionGroupList().equals(EItemOptionTypeFlag.SIMPLE)) {
                    //단독형은 999개로 임시로 넣기
                    qty = 999;
                }
                ItemStock itemStock = ItemStock.newInstance(item, optionItem.getStatus(), EItemStockFlag.OPTION, itemOption.getItemNumber(), 1L, qty);
                item.addOption(itemOption);
                itemTemp.update();
                item.addStock(itemStock);
            }
            item.updateOption();
        } else {
            ItemStock itemStock = ItemStock.newInstance(item, itemDto.getStatus(), EItemStockFlag.COMMON, item.getItemNumber(), 1L, itemDto.getQty());
            item.addStock(itemStock);
            itemTemp.update();
            item.updateOption();
        }
        ItemAggregation itemAggregation = ItemAggregation.newInstance(item);
        itemDetailRepository.save(itemDetail);
        shippingTemplateRepository.save(shippingTemplate);
        itemRepository.save(item);
        itemAggregationRepository.save(itemAggregation);

//        묶음 집계 업데이트
        updateItemBundleAggregation(item.getBundle().getId(), true);
    }

    public void updateItemBundleAggregation(Long bundleId, Boolean isUpdate){
        //묶음 집계 업데이트
        ItemBundleAggregation itemBundleAggregation = itemBundleAggregationRepository.findByBundleId(bundleId);

        List<Item> itemList = itemRepository.findAllItemByBundleIdAndStatusSalesOrderByPrice(bundleId);
        List<Item> usedItemList = itemList.stream().filter(k -> k.getClassification().equals(EItemClassificationFlag.USED)).collect(Collectors.toList());
        List<Item> newItemList = itemList.stream().filter(k -> k.getClassification().equals(EItemClassificationFlag.NEW)).collect(Collectors.toList());

        int itemCount = 0;
        int newLowestPrice = 0;
        int usedLowestPrice = 0;
        int lowestPrice = 0;
        int highestPrice = 0;

        if(itemList.isEmpty()){
            itemBundleAggregation.update(0, 0, 0, 0, 0);
        } else {
            itemCount = itemList.size();
            lowestPrice = itemList.get(0).getPrice();
            highestPrice = itemList.get(itemList.size()-1).getPrice();
        }
        if(usedItemList.isEmpty()){
            usedLowestPrice = 0;
        } else {
            usedLowestPrice = usedItemList.get(0).getPrice();
        }
        if(newItemList.isEmpty()){
            newLowestPrice = 0;
        } else {
            newLowestPrice = newItemList.get(0).getPrice();
        }
        itemBundleAggregation.update(itemCount, newLowestPrice, usedLowestPrice, lowestPrice, highestPrice);
        if(isUpdate){
            itemBundleAggregation.updateRegisterDate();
        }
    }

    @Transactional
    public void delete(Long id){
        Item item = itemRepository.findByItemId(id);
        Long bundleId = item.getBundle().getId();
        item.delete();
        updateItemBundleAggregation(bundleId, false);
    }

    @Transactional
    public void saveUsedItem(MultipartFile image, List<MultipartFile> images, ItemDto itemDto){
        ItemBundle itemBundle = itemBundleRepository.findByBundleIdNotFetch(itemDto.getBundleId());
        Brand brand = brandRepository.findByBrandId(itemBundle.getBrand().getId());
        Category category = categoryRepository.findByCategoryId(itemBundle.getCategory().getId());
        String thumbnail = null ;
        if(!image.isEmpty() && !image.getOriginalFilename().equals("")){
            thumbnail =  s3FileStorageClient.uploadFile(image, FileType.ITEM_IMAGE);
        }
        String optionList = AdminUtil.makeOption(itemDto);

        ItemDetail itemDetail = ItemDetail.newInstance(
                itemDto.getGender(),
                itemDto.getSpec(),
                optionList,
                itemDto.getDescription());

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
                    sort++;
                }
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
        updateItemBundleAggregation(item.getBundle().getId(), true);
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
