package com.testerhome.android.app.provider.favorite;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.testerhome.android.app.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code favorite} table.
 */
public class FavoriteCursor extends AbstractCursor implements FavoriteModel {
    public FavoriteCursor(Cursor cursor) {
        super(cursor);
    }

    @Override
    public long getId() {
        return getLongOrNull(FavoriteColumns._ID);
    }

    /**
     * Get the {@code topic_id} value.
     */
    public long getTopicId() {
        Long res = getLongOrNull(FavoriteColumns.TOPIC_ID);
        if (res == null)
            throw new NullPointerException("The value of 'topic_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(FavoriteColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code create_at} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCreateAt() {
        String res = getStringOrNull(FavoriteColumns.CREATE_AT);
        return res;
    }

    /**
     * Get the {@code node_name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getNodeName() {
        String res = getStringOrNull(FavoriteColumns.NODE_NAME);
        return res;
    }

    /**
     * Get the {@code body_html} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getBodyHtml() {
        String res = getStringOrNull(FavoriteColumns.BODY_HTML);
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getName() {
        String res = getStringOrNull(FavoriteColumns.NAME);
        return res;
    }

    /**
     * Get the {@code avatar_url} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getAvatarUrl() {
        String res = getStringOrNull(FavoriteColumns.AVATAR_URL);
        return res;
    }
}
