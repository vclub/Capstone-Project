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

    protected Subscription mSubscription;
    protected Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        mUnbinder = ButterKnife.bind(this, view);

        initView();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void initView(){

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
