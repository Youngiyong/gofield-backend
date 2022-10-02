package com.gofield.api.util;

import com.gofield.api.dto.response.ClientResponse;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Base64;

public class ApiUtil {

    public static Integer stringConvertToIntVersion(String[] version){
        int intVersion = 0;

        intVersion += 10000 * Integer.parseInt(version[0]);
        intVersion += 100 * Integer.parseInt(version[1]);
        intVersion += 1 * Integer.parseInt(version[2]);

        return intVersion;
    }

    public static ClientResponse base64EncodingStrToDecodeClientDetail(String encodingStr){

        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodingStr);
            String decodedString = new String(decodedBytes);

            return new Gson().fromJson(decodedString, ClientResponse.class);
        } catch (Exception e){
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, "base 64 decoding error");
        }


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

}
