package com.testerhome.android.app.util;

import android.text.TextUtils;

/**
 * Created by Bin Li on 2016/6/14.
 */

public class ImageUrlConvert {

    public static String getImageUrl(String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            return "";
        } else if (imagePath.startsWith("//testerhome.com")) {
            return "https:".concat(imagePath);
        } else if (!imagePath.contains("https://testerhome.com")) {
            return "https://testerhome.com".concat(imagePath);
        } else {
            return imagePath;
        }

    }
}
