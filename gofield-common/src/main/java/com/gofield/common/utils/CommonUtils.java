package com.gofield.common.utils;

import com.gofield.common.model.Constants;

public class CommonUtils {
    public static String makeCloudFrontUrlResize(String image){
        if(image==null) return null;
        return Constants.CDN_URL.concat(image).concat(Constants.RESIZE_120x120);
    }
    public static String makeCloudFrontUrlResize260(String image){
        if(image==null) return null;
        return Constants.CDN_URL.concat(image).concat(Constants.RESIZE_260x260);
    }
    public static String makeCloudFrontUrlResize460(String image){
        if(image==null) return null;
        return Constants.CDN_URL.concat(image).concat(Constants.RESIZE_460x460);
    }

    public static String makeCloudFrontUrl(String image){
        if(image==null) return null;
        return Constants.CDN_URL.concat(image);
    }

    public static String makeCloudFrontAdminUrl(String image){
        if(image==null) return null;
        return Constants.CDN_URL.concat(image).concat(Constants.RESIZE_ADMIN);

    }
}
