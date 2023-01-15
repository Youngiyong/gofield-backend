package com.gofield.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpServletUtil {

    public static String getFullUrlWithMethod(HttpServletRequest request) {
        return request.getMethod() + " " + request.getRequestURI() + getQuery(request);
    }

    private static String getQuery(HttpServletRequest request) {
        if (request.getQueryString()==null) {
            return null;
        }
        return "?" + request.getQueryString();
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();
        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    public static String getRequestBody(ContentCachingRequestWrapper request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    return new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException exception) {
                    return " - ";
                }
            }
        }
        return " - ";
    }


}
