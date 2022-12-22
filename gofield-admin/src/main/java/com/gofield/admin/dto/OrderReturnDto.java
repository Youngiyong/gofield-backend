package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.order.EOrderCancelReasonFlag;
import com.gofield.domain.rds.domain.order.Order;
import com.gofield.domain.rds.domain.order.OrderCancel;
import com.gofield.domain.rds.domain.order.OrderCancelItem;
import com.lannstark.ExcelColumn;
import com.lannstark.ExcelColumnStyle;
import com.lannstark.style.DefaultExcelCellStyle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderReturnDto {
    private Long id;

    @ExcelColumn(headerName = "주문번호", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String orderNumber;

    @ExcelColumn(headerName = "주문날짜", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String orderDate;

    @ExcelColumn(headerName = "취소상태", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String status;

    @ExcelColumn(headerName = "취소날짜", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String cancelDate;

    private EOrderCancelReasonFlag reason;

    @ExcelColumn(headerName = "반품사유", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String returnReason;

    @ExcelColumn(headerName = "취소내용", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String comment;

    @ExcelColumn(headerName = "상품명", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String name;

    @ExcelColumn(headerName = "상품옵션명", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String optionName;
    @ExcelColumn(headerName = "상품가격", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private int price;

    @ExcelColumn(headerName = "상품가격", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private int qty;

    @ExcelColumn(headerName = "배송지 전화번호", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String customerTel;

    @ExcelColumn(headerName = "수령자 이름", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String customerName;

    @ExcelColumn(headerName = "수령자 주소", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String address;

    @ExcelColumn(headerName = "수령자 상세주소", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String addressExtra;

    @ExcelColumn(headerName = "우편번호", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String zipCode;



    @Builder
    private OrderReturnDto(Long id, String orderNumber, String orderDate, String status, String cancelDate, EOrderCancelReasonFlag reason, String comment, String name,
                           String optionName, int price, int qty, String customerTel, String customerName,
                           String address, String addressExtra, String zipCode, String returnReason){
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.status = status;
        this.cancelDate = cancelDate;
        this.reason = reason;
        this.comment = comment;
        this.name = name;
        this.optionName = optionName;
        this.price = price;
        this.qty = qty;
        this.customerTel = customerTel;
        this.customerName = customerName;
        this.address = address;
        this.addressExtra = addressExtra;
        this.zipCode = zipCode;
        this.returnReason = returnReason;
    }


    public static OrderReturnDto of(OrderCancel orderCancel){
        OrderCancelItem orderCancelItem = orderCancel.getOrderCancelItems().get(0);
        Order order = orderCancel.getOrder();
        int qty = orderCancelItem.getQty();
        int price = orderCancelItem.getPrice();
        return OrderReturnDto.builder()
                .id(orderCancel.getId())
                .orderNumber(orderCancel.getOrder().getOrderNumber())
                .orderDate(orderCancel.getOrder().getCreateDate().toString())
                .cancelDate(orderCancel.getCreateDate().toString())
                .status(orderCancel.getStatus().getDescription())
                .reason(orderCancel.getReason())
                .comment(orderCancel.getOrderCancelComment().getContent())
                .name(orderCancelItem.getName())
                .optionName(orderCancelItem.getOptionName())
                .price(orderCancelItem.getPrice())
                .qty(orderCancelItem.getQty())
                .qty(qty)
                .price(price)
                .customerTel(order.getShippingAddress().getTel())
                .customerName(order.getShippingAddress().getName())
                .address(order.getShippingAddress().getAddress())
                .addressExtra(order.getShippingAddress().getAddressExtra())
                .zipCode(order.getShippingAddress().getZipCode())
                .returnReason(orderCancel.getReason().getDescription())
                .build();
    }

    public static List<OrderReturnDto> of(List<OrderCancel> list){
        return list.stream()
                .map(p -> OrderReturnDto.of(p))
                .collect(Collectors.toList());
    }
}