package com.testerhome.android.app.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bin Li on 2016/6/26.
 */

public class SharedPreferencesHelper {

    private static final String APP_NAME = "com.testerhome.android";
    private SharedPreferences mSharedPreferences;

    public static void setValue(Context ctx, String key, String value){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(APP_NAME, 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getValue(Context ctx, String key){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(APP_NAME, 0);

        return sharedPreferences.getString(key, "");
    }

    public static void remove(Context ctx, String key) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(APP_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
