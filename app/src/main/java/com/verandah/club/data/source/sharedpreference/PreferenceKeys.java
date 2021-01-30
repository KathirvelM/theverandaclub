package com.verandah.club.data.source.sharedpreference;

import android.content.Context;

public interface PreferenceKeys {
    String PREF_NAME = "PEOPLE_PREFERENCES";
    int MODE = Context.MODE_PRIVATE;

    String IS_LOGGED_IN = "IsLoggedIn";
    String USER_ID = "userId";
    String OAUTH_KEY = "OAuthKey";
    String IS_SHOW_INTRO = "isShowIntro";
    String FCM_TOKEN = "fcmToken";

    String FIRST_NAME = "first_name";
    String LAST_NAME = "last_name";
    String EMAIL = "email";

    String CURRENCY_CODE = "currencyCode";

    String LANGUAGE = "language";
    String USER_TYPE = "user_type";
    String USER_TYPE_CURRENT = "user_type_current";

    String HALAL_OPTION = "halal_option";

    String AVATAR = "avatar";
}
