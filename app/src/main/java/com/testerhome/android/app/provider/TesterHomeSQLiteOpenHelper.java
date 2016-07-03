package com.testerhome.android.app.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.testerhome.android.app.BuildConfig;
import com.testerhome.android.app.provider.favorite.FavoriteColumns;

public class TesterHomeSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = TesterHomeSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "testerhome.db";
    private static final int DATABASE_VERSION = 1;
    private static TesterHomeSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final TesterHomeSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE IF NOT EXISTS "
            + FavoriteColumns.TABLE_NAME + " ( "
            + FavoriteColumns._ID + " INTEGER PRIMARY KEY, "
            + FavoriteColumns.TITLE + " TEXT, "
            + FavoriteColumns.CREATE_AT + " TEXT, "
            + FavoriteColumns.NODE_NAME + " TEXT, "
            + FavoriteColumns.BODY_HTML + " TEXT, "
            + FavoriteColumns.NAME + " TEXT, "
            + FavoriteColumns.AVATAR_URL + " TEXT "
            + " );";

    public static final String SQL_CREATE_INDEX_FAVORITE_TOPIC_ID = "CREATE INDEX IDX_FAVORITE_TOPIC_ID "
            + " ON " + FavoriteColumns.TABLE_NAME + " ( " + FavoriteColumns.TOPIC_ID + " );";

    // @formatter:on

    public static TesterHomeSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static TesterHomeSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static TesterHomeSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new TesterHomeSQLiteOpenHelper(context);
    }

    private TesterHomeSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new TesterHomeSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static TesterHomeSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new TesterHomeSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private TesterHomeSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new TesterHomeSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
        db.execSQL(SQL_CREATE_INDEX_FAVORITE_TOPIC_ID);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
