package com.testerhome.android.app.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.testerhome.android.app.R;
import com.testerhome.android.app.fragment.CommentListFragment;
import com.testerhome.android.app.fragment.WebViewFragment;
import com.testerhome.android.app.models.TopicEntity;
import com.testerhome.android.app.provider.favorite.FavoriteColumns;
import com.testerhome.android.app.provider.favorite.FavoriteContentValues;
import com.testerhome.android.app.provider.favorite.FavoriteSelection;
import com.testerhome.android.app.util.ImageUrlConvert;
import com.testerhome.android.app.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

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

    @BindView(R.id.sdv_topic_user_avatar)
    SimpleDraweeView topicUserAvatar;

    @BindView(R.id.tv_topic_title)
    TextView textViewTopicTitle;

    @BindView(R.id.tv_topic_username)
    TextView topicUsername;

    @BindView(R.id.tv_topic_publish_date)
    TextView topicPublishDate;

    @BindView(R.id.tv_topic_name)
    TextView topicName;

    @BindView(R.id.tv_topic_replies_count)
    TextView topicRepliesCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        if (getIntent().hasExtra("topicInfo")) {
            mTopicEntity = getIntent().getParcelableExtra("topicInfo");
            initView();
        } else {
            finish();
        }

    }

    private TabPageAdapter mAdapter;

    private void initView() {
        mAdapter = new TabPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        topicUserAvatar.setImageURI(Uri.parse(ImageUrlConvert.getImageUrl(mTopicEntity.getUser().getAvatar_url())));
        textViewTopicTitle.setText(mTopicEntity.getTitle());
        topicUsername.setText(TextUtils.isEmpty(mTopicEntity.getUser().getName()) ? mTopicEntity.getUser().getLogin() : mTopicEntity.getUser().getName());
        topicPublishDate.setText(StringUtils.formatPublishDateTime(mTopicEntity.getCreated_at()));
        topicName.setText(mTopicEntity.getNode_name());

        initFabIcon();
    }

    private ShareActionProvider mShareActionProvider;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        final Intent target = new Intent(Intent.ACTION_SEND);
        target.setType("text/plain");
        target.putExtra(Intent.EXTRA_TITLE, mTopicEntity.getTitle());
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
    private void doShare() {
        // When you want to share set the share intent.

        final Intent target = new Intent(Intent.ACTION_SEND);
        target.setType("text/plain");
        target.putExtra(Intent.EXTRA_TITLE, mTopicEntity.getTitle());
        target.putExtra(Intent.EXTRA_TEXT, String.format("https://testerhome.com/topics/%s", mTopicEntity.getId()));
        mShareActionProvider.setShareIntent(target);
    }

    public class TabPageAdapter extends FragmentPagerAdapter {

        private String[] titles = {getString(R.string.topic), getString(R.string.commend)};

        public TabPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return WebViewFragment.newInstance(mTopicEntity.getId());
                default:
                    return CommentListFragment.newInstance(mTopicEntity.getId());
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

    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    private void initFabIcon(){
        FavoriteSelection where = new FavoriteSelection();
        where.id(mTopicEntity.getId());

        Cursor c = getContentResolver().query(FavoriteColumns.CONTENT_URI, FavoriteColumns.ALL_COLUMNS, where.sel(), where.args(), null);

        if (c == null || c.getCount() <= 0) {
            mFloatingActionButton.setImageResource(R.drawable.bookmark_icon_off);
        } else {
            mFloatingActionButton.setImageResource(R.drawable.bookmark_icon_on);
        }
        if (c != null) {
            c.close();
        }
    }

    @OnClick(R.id.fab)
    void onFabClick() {

        // check if has in db
        FavoriteSelection where = new FavoriteSelection();
        where.id(mTopicEntity.getId());

        Cursor c = getContentResolver().query(FavoriteColumns.CONTENT_URI, FavoriteColumns.ALL_COLUMNS, where.sel(), where.args(), null);

        if (c == null || c.getCount() <= 0) {
            FavoriteContentValues values = new FavoriteContentValues();
            values.putTopicId(mTopicEntity.getId())
                    .putTitle(mTopicEntity.getTitle())
                    .putCreateAt(mTopicEntity.getCreated_at())
                    .putName(mTopicEntity.getUser().getName())
                    .putAvatarUrl(mTopicEntity.getUser().getAvatar_url())
                    .putNodeName(mTopicEntity.getNode_name());

            getContentResolver().insert(FavoriteColumns.CONTENT_URI, values.values());

            mFloatingActionButton.setImageResource(R.drawable.bookmark_icon_on);
        } else {
            getContentResolver().delete(FavoriteColumns.CONTENT_URI, where.sel(), where.args());

            mFloatingActionButton.setImageResource(R.drawable.bookmark_icon_off);
        }
        if (c != null) {
            c.close();
        }

    }
}
