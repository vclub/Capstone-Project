package com.testerhome.android.app;

import com.facebook.stetho.Stetho;

/**
 * Created by Bin Li on 2016/7/8.
 */

public class TesterHomeDebugApp extends TesterHomeApp {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
