package com.testerhome.android.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.testerhome.android.app.R;
import com.testerhome.android.app.fragment.CommentListFragment;
import com.testerhome.android.app.fragment.WebViewFragment;
import com.testerhome.android.app.models.TopicEntity;

import butterknife.BindView;

/**
 * Created by Bin Li on 2016/6/15.
 */

public class TopicDetailActivity extends BackBaseActivity {

    private static final String TAG = "TopicDetailActivity";

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private TopicEntity mTopicEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        if (getIntent().hasExtra("topicInfo")){
            mTopicEntity = getIntent().getParcelableExtra("topicInfo");
        } else {
            finish();
        }

        initView();


    }

    private void initView() {
        mViewPager.setAdapter(new TabPageAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mWebViewFragment.loadTopicDetail(mTopicEntity.getId());

                mCommentListFragment.setInfo(mTopicEntity.getTitle());
            }
        });
    }

    private WebViewFragment mWebViewFragment;
    private CommentListFragment mCommentListFragment;

    public class TabPageAdapter extends FragmentPagerAdapter {

        private String[] titles = {"topic", "commend"};

        public TabPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (mWebViewFragment == null){
                        mWebViewFragment = new WebViewFragment();
                    }
                    return mWebViewFragment;
                default:
                    if (mCommentListFragment == null){
                        mCommentListFragment = new CommentListFragment();
                    }
                    return mCommentListFragment;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
