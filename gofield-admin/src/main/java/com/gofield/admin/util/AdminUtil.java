package com.gofield.admin.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.admin.dto.ItemDto;
import com.gofield.admin.dto.ItemKeyValueDto;
import com.gofield.admin.dto.ItemUsedBundleDto;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
;

public class AdminUtil {

    public static String toJsonStr(Object from) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.writeValueAsString(from);
        }catch (IOException e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
        }
    }

    public static <T> T toObject(Object from, Class<T> target) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(toJsonStr(from), target);
        }catch (IOException e){
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
        }
    }

    public static <T> T strToObject(String from, TypeReference<T> target) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(from, target);
        }catch (IOException e){
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
        }
    }

    public static String makeItemNumber(Long itemNumber){
        return String.format("A%s", itemNumber);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");

        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.isEmpty()) {
            return null;
        }

        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            return null;
        }

        String addr = String.valueOf(ipAddress);
        if (addr.contains("/")) {
            addr = addr.replaceAll("/", "");
        }

        return addr;
    }

    public static String makeOption(ItemDto itemDto){
        List<ItemKeyValueDto> result = new ArrayList<>();
        if(itemDto.getManufacturer()!=null){
            result.add(ItemKeyValueDto.of("제조사", itemDto.getManufacturer()));
        }
        if(itemDto.getOrigin()!=null){
            result.add(ItemKeyValueDto.of("원산지", itemDto.getOrigin()));
        }
        if(itemDto.getLength()!=null){
            result.add(ItemKeyValueDto.of("길이", itemDto.getLength()));
        }
        if(itemDto.getWeight()!=null){
            result.add(ItemKeyValueDto.of("중량", itemDto.getWeight()));
        }
        if(itemDto.getIsAs()!=null){
            result.add(ItemKeyValueDto.of("AS 가능여부", itemDto.getIsAs()));
        }
        return AdminUtil.toJsonStr(result);
    }

    public static String makeOption(ItemUsedBundleDto itemDto){
        List<ItemKeyValueDto> result = new ArrayList<>();
        if(itemDto.getManufacturer()!=null){
            result.add(ItemKeyValueDto.of("제조사", itemDto.getManufacturer()));
        }
        if(itemDto.getOrigin()!=null){
            result.add(ItemKeyValueDto.of("원산지", itemDto.getOrigin()));
        }
        if(itemDto.getLength()!=null){
            result.add(ItemKeyValueDto.of("길이", itemDto.getLength()));
        }
        if(itemDto.getWeight()!=null){
            result.add(ItemKeyValueDto.of("중량", itemDto.getWeight()));
        }
        if(itemDto.getIsAs()!=null){
            result.add(ItemKeyValueDto.of("AS 가능여부", itemDto.getIsAs()));
        }
        return AdminUtil.toJsonStr(result);
    }

}
