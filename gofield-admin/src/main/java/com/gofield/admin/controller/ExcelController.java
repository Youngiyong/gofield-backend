package com.gofield.admin.controller;

import com.gofield.admin.dto.OrderShippingDto;
import com.gofield.admin.dto.response.projection.BrandInfoProjectionResponse;
import com.gofield.admin.dto.response.projection.ItemBundleInfoProjectionResponse;
import com.gofield.admin.dto.response.projection.ItemInfoProjectionResponse;
import com.gofield.admin.service.ExcelService;
import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.lannstark.excel.ExcelFile;
import com.lannstark.excel.onesheet.OneSheetExcelFile;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("/excel")
@RestController
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping("/brand")
    public void downloadBrandListExcel(@RequestParam(required = false) String keyword,
                                       HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        List<BrandInfoProjectionResponse> result = excelService.downloadBrands(keyword);
        ExcelFile excelFile = new OneSheetExcelFile<>(result, BrandInfoProjectionResponse.class);
        excelFile.write(response.getOutputStream());
    }

    @GetMapping("/bundle")
    public void downloadBundleListExcel(@RequestParam(required = false) String keyword,
                                       HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        List<ItemBundleInfoProjectionResponse> result = excelService.downloadBundles(keyword);
        ExcelFile excelFile = new OneSheetExcelFile<>(result, ItemBundleInfoProjectionResponse.class);
        excelFile.write(response.getOutputStream());
    }

    @GetMapping("/item")
    public void downloadItemListExcel(@RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) EItemStatusFlag status,
                                      HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        List<ItemInfoProjectionResponse> result = excelService.downloadItems(keyword, status);
        ExcelFile excelFile = new OneSheetExcelFile<>(result, ItemInfoProjectionResponse.class);
        excelFile.write(response.getOutputStream());
    }

    @GetMapping("/order")
    public void downloadOrderShipping(@RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) EOrderShippingStatusFlag status,
                                      HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        List<OrderShippingDto> result = excelService.downloadOrderShipping(keyword, status);
        ExcelFile excelFile = new OneSheetExcelFile<>(result, OrderShippingDto.class);
        excelFile.write(response.getOutputStream());
    }
}
