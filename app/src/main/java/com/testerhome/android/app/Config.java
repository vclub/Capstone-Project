package com.testerhome.android.app;

/**
 * Created by Bin Li on 2016/6/13.
 */

public class Config {

    public static final String BASE_URL = "https://testerhome.com/api/v3/";
    public static final String TOPICS_TYPE_RECENT = "recent";

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
