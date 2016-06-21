package com.testerhome.android.app.auth;

import android.accounts.AccountManager;
import android.content.Context;

/**
 * Created by Bin Li on 2016/6/21.
 */

public class AppAccountService {

    private volatile static AppAccountService mInstance;
    private Context mContext;
    private AccountManager mAccountManager;

    public static AppAccountService getInstance(Context ctx){
        if (mInstance == null){
            synchronized (AppAccountService.class) {
                if (mInstance == null) {
                    mInstance = new AppAccountService(ctx.getApplicationContext());
                }
            }
        }
        return mInstance;
    }


    public AppAccountService(Context context) {
        this.mContext = context;

    }
}
