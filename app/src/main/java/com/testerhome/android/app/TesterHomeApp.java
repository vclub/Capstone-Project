package com.testerhome.android.app;

import android.app.Application;
import android.os.Build;
import android.webkit.WebView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.squareup.leakcanary.LeakCanary;

import okhttp3.OkHttpClient;

/**
 * Created by Bin Li on 2016/6/12.
 */

public class TesterHomeApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // initialize fresco with OK HTTP
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, new OkHttpClient().newBuilder().addInterceptor(new StethoInterceptor()).build())
                .build();
        Fresco.initialize(this, config);

        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        LeakCanary.install(this);
        Stetho.initializeWithDefaults(this);

        mTracker = getDefaultTracker();
        mTracker.enableExceptionReporting(true);
        mTracker.enableAutoActivityTracking(true);
    }

    private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker("UA-79133086-1");
        }
        return mTracker;
    }
}
