package com.testerhome.android.app.auth;

/**
 * Created by Bin Li on 2016/6/19.
 */

public class AuthenticationService {

    public static final String BASEURL = "https://testerhome.com/";
    public static final String AUTHORIZATION_URL = "https://testerhome.com/oauth/authorize";
    public static final String ACCESS_TOKEN_URL = "https://testerhome.com/oauth/token";

    private static final String API_KEY = "85a67646";
    private static final String STATE = "E3ZYKC1T6H2yP4z";

    public static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

    private static final String RESPONSE_TYPE_PARAM = "response_type";
    private static final String RESPONSE_TYPE_VALUE = "code";
    private static final String CLIENT_ID_PARAM = "client_id";
    private static final String STATE_PARAM = "state";
    private static final String REDIRECT_URI_PARAM = "redirect_uri";

    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUALS = "=";
    /**
     * Method that generates the url for get the authorization token from the Service
     *
     * @return Url
     */
    public static String getAuthorizationUrl() {
        return AUTHORIZATION_URL
                + QUESTION_MARK + RESPONSE_TYPE_PARAM + EQUALS + RESPONSE_TYPE_VALUE
                + AMPERSAND + CLIENT_ID_PARAM + EQUALS + API_KEY
                + AMPERSAND + STATE_PARAM + EQUALS + STATE
                + AMPERSAND + REDIRECT_URI_PARAM + EQUALS + REDIRECT_URI;
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
