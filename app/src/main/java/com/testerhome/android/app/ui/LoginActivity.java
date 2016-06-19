package com.testerhome.android.app.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.testerhome.android.app.R;
import com.testerhome.android.app.auth.AuthenticationService;

import butterknife.BindView;

/**
 * Created by Bin Li on 2016/6/19.
 */

public class LoginActivity extends BackBaseActivity {

    @BindView(R.id.login_view)
    WebView mLoginView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");

        setupView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupView(){
        mLoginView.getSettings().setJavaScriptEnabled(true);

        mLoginView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mLoginView.loadUrl(AuthenticationService.getAuthorizationUrl());
    }
}
