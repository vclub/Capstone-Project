package com.testerhome.android.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * Created by Bin Li on 2016/6/14.
 */

public abstract class BaseFragment extends Fragment {

    protected View mContentView;
    protected Subscription mSubscription;
    protected Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContentView == null){
            initView(savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup)mContentView.getParent();
            if (parent != null){
                parent.removeAllViews();
            }
        }
        return mContentView;
    }

    protected void initView(Bundle savedInstanceState){
        mContentView = LayoutInflater.from(getContext()).inflate(getLayoutRes(), null);
        mUnbinder = ButterKnife.bind(this, mContentView);
    }

    protected abstract int getLayoutRes();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        unSubscribe();
    }
    protected void unSubscribe(){
        if (mSubscription != null && ! mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
    }
}
