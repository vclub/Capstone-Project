package com.testerhome.android.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.testerhome.android.app.R;

/**
 * Created by Bin Li on 2016/6/19.
 */

public class AboutActivity extends BackBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
    }

    private void initView() {

    }

}
