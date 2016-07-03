package com.testerhome.android.app.provider.favorite;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.testerhome.android.app.provider.base.AbstractSelection;

/**
 * Selection for the {@code favorite} table.
 */
public class FavoriteSelection extends AbstractSelection<FavoriteSelection> {
    @Override
    protected Uri baseUri() {
        return FavoriteColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavoriteCursor} object, which is positioned before the first entry, or null.
     */
    public FavoriteCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavoriteCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public FavoriteCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavoriteCursor} object, which is positioned before the first entry, or null.
     */
    public FavoriteCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavoriteCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public FavoriteCursor query(Context context) {
        return query(context, null);
    }


    public FavoriteSelection id(long... value) {
        addEquals("favorite." + FavoriteColumns._ID, toObjectArray(value));
        return this;
    }

    public FavoriteSelection idNot(long... value) {
        addNotEquals("favorite." + FavoriteColumns._ID, toObjectArray(value));
        return this;
    }

    public FavoriteSelection orderById(boolean desc) {
        orderBy("favorite." + FavoriteColumns._ID, desc);
        return this;
    }

    public FavoriteSelection orderById() {
        return orderById(false);
    }

    public FavoriteSelection topicId(long... value) {
        addEquals(FavoriteColumns.TOPIC_ID, toObjectArray(value));
        return this;
    }

    public FavoriteSelection topicIdNot(long... value) {
        addNotEquals(FavoriteColumns.TOPIC_ID, toObjectArray(value));
        return this;
    }

    public FavoriteSelection topicIdGt(long value) {
        addGreaterThan(FavoriteColumns.TOPIC_ID, value);
        return this;
    }

    public FavoriteSelection topicIdGtEq(long value) {
        addGreaterThanOrEquals(FavoriteColumns.TOPIC_ID, value);
        return this;
    }

    public FavoriteSelection topicIdLt(long value) {
        addLessThan(FavoriteColumns.TOPIC_ID, value);
        return this;
    }

    public FavoriteSelection topicIdLtEq(long value) {
        addLessThanOrEquals(FavoriteColumns.TOPIC_ID, value);
        return this;
    }

    public FavoriteSelection orderByTopicId(boolean desc) {
        orderBy(FavoriteColumns.TOPIC_ID, desc);
        return this;
    }

    public FavoriteSelection orderByTopicId() {
        orderBy(FavoriteColumns.TOPIC_ID, false);
        return this;
    }

    public FavoriteSelection title(String... value) {
        addEquals(FavoriteColumns.TITLE, value);
        return this;
    }

    public FavoriteSelection titleNot(String... value) {
        addNotEquals(FavoriteColumns.TITLE, value);
        return this;
    }

    public FavoriteSelection titleLike(String... value) {
        addLike(FavoriteColumns.TITLE, value);
        return this;
    }

    public FavoriteSelection titleContains(String... value) {
        addContains(FavoriteColumns.TITLE, value);
        return this;
    }

    public FavoriteSelection titleStartsWith(String... value) {
        addStartsWith(FavoriteColumns.TITLE, value);
        return this;
    }

    public FavoriteSelection titleEndsWith(String... value) {
        addEndsWith(FavoriteColumns.TITLE, value);
        return this;
    }

    public FavoriteSelection orderByTitle(boolean desc) {
        orderBy(FavoriteColumns.TITLE, desc);
        return this;
    }

    public FavoriteSelection orderByTitle() {
        orderBy(FavoriteColumns.TITLE, false);
        return this;
    }

    public FavoriteSelection createAt(String... value) {
        addEquals(FavoriteColumns.CREATE_AT, value);
        return this;
    }

    public FavoriteSelection createAtNot(String... value) {
        addNotEquals(FavoriteColumns.CREATE_AT, value);
        return this;
    }

    public FavoriteSelection createAtLike(String... value) {
        addLike(FavoriteColumns.CREATE_AT, value);
        return this;
    }

    public FavoriteSelection createAtContains(String... value) {
        addContains(FavoriteColumns.CREATE_AT, value);
        return this;
    }

    public FavoriteSelection createAtStartsWith(String... value) {
        addStartsWith(FavoriteColumns.CREATE_AT, value);
        return this;
    }

    public FavoriteSelection createAtEndsWith(String... value) {
        addEndsWith(FavoriteColumns.CREATE_AT, value);
        return this;
    }

    public FavoriteSelection orderByCreateAt(boolean desc) {
        orderBy(FavoriteColumns.CREATE_AT, desc);
        return this;
    }

    public FavoriteSelection orderByCreateAt() {
        orderBy(FavoriteColumns.CREATE_AT, false);
        return this;
    }

    public FavoriteSelection nodeName(String... value) {
        addEquals(FavoriteColumns.NODE_NAME, value);
        return this;
    }

    public FavoriteSelection nodeNameNot(String... value) {
        addNotEquals(FavoriteColumns.NODE_NAME, value);
        return this;
    }

    public FavoriteSelection nodeNameLike(String... value) {
        addLike(FavoriteColumns.NODE_NAME, value);
        return this;
    }

    public FavoriteSelection nodeNameContains(String... value) {
        addContains(FavoriteColumns.NODE_NAME, value);
        return this;
    }

    public FavoriteSelection nodeNameStartsWith(String... value) {
        addStartsWith(FavoriteColumns.NODE_NAME, value);
        return this;
    }

    public FavoriteSelection nodeNameEndsWith(String... value) {
        addEndsWith(FavoriteColumns.NODE_NAME, value);
        return this;
    }

    public FavoriteSelection orderByNodeName(boolean desc) {
        orderBy(FavoriteColumns.NODE_NAME, desc);
        return this;
    }

    public FavoriteSelection orderByNodeName() {
        orderBy(FavoriteColumns.NODE_NAME, false);
        return this;
    }

    public FavoriteSelection bodyHtml(String... value) {
        addEquals(FavoriteColumns.BODY_HTML, value);
        return this;
    }

    public FavoriteSelection bodyHtmlNot(String... value) {
        addNotEquals(FavoriteColumns.BODY_HTML, value);
        return this;
    }

    public FavoriteSelection bodyHtmlLike(String... value) {
        addLike(FavoriteColumns.BODY_HTML, value);
        return this;
    }

    public FavoriteSelection bodyHtmlContains(String... value) {
        addContains(FavoriteColumns.BODY_HTML, value);
        return this;
    }

    public FavoriteSelection bodyHtmlStartsWith(String... value) {
        addStartsWith(FavoriteColumns.BODY_HTML, value);
        return this;
    }

    public FavoriteSelection bodyHtmlEndsWith(String... value) {
        addEndsWith(FavoriteColumns.BODY_HTML, value);
        return this;
    }

    public FavoriteSelection orderByBodyHtml(boolean desc) {
        orderBy(FavoriteColumns.BODY_HTML, desc);
        return this;
    }

    public FavoriteSelection orderByBodyHtml() {
        orderBy(FavoriteColumns.BODY_HTML, false);
        return this;
    }

    public FavoriteSelection name(String... value) {
        addEquals(FavoriteColumns.NAME, value);
        return this;
    }

    public FavoriteSelection nameNot(String... value) {
        addNotEquals(FavoriteColumns.NAME, value);
        return this;
    }

    public FavoriteSelection nameLike(String... value) {
        addLike(FavoriteColumns.NAME, value);
        return this;
    }

    public FavoriteSelection nameContains(String... value) {
        addContains(FavoriteColumns.NAME, value);
        return this;
    }

    public FavoriteSelection nameStartsWith(String... value) {
        addStartsWith(FavoriteColumns.NAME, value);
        return this;
    }

    public FavoriteSelection nameEndsWith(String... value) {
        addEndsWith(FavoriteColumns.NAME, value);
        return this;
    }

    public FavoriteSelection orderByName(boolean desc) {
        orderBy(FavoriteColumns.NAME, desc);
        return this;
    }

    public FavoriteSelection orderByName() {
        orderBy(FavoriteColumns.NAME, false);
        return this;
    }

    public FavoriteSelection avatarUrl(String... value) {
        addEquals(FavoriteColumns.AVATAR_URL, value);
        return this;
    }

    public FavoriteSelection avatarUrlNot(String... value) {
        addNotEquals(FavoriteColumns.AVATAR_URL, value);
        return this;
    }

    public FavoriteSelection avatarUrlLike(String... value) {
        addLike(FavoriteColumns.AVATAR_URL, value);
        return this;
    }

    public FavoriteSelection avatarUrlContains(String... value) {
        addContains(FavoriteColumns.AVATAR_URL, value);
        return this;
    }

    public FavoriteSelection avatarUrlStartsWith(String... value) {
        addStartsWith(FavoriteColumns.AVATAR_URL, value);
        return this;
    }

    public FavoriteSelection avatarUrlEndsWith(String... value) {
        addEndsWith(FavoriteColumns.AVATAR_URL, value);
        return this;
    }

    public FavoriteSelection orderByAvatarUrl(boolean desc) {
        orderBy(FavoriteColumns.AVATAR_URL, desc);
        return this;
    }

    public FavoriteSelection orderByAvatarUrl() {
        orderBy(FavoriteColumns.AVATAR_URL, false);
        return this;
    }
}
