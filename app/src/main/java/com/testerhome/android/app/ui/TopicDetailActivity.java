package com.testerhome.android.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.testerhome.android.app.R;
import com.testerhome.android.app.fragment.CommentListFragment;
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

        if (getIntent().hasExtra("topicInfo")) {
            mTopicEntity = getIntent().getParcelableExtra("topicInfo");
        } else {
            finish();
        }

        initView();
    }

    private TabPageAdapter mAdapter;
    private void initView() {
        mAdapter = new TabPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private ShareActionProvider mShareActionProvider;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        final Intent target = new Intent(Intent.ACTION_SEND);
        target.setType("text/plain");
        target.putExtra(Intent.EXTRA_TITLE, "dddddd");
        target.putExtra(Intent.EXTRA_TEXT, String.format("https://testerhome.com/topics/%s", mTopicEntity.getId()));
        mShareActionProvider.setShareIntent(target);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            doShare();
        }
        return super.onOptionsItemSelected(item);
    }

    // Somewhere in the application.
    public void doShare() {
        // When you want to share set the share intent.

        final Intent target = new Intent(Intent.ACTION_SEND);
        target.setType("text/plain");
        target.putExtra(Intent.EXTRA_TITLE, "dddddd");
        target.putExtra(Intent.EXTRA_TEXT, String.format("https://testerhome.com/topics/%s", mTopicEntity.getId()));
        mShareActionProvider.setShareIntent(target);
    }

    public class TabPageAdapter extends FragmentPagerAdapter {

        private String[] titles = {"topic", "commend"};

        public TabPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            switch (position) {
//                case 0:
//                    return WebViewFragment.newInstance();
//                default:
//                    return CommentListFragment.newInstance();
//            }
            return CommentListFragment.newInstance();
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
