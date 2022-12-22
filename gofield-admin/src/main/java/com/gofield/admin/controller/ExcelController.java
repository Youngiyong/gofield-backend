package com.gofield.admin.controller;

import com.gofield.admin.dto.OrderCancelDto;
import com.gofield.admin.dto.OrderChangeDto;
import com.gofield.admin.dto.OrderReturnDto;
import com.gofield.admin.dto.OrderShippingDto;
import com.gofield.admin.dto.response.projection.BrandInfoProjectionResponse;
import com.gofield.admin.dto.response.projection.ItemBundleInfoProjectionResponse;
import com.gofield.admin.dto.response.projection.ItemInfoProjectionResponse;
import com.gofield.admin.service.ExcelService;
import com.gofield.common.excel.ExcelFile;
import com.gofield.common.excel.poi.PoiSheetExcelFile;
import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import com.gofield.domain.rds.domain.order.EOrderCancelStatusFlag;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
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
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=excelFile.xlsx");
        List<BrandInfoProjectionResponse> result = excelService.downloadBrands(keyword);
        ExcelFile excelFile = new PoiSheetExcelFile(result, BrandInfoProjectionResponse.class);
        excelFile.write(response.getOutputStream());
    }

    @GetMapping("/bundle")
    public void downloadBundleListExcel(@RequestParam(required = false) String keyword,
                                       HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=excelFile.xlsx");
        List<ItemBundleInfoProjectionResponse> result = excelService.downloadBundles(keyword);
        ExcelFile excelFile = new PoiSheetExcelFile(result, ItemBundleInfoProjectionResponse.class);
        excelFile.write(response.getOutputStream());
    }

    @GetMapping("/item")
    public void downloadItemListExcel(@RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) EItemStatusFlag status,
                                      HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=excelFile.xlsx");
        List<ItemInfoProjectionResponse> result = excelService.downloadItems(keyword, status);
        ExcelFile excelFile = new PoiSheetExcelFile(result, ItemInfoProjectionResponse.class);
        excelFile.write(response.getOutputStream());
    }

    @GetMapping("/order")
    public void downloadOrderShipping(@RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) EOrderShippingStatusFlag status,
                                      HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=excelFile.xlsx");
        List<OrderShippingDto> result = excelService.downloadOrderShipping(keyword, status);
        ExcelFile excelFile = new PoiSheetExcelFile(result, OrderShippingDto.class);
        excelFile.write(response.getOutputStream());
    }

    @GetMapping("/cancel")
    public void downloadOrderCancel(@RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) EOrderCancelStatusFlag status,
                                      HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=excelFile.xlsx");
        List<OrderCancelDto> result = excelService.downloadOrderCancels(keyword, status);
        ExcelFile excelFile = new PoiSheetExcelFile(result, OrderCancelDto.class);
        excelFile.write(response.getOutputStream());
    }

    @GetMapping("/change")
    public void downloadOrderChange(@RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) EOrderCancelStatusFlag status,
                                    HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=excelFile.xlsx");
        List<OrderChangeDto> result = excelService.downloadOrderChanges(keyword, status);
        ExcelFile excelFile = new PoiSheetExcelFile(result, OrderChangeDto.class);
        excelFile.write(response.getOutputStream());
    }

    @GetMapping("/return")
    public void downloadOrderReturn(@RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) EOrderCancelStatusFlag status,
                                    HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=excelFile.xlsx");
        List<OrderReturnDto> result = excelService.downloadOrderReturns(keyword, status);
        ExcelFile excelFile = new PoiSheetExcelFile(result, OrderReturnDto.class);
        excelFile.write(response.getOutputStream());
    }
}
