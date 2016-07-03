package com.testerhome.android.app.provider.favorite;

import android.net.Uri;
import android.provider.BaseColumns;

import com.testerhome.android.app.provider.TesterHomeProvider;

/**
 * My favorite.
 */
public class FavoriteColumns implements BaseColumns {
    public static final String TABLE_NAME = "favorite";
    public static final Uri CONTENT_URI = Uri.parse(TesterHomeProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String _ID = "topic_id";

    public static final String TOPIC_ID = "topic_id";

    public static final String TITLE = "title";

    public static final String CREATE_AT = "create_at";

    public static final String NODE_NAME = "node_name";

    public static final String BODY_HTML = "body_html";

    public static final String NAME = "name";

    public static final String AVATAR_URL = "avatar_url";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            TITLE,
            CREATE_AT,
            NODE_NAME,
            BODY_HTML,
            NAME,
            AVATAR_URL
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(TOPIC_ID) || c.contains("." + TOPIC_ID)) return true;
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(CREATE_AT) || c.contains("." + CREATE_AT)) return true;
            if (c.equals(NODE_NAME) || c.contains("." + NODE_NAME)) return true;
            if (c.equals(BODY_HTML) || c.contains("." + BODY_HTML)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(AVATAR_URL) || c.contains("." + AVATAR_URL)) return true;
        }
        return false;
    }

}
