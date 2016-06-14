package com.testerhome.android.app.util;

/**
 * Created by Bin Li on 2016/6/14.
 */

public class ImageUrlConvert {

    public static String getImageUrl(String imagePath){
        if (imagePath.startsWith("//testerhome.com")){
            return "https:".concat(imagePath);
        } else if (!imagePath.contains("https://testerhome.com")) {
            return "https://testerhome.com".concat(imagePath);
        }else{
            return imagePath;
        }

    }
}
