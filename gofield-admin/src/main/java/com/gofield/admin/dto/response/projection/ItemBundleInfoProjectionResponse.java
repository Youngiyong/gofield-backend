package com.gofield.admin.dto.response.projection;

import com.gofield.common.excel.annotation.ExcelColumn;
import com.gofield.common.excel.annotation.ExcelColumnStyle;
import com.gofield.common.excel.style.DefaultExcelCellStyle;
import com.gofield.common.model.Constants;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.ItemBundle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ItemBundleInfoProjectionResponse {
    @ExcelColumn(headerName = "묶음아이디", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private Long id;
    @ExcelColumn(headerName = "카테고리명", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String categoryName;
    @ExcelColumn(headerName = "브랜드명", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String brandName;
    @ExcelColumn(headerName = "묶음상품명", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String name;
    @ExcelColumn(headerName = "묶음상품명", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String isActive;
    @ExcelColumn(headerName = "대표이미지", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String thumbnail;
    @ExcelColumn(headerName = "생성일자", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String createDate;


    @Builder
    private ItemBundleInfoProjectionResponse(Long id, String categoryName, String brandName, String name, String isActive, String thumbnail, String createDate){
        this.id = id;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.name = name;
        this.isActive = isActive;
        this.thumbnail = thumbnail;
        this.createDate = createDate;
    }

    public static ItemBundleInfoProjectionResponse of(ItemBundle itemBundle){
        return ItemBundleInfoProjectionResponse.builder()
                .id(itemBundle.getId())
                .categoryName(itemBundle.getCategory().getName())
                .brandName(itemBundle.getBrand().getName())
                .name(itemBundle.getName())
                .isActive(itemBundle.getIsActive() ? "활성" : "비활성")
                .thumbnail(itemBundle.getThumbnail()==null ? null : CommonUtils.makeCloudFrontAdminUrl(itemBundle.getThumbnail()))
                .createDate(itemBundle.getCreateDate().toLocalDate().toString())
                .build();
    }

    public static List<ItemBundleInfoProjectionResponse> of(List<ItemBundle> list){
        return list.stream()
                .map(p -> ItemBundleInfoProjectionResponse.of(p))
                .collect(Collectors.toList());
    }
}
