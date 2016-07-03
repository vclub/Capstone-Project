package com.testerhome.android.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.testerhome.android.app.R;
import com.testerhome.android.app.provider.favorite.FavoriteColumns;
import com.testerhome.android.app.provider.favorite.FavoriteContentValues;
import com.testerhome.android.app.provider.favorite.FavoriteCursor;
import com.testerhome.android.app.provider.favorite.FavoriteSelection;

/**
 * Created by Bin Li on 2016/6/19.
 */

public class AboutActivity extends BackBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
    }

    private static final String TAG = "AboutActivity";
    private void initView() {
        FavoriteContentValues values = new FavoriteContentValues();
        values.putTopicId(111)
                .putTitle("dfdsafdsaf")
                .putName("username");
        getContentResolver().insert(FavoriteColumns.CONTENT_URI, values.values());

        FavoriteSelection favoriteSelection = new FavoriteSelection();
        String[] projection = {FavoriteColumns.TITLE};
        FavoriteCursor c = favoriteSelection.query(getContentResolver(), projection);

        while (c.moveToNext()){
            Log.e(TAG, "initView: " + c.getTitle());
        }
        c.close();
    }

}
