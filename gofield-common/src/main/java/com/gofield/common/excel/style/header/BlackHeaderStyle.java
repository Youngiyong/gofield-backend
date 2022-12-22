package com.gofield.common.excel.style.header;

import com.gofield.common.excel.style.CustomExcelCellStyle;
import com.gofield.common.excel.style.align.DefaultExcelAlign;
import com.gofield.common.excel.style.border.DefaultExcelBorders;
import com.gofield.common.excel.style.border.ExcelBorderStyle;
import com.gofield.common.excel.style.configurer.ExcelCellStyleConfigurer;

public class BlackHeaderStyle extends CustomExcelCellStyle {

    @Override
    public void configure(ExcelCellStyleConfigurer configurer) {

        configurer.foregroundColor(0, 0, 0)
                .excelBorders(DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN))
                .excelAlign(DefaultExcelAlign.CENTER_CENTER);
    }

}