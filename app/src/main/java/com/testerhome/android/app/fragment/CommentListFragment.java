package com.testerhome.android.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.testerhome.android.app.R;
import com.testerhome.android.app.networks.TesterHomeApi;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Bin Li on 2016/6/15.
 */

public class CommentListFragment extends BaseFragment {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.comment_recycler_view)
    RecyclerView mRecyclerView;

    private String mTopicId;
    private int mNextCursor = 0;

    public static CommentListFragment newInstance(String topicId) {

        Bundle args = new Bundle();
        args.putString("topic_id", topicId);
        CommentListFragment fragment = new CommentListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_comment_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTopicId = getArguments().getString("id");

        loadComments();
    }

    private void loadComments() {
        TesterHomeApi.getInstance().getService()
                .getTopicsReplies(mTopicId, mNextCursor * 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{

                },error->{

                });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // refresh info
        });
    }

}
