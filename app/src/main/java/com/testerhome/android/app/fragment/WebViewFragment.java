package com.testerhome.android.app.fragment;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.testerhome.android.app.R;
import com.testerhome.android.app.networks.TesterHomeApi;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Bin Li on 2016/6/15.
 */

public class WebViewFragment extends BaseFragment {

    private static final String TAG = "WebViewFragment";

    @BindView(R.id.md_view)
    WebView mMarkedView;

    @BindView(R.id.loading)
    ProgressBar mProgressBar;

    private String mTopicId;

    public static WebViewFragment newInstance(String topicId) {

        Bundle args = new Bundle();

        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        fragment.mTopicId = topicId;
        return fragment;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mMarkedView.getSettings().setJavaScriptEnabled(true);
        mMarkedView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });

        loadTopicDetail(mTopicId);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_webview;
    }

    public void loadTopicDetail(String topidId) {
        mSubscription = TesterHomeApi.getInstance().getService().getTopicById(topidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    showWebContent(response.getTopic().getBody_html());
                }, error -> {
                    Log.e(TAG, "loadTopicDetail: ", error);
                });
    }

    public void showWebContent(String htmlBody) {
        String prompt = "";
        AssetManager assetManager = getActivity().getResources().getAssets();

        try {
            InputStream inputStream = assetManager.open("html/md_preview.html");

            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            prompt = new String(b);
            prompt = prompt.replace("$container", htmlBody.replace("<img src=\"/photo/",
                    "<img src=\"https://testerhome.com/photo/"));
            inputStream.close();
        } catch (IOException e) {
            Log.e("", "Couldn't open md_preview.html", e);
        }

        mMarkedView.setBackgroundColor(0);
        mMarkedView.loadDataWithBaseURL(null, prompt, "text/html", "utf-8", null);

    }
}
