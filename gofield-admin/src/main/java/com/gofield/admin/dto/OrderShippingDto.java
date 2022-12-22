package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.order.EOrderCancelReasonFlag;
import com.gofield.domain.rds.domain.order.OrderItem;
import com.gofield.domain.rds.domain.order.OrderShipping;
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
public class OrderShippingDto {
    private Long id;

    @ExcelColumn(headerName = "주문번호", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String orderNumber;

    @ExcelColumn(headerName = "배송번호", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String shippingNumber;

    @ExcelColumn(headerName = "배송상태", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String status;

    @ExcelColumn(headerName = "주문날짜", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String createDate;

    @ExcelColumn(headerName = "상품명", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String name;

    @ExcelColumn(headerName = "상품옵션명", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String optionName;
    @ExcelColumn(headerName = "상품가격", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private int price;

    @ExcelColumn(headerName = "상품가격", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private int qty;

    @ExcelColumn(headerName = "운송사", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String carrier;

    @ExcelColumn(headerName = "송장번호", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String trackingNumber;

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

    private List<CodeDto> codeList;

    private EOrderCancelReasonFlag reason;

    private OrderCancelItemTempDto cancel;


    @Builder
    private OrderShippingDto(Long id, String orderNumber, String shippingNumber, String status, String createDate, String name,
                             String optionName, int price, int qty, String carrier, String trackingNumber, String customerTel, String customerName,
                             String address, String addressExtra, String zipCode, List<CodeDto> codeList, EOrderCancelReasonFlag reason, OrderCancelItemTempDto cancel){
        this.id = id;
        this.orderNumber = orderNumber;
        this.shippingNumber = shippingNumber;
        this.status = status;
        this.createDate = createDate;
        this.name = name;
        this.optionName = optionName;
        this.price = price;
        this.qty = qty;
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        this.customerTel = customerTel;
        this.customerName = customerName;
        this.address = address;
        this.addressExtra = addressExtra;
        this.zipCode = zipCode;
        this.codeList = codeList;
        this.reason = reason;
        this.cancel = cancel;
    }


    public static OrderShippingDto of(OrderShipping orderShipping, List<CodeDto> codeList, OrderCancelItemTempDto cancel){
        OrderItem orderItem = orderShipping.getOrderItems().get(0);
        int qty = orderItem.getOrderItemOption() == null ? orderItem.getQty() : orderItem.getOrderItemOption().getQty();
        int price = orderItem.getOrderItemOption() == null ? orderItem.getPrice() : orderItem.getOrderItemOption().getPrice();
        String optionName = orderItem.getOrderItemOption() == null ? null : orderItem.getOrderItemOption().getName();
        return OrderShippingDto.builder()
                .id(orderShipping.getId())
                .orderNumber(orderShipping.getOrderNumber())
                .shippingNumber(orderShipping.getShippingNumber())
                .status(orderShipping.getStatus().getDescription())
                .createDate(orderShipping.getCreateDate().toString())
                .name(orderItem.getName())
                .optionName(optionName)
                .qty(qty)
                .price(price)
                .optionName(optionName)
                .carrier(orderShipping.getCarrier())
                .trackingNumber(orderShipping.getTrackingNumber())
                .customerTel(orderItem.getOrder().getShippingAddress().getTel())
                .customerName(orderItem.getOrder().getShippingAddress().getName())
                .address(orderItem.getOrder().getShippingAddress().getAddress())
                .addressExtra(orderItem.getOrder().getShippingAddress().getAddressExtra())
                .zipCode(orderItem.getOrder().getShippingAddress().getZipCode())
                .codeList(codeList)
                .reason(EOrderCancelReasonFlag.CANCEL_REASON_101)
                .cancel(cancel)
                .build();
    }

    public static List<OrderShippingDto> of(List<OrderShipping> list, List<CodeDto> codeList){
        return list.stream()
                .map(p -> OrderShippingDto.of(p, codeList, null))
                .collect(Collectors.toList());
    }
}
