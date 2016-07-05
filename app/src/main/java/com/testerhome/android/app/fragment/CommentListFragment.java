package com.testerhome.android.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.testerhome.android.app.R;
import com.testerhome.android.app.networks.TesterHomeApi;
import com.testerhome.android.app.ui.adapters.BaseRecyclerAdapter;
import com.testerhome.android.app.ui.adapters.CommentAdapter;

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

    private long mTopicId;
    private int mNextCursor = 0;

    private CommentAdapter mAdapter;

    private static final String TAG = "CommentListFragment";

    public static CommentListFragment newInstance(long topicId) {
        CommentListFragment fragment = new CommentListFragment();
        fragment.mTopicId = topicId;
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_comment_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadComments();
    }

    private void loadComments() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
        mSubscription = TesterHomeApi.getInstance().getService()
                .getTopicsReplies(mTopicId, mNextCursor * 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    if (response != null && response.getTopicReply().size() > 0) {
                        if (mNextCursor == 0) {
                            mAdapter.setItems(response.getTopicReply());
                        } else {
                            mAdapter.addItems(response.getTopicReply());
                        }

                        if (response.getTopicReply().size() == 20) {
                            mNextCursor += 1;
                        } else {
                            mNextCursor = 0;
                        }
                    } else {
                        mNextCursor = 0;
                    }
                }, error -> {
                    Log.e(TAG, "loadComments: " + Log.getStackTraceString(error));
                });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter = new CommentAdapter(getContext()));
        mAdapter.setListener(new BaseRecyclerAdapter.RecyclerAdapterListener() {
            @Override
            public void onEndOfList() {
                if (mNextCursor > 0) {
                    loadComments();
                }
            }

            @Override
            public void onListItemClick(Object item) {

            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // refresh info
            mNextCursor = 0;
            loadComments();
        });
    }

}
