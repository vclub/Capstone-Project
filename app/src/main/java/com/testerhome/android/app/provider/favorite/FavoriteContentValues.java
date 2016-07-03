package com.testerhome.android.app.provider.favorite;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.testerhome.android.app.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code favorite} table.
 */
public class FavoriteContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return FavoriteColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable FavoriteSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable FavoriteSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public FavoriteContentValues putTopicId(long value) {
        mContentValues.put(FavoriteColumns.TOPIC_ID, value);
        return this;
    }


    public FavoriteContentValues putTitle(@Nullable String value) {
        mContentValues.put(FavoriteColumns.TITLE, value);
        return this;
    }

    public FavoriteContentValues putTitleNull() {
        mContentValues.putNull(FavoriteColumns.TITLE);
        return this;
    }

    public FavoriteContentValues putCreateAt(@Nullable String value) {
        mContentValues.put(FavoriteColumns.CREATE_AT, value);
        return this;
    }

    public FavoriteContentValues putCreateAtNull() {
        mContentValues.putNull(FavoriteColumns.CREATE_AT);
        return this;
    }

    public FavoriteContentValues putNodeName(@Nullable String value) {
        mContentValues.put(FavoriteColumns.NODE_NAME, value);
        return this;
    }

    public FavoriteContentValues putNodeNameNull() {
        mContentValues.putNull(FavoriteColumns.NODE_NAME);
        return this;
    }

    public FavoriteContentValues putBodyHtml(@Nullable String value) {
        mContentValues.put(FavoriteColumns.BODY_HTML, value);
        return this;
    }

    public FavoriteContentValues putBodyHtmlNull() {
        mContentValues.putNull(FavoriteColumns.BODY_HTML);
        return this;
    }

    public FavoriteContentValues putName(@Nullable String value) {
        mContentValues.put(FavoriteColumns.NAME, value);
        return this;
    }

    public FavoriteContentValues putNameNull() {
        mContentValues.putNull(FavoriteColumns.NAME);
        return this;
    }

    public FavoriteContentValues putAvatarUrl(@Nullable String value) {
        mContentValues.put(FavoriteColumns.AVATAR_URL, value);
        return this;
    }

    public FavoriteContentValues putAvatarUrlNull() {
        mContentValues.putNull(FavoriteColumns.AVATAR_URL);
        return this;
    }
}
