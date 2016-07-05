package com.testerhome.android.app.ui;

import android.app.LoaderManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.testerhome.android.app.R;
import com.testerhome.android.app.provider.favorite.FavoriteColumns;
import com.testerhome.android.app.ui.adapters.FavoriteAdapter;

import butterknife.BindView;

/**
 * My Favorite
 * Created by Bin Li on 2016/7/5.
 */

public class MyFavoriteActivity extends BackBaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.favoriteView)
    ListView mFavoriteView;

    private FavoriteAdapter mFavoriteCursorAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);


        setFavoriteViewAdapter();

        getLoaderManager().initLoader(0, null, this);
    }

    private void setFavoriteViewAdapter(){
        mFavoriteCursorAdapter = new FavoriteAdapter(this, null);
        mFavoriteView.setAdapter(mFavoriteCursorAdapter);
    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new android.content.CursorLoader(
                this,
                FavoriteColumns.CONTENT_URI,
                FavoriteColumns.ALL_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        mFavoriteCursorAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        mFavoriteCursorAdapter.changeCursor(null);
    }
}
