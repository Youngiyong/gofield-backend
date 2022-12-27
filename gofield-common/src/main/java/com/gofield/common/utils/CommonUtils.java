package com.gofield.common.utils;

import com.gofield.common.model.Constants;

public class CommonUtils {
    public static String makeCloudFrontUrl(String image){
        if(image==null) return null;
        return Constants.CDN_URL.concat(image);
    }
}
