package com.testerhome.android.app.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Bin Li on 2016/6/14.
 */

public class StringUtils {
    public static String formatPublishDateTime(String begin) {
        if (TextUtils.isEmpty(begin)){
            return "";
        }
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
        SimpleDateFormat outDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String time = "";
        try {
            Date date = sDate.parse(begin);
            Date currentdate = new Date(System.currentTimeMillis());

            long diff = currentdate.getTime() - date.getTime();
            if (diff < 1000 * 60 * 60) {
                // 一个小时内
                time = "刚刚";
            } else if (diff < 1000 * 60 * 60 * 24) {
                // 一天内
                // 1h
                long hours = diff / (1000 * 60 * 60);
                time = hours + " 小时 前";
            } else if (diff < 1000 * 60 * 60 * 24 * 7) {
                // 7天内
                // 1d
                long days = diff / (1000 * 60 * 60 * 24);
                time = days + " 天 前";
            } else {
                time = outDate.format(date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
