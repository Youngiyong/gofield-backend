package com.gofield.admin.dto.response.projection;

import com.gofield.common.excel.annotation.ExcelColumn;
import com.gofield.common.excel.annotation.ExcelColumnStyle;
import com.gofield.common.excel.style.DefaultExcelCellStyle;
import com.gofield.common.utils.CommonUtils;
import com.gofield.common.utils.LocalDateTimeUtils;
import com.gofield.domain.rds.domain.item.Brand;
import com.gofield.domain.rds.domain.item.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CategoryInfoProjectionResponse {
    @ExcelColumn(headerName = "카테고리아이디", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private Long id;
    @ExcelColumn(headerName = "카테고리명", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String name;
    @ExcelColumn(headerName = "생성날짜", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String createDate;


    @Builder
    private CategoryInfoProjectionResponse(Long id, String name, String createDate){
        this.id = id;
        this.name = name;
        this.createDate = createDate;
    }

    public static CategoryInfoProjectionResponse of(Long id, String name, LocalDateTime createDate){
        return CategoryInfoProjectionResponse.builder()
                .id(id)
                .name(name)
                .createDate(LocalDateTimeUtils.LocalDateTimeToString(createDate))
                .build();
    }

    public static List<CategoryInfoProjectionResponse> of(List<Category> list){
        return list.stream()
                .map(p -> CategoryInfoProjectionResponse.of(p.getId(), p.getName(), p.getCreateDate()))
                .collect(Collectors.toList());
    }
}
