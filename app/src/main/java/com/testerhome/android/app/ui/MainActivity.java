package com.testerhome.android.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.testerhome.android.app.Config;
import com.testerhome.android.app.R;
import com.testerhome.android.app.auth.AppAccountService;
import com.testerhome.android.app.models.TesterUser;
import com.testerhome.android.app.networks.TesterHomeApi;
import com.testerhome.android.app.ui.adapters.TopicsListAdapter;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {


    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;

    private SimpleDraweeView mAccountAvatar;
    private TextView mAccountName;
    private Button mLogout;

    private TopicsListAdapter mTopicsListAdapter;

    private static final String TAG = "MainActivity";

    private TesterUser mTesterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();
        loadMainInfo();

        getUserInfo();
    }

    private void setupView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        View headerLayout = mNavigationView.inflateHeaderView(R.layout.nav_header_main);
        mAccountAvatar = (SimpleDraweeView) headerLayout.findViewById(R.id.sdv_account_avatar);
        mAccountAvatar.setOnClickListener(v -> {
            onAvatarClick();
        });
        mAccountName = (TextView)headerLayout.findViewById(R.id.tv_account_username);
        mLogout= (Button)headerLayout.findViewById(R.id.btn_logout);
        mLogout.setOnClickListener(v->{
            onLogoutClick();
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTopicsListAdapter = new TopicsListAdapter(this);
        mRecyclerView.setAdapter(mTopicsListAdapter);
    }

    private void onLogoutClick() {
        AppAccountService.getInstance(this).logout();
        mTesterUser = null;
        updateUserInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserInfo();
    }

    private void updateUserInfo() {
        mTesterUser = null;
        if (!TextUtils.isEmpty(getUserInfo().getLogin())) {
            mAccountAvatar.setImageURI(Uri.parse(Config.getImageUrl(mTesterUser.getAvatar_url())));
            mAccountName.setText(mTesterUser.getName());
            mLogout.setVisibility(View.VISIBLE);
        } else {
            mAccountAvatar.setImageResource(R.mipmap.ic_launcher);
            mAccountName.setText("Please login by click on the avatar");
            mLogout.setVisibility(View.GONE);
        }
    }

    private TesterUser getUserInfo() {
        if (mTesterUser == null) {
            mTesterUser = AppAccountService.getInstance(getApplicationContext()).getActiveAccountInfo();
        }
        return mTesterUser;
    }

    private void onAvatarClick() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.fab)
    void onFabClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void loadMainInfo() {
        mSubscription = TesterHomeApi.getInstance().getService()
                .getTopicsByType(Config.TOPICS_TYPE_RECENT, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mTopicsListAdapter.setItems(response.getTopics());
                    Log.e(TAG, "loadMainInfo: " + response.getTopics().size());
                }, error -> {
                    Log.e(TAG, "loadMainInfo: ", error);
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private SearchView mSearchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if (mSearchView != null) {
            mSearchView.setOnQueryTextListener(this);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!TextUtils.isEmpty(query)) {
            startActivity(new Intent(this, SearchActivity.class).putExtra("keyword", query));
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
