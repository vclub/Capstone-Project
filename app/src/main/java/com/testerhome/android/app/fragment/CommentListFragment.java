package com.testerhome.android.app.fragment;

import android.widget.TextView;

import com.testerhome.android.app.R;

import butterknife.BindView;

/**
 * Created by Bin Li on 2016/6/15.
 */

public class CommentListFragment extends BaseFragment {

    @BindView(R.id.info_test)
    TextView mTextView;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_comment_list;
    }

    public void setInfo(String info) {
        mTextView.setText(info);
    }
}
