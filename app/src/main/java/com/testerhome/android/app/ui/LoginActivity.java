package com.testerhome.android.app.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.testerhome.android.app.R;
import com.testerhome.android.app.auth.AuthenticationService;
import com.testerhome.android.app.models.OAuth;
import com.testerhome.android.app.networks.TesterHomeApi;

import butterknife.BindView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Bin Li on 2016/6/19.
 */

public class LoginActivity extends BackBaseActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.login_view)
    WebView mLoginView;

    private ProgressDialog pd;
    private String auth_code = "";

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
                if (url.startsWith(AuthenticationService.AUTHORIZATION_URL)) {
                    try {
                        auth_code = url.substring(url.lastIndexOf("/") + 1);
                        //Generate URL for requesting Access Token
                        // String accessTokenUrl = AuthenticationService.getAccessTokenUrl(auth_code = code);
                        //We make the request in a AsyncTask
                        view.setVisibility(View.INVISIBLE);
                        new PostRequestAsyncTask().execute(AuthenticationService.ACCESS_TOKEN_URL);
                    } catch (Exception e) {
                        Log.e(TAG, "shouldOverrideUrlLoading: " + Log.getStackTraceString(e));
                    }
                } else if (url.equals(AuthenticationService.BASEURL)) {
                    url = AuthenticationService.getAuthorizationUrl();
                }
                view.loadUrl(url);
                return true;
            }
        });

        mLoginView.loadUrl(AuthenticationService.getAuthorizationUrl());
    }

    private class PostRequestAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            pd = DialogUtils.createProgressDialog(LoginActivity.this);
            pd.setMessage("正在登录");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            if (urls.length > 0) {

                String url = urls[0];
                OkHttpClient okHttpClient = new OkHttpClient();


                FormBody formBody =  new FormBody.Builder()
                        .add("client_id", AuthenticationService.getApiKey())
                        .add("code", auth_code)
                        .add("grant_type", "authorization_code")
                        .add("redirect_uri", AuthenticationService.REDIRECT_URI)
                        .add("client_secret", "3a20127eb087257ad7196098bfd8240746a66b0550d039eb2c1901c025e7cbea")
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();

                Response response = null;
                try {
                    response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        //Convert the string result to a JSON Object
                        String responseStr = response.body().string();

                        Gson gson = new Gson();
                        OAuth oAuth = gson.fromJson(responseStr,OAuth.class);
                        if (oAuth.getExpires_in() > 0 && oAuth.getAccess_token() != null) {
                            getUserInfo(oAuth);
                            return true;
                        }
                    } else {
                        Log.e("Tokenm", "error:" + response.message());
                    }
                } catch (Exception e) {
                    Log.e("Tokenm", "error:" + response.message());
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean status) {
            if (status) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }else {
                mLoginView.setVisibility(View.VISIBLE);
                mLoginView.loadUrl(AuthenticationService.getAuthorizationUrl());
            }
        }

    }

    private void getUserInfo(final OAuth oAuth) {
        mSubscription = TesterHomeApi.getInstance()
                .getService()
                .getCurrentUserInfo(oAuth.getAccess_token())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userDetailResponse -> {
                    if (userDetailResponse != null) {
//                        AppAccountService.getInstance(this)
//                                .signIn(userDetailResponse.getUser().getLogin(), userDetailResponse.getUser(), oAuth);
                        this.finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (mLoginView != null){
            mLoginView.stopLoading();
            mLoginView.removeAllViews();
            mLoginView.destroy();
        }
        super.onDestroy();
    }
}
