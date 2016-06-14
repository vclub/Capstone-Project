package com.testerhome.android.app.networks;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.testerhome.android.app.Config;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bin Li on 2016/6/14.
 */

public class TesterHomeApi {

    private static TesterHomeApi mInstance;
    private TesterHomeService mService;

    public static TesterHomeApi getInstance(){
        if (mInstance == null){
            mInstance = new TesterHomeApi();
        }
        return mInstance;
    }

    public TesterHomeApi() {
        this.mService = buildRestAdapter().create(TesterHomeService.class);
    }

    private Retrofit buildRestAdapter(){
        return new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new StethoInterceptor()).build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public TesterHomeService getService() {
        return mService;
    }
}
