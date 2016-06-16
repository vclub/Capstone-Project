package com.testerhome.android.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebView;

import com.testerhome.android.app.R;
import com.testerhome.android.app.networks.TesterHomeApi;

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

    public static WebViewFragment newInstance() {

        Bundle args = new Bundle();

        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMarkedView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_webview;
    }

    public void loadTopicDetail(String topidId) {
        mSubscription = TesterHomeApi.getInstance().getService().getTopicById(topidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    Log.e(TAG, "loadTopicDetail: " + response.getTopic().getBody_html() );
                    // mMarkedView.setMDText(response.getTopic().getBody_html());

                    String prompt = "";
                    prompt = prompt.concat(response.getTopic().getBody_html().replace("<img src=\"/photo/",
                            "<img src=\"https://testerhome.com/photo/")).concat("</body></html>");
                    mMarkedView.loadDataWithBaseURL("https://testhome.com/photo/", prompt, "text/html", "utf-8", null);
                }, error->{
                    Log.e(TAG, "loadTopicDetail: ", error);
                });
    }
}
