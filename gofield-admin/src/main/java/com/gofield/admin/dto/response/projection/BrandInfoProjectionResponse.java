package com.gofield.admin.dto.response.projection;

import com.gofield.common.excel.annotation.ExcelColumn;
import com.gofield.common.excel.annotation.ExcelColumnStyle;
import com.gofield.common.excel.style.DefaultExcelCellStyle;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.Brand;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BrandInfoProjectionResponse {
    @ExcelColumn(headerName = "브랜드아이디", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private Long id;
    @ExcelColumn(headerName = "브랜드명", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String name;

    private String thumbnail;
    @ExcelColumn(headerName = "활성화여부", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String isVisible;
    @ExcelColumn(headerName = "생성날짜", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String createDate;


    @Builder
    private BrandInfoProjectionResponse(Long id, String name, String thumbnail, String isVisible, String createDate){
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.isVisible = isVisible;
        this.createDate = createDate;
    }

    public static BrandInfoProjectionResponse of(Long id, String name, String thumbnail, String isVisible, String createDate){
        return BrandInfoProjectionResponse.builder()
                .id(id)
                .name(name)
                .thumbnail(thumbnail)
                .isVisible(isVisible)
                .createDate(createDate)
                .build();
    }

    public static List<BrandInfoProjectionResponse> of(List<Brand> list){
        return list.stream()
                .map(p -> BrandInfoProjectionResponse.of(p.getId(), p.getName(), p.getThumbnail()==null ? null : CommonUtils.makeCloudFrontUrl(p.getThumbnail()),  p.getIsVisible() ? "활성" : "숨김", p.getCreateDate().toLocalDate().toString()))
                .collect(Collectors.toList());
    }
}
