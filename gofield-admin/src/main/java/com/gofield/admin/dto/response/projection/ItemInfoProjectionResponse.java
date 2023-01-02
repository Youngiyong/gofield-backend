package com.gofield.admin.dto.response.projection;

import com.gofield.common.excel.annotation.ExcelColumn;
import com.gofield.common.excel.annotation.ExcelColumnStyle;
import com.gofield.common.excel.style.DefaultExcelCellStyle;
import com.gofield.common.model.Constants;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.projection.ItemInfoProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ItemInfoProjectionResponse {
    @ExcelColumn(headerName = "상품아이디", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private Long id;
    @ExcelColumn(headerName = "상품명", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String name;
    @ExcelColumn(headerName = "분류", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String classification;
    @ExcelColumn(headerName = "상품가격", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private int price;
    @ExcelColumn(headerName = "카테고리명", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String categoryName;
    @ExcelColumn(headerName = "상태", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String status;
    @ExcelColumn(headerName = "대표이미지", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String thumbnail;
    @ExcelColumn(headerName = "생성일자", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String createDate;

    @Builder
    private ItemInfoProjectionResponse(Long id, String name, String classification, int price, String categoryName, String status, String thumbnail, String createDate){
        this.id = id;
        this.name = name;
        this.classification = classification;
        this.price = price;
        this.categoryName = categoryName;
        this.status = status;
        this.thumbnail = thumbnail;
        this.createDate = createDate;
    }

    public static ItemInfoProjectionResponse of(ItemInfoProjection itemInfoProjection){
        return ItemInfoProjectionResponse.builder()
                .id(itemInfoProjection.getId())
                .name(itemInfoProjection.getName())
                .classification(itemInfoProjection.getClassification().getDescription())
                .price(itemInfoProjection.getPrice())
                .categoryName(itemInfoProjection.getCategoryName())
                .status(itemInfoProjection.getStatus().getDescription())
                .thumbnail(CommonUtils.makeCloudFrontAdminUrl(itemInfoProjection.getThumbnail()))
                .createDate(itemInfoProjection.getCreateDate().toLocalDate().toString())
                .build();
    }

    public static List<ItemInfoProjectionResponse> of(List<ItemInfoProjection> list){
        return list.stream()
                .map(p -> ItemInfoProjectionResponse.of(p))
                .collect(Collectors.toList());
    }
}
