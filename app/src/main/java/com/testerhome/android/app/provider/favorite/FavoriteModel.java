package com.testerhome.android.app.provider.favorite;

import android.support.annotation.Nullable;

import com.testerhome.android.app.provider.base.BaseModel;

/**
 * My favorite.
 */
public interface FavoriteModel extends BaseModel {

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    String getTitle();

    /**
     * Get the {@code create_at} value.
     * Can be {@code null}.
     */
    @Nullable
    String getCreateAt();

    /**
     * Get the {@code node_name} value.
     * Can be {@code null}.
     */
    @Nullable
    String getNodeName();

    /**
     * Get the {@code body_html} value.
     * Can be {@code null}.
     */
    @Nullable
    String getBodyHtml();

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    String getName();

    /**
     * Get the {@code avatar_url} value.
     * Can be {@code null}.
     */
    @Nullable
    String getAvatarUrl();
}
