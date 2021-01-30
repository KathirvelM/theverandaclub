package com.verandah.club.data.source.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.verandah.club.data.source.AppDataSource;

public class AppPreferenceDataSource implements AppDataSource, PreferenceKeys {

    public void delete() {
        String language = getLanguage();
        int halalOption = getHalalOption();
        boolean isShowIntro = isShowIntro();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();

        setLanguage(language);
        setHalalOption(halalOption);
        saveIsShowIntro(isShowIntro);
    }

    public void save(String key, boolean bool) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, bool);
        editor.apply();
        editor.commit();
    }

    public void save(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    }

    public void save(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
        editor.commit();
    }

    public void save(String key, Object object) {
        save(key, new Gson().toJson(object));
    }

    public Object get(String key, Class object) {
        return new Gson().fromJson(sharedPreferences.getString(key, null), object);
    }

    private Object get(String key, TypeToken typeToken) {
        return new Gson().fromJson(sharedPreferences.getString(key, null), typeToken.getType());
    }

    private SharedPreferences sharedPreferences;

    public AppPreferenceDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE);
    }

    @Override
    public void saveIsLoggedIn(boolean isLoggedIn) {
        sharedPreferences.edit().putBoolean(IS_LOGGED_IN, isLoggedIn).apply();

    }

    @Override
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, true);
    }

    @Override
    public void setUserId(String userId) {
        sharedPreferences.edit().putString(USER_ID, userId).apply();
    }

    @Override
    public String getUserId() {
        return sharedPreferences.getString(USER_ID, "0");
    }

    @Override
    public void setOAuthKey(String oAuthKey) {
        sharedPreferences.edit().putString(OAUTH_KEY, oAuthKey).apply();
    }

    @Override
    public String getOAuthKey() {
        return sharedPreferences.getString(OAUTH_KEY, "");
    }

    @Override
    public void saveIsShowIntro(boolean status) {
        sharedPreferences.edit().putBoolean(IS_SHOW_INTRO, status).apply();
    }

    @Override
    public void setHalalOption(int status) {
        sharedPreferences.edit().putInt(HALAL_OPTION, status).apply();
    }

    @Override
    public boolean isShowIntro() {
        return sharedPreferences.getBoolean(IS_SHOW_INTRO, false);
    }

    @Override
    public int getHalalOption() {
        return sharedPreferences.getInt(HALAL_OPTION, 0);
    }

    @Override
    public void setFCMToken(String fcmToken) {
        sharedPreferences.edit().putString(FCM_TOKEN, fcmToken).apply();
    }

    @Override
    public String getFCMToken() {
        return sharedPreferences.getString(FCM_TOKEN, "");
    }


    @Override
    public String getFirstName() {
        return sharedPreferences.getString(FIRST_NAME, "");
    }

    @Override
    public String getLastName() {
        return sharedPreferences.getString(LAST_NAME, "");
    }

    @Override
    public String getEmail() {
        return sharedPreferences.getString(EMAIL, "");
    }

    @Override
    public void setFirstName(String firstName) {
        save(FIRST_NAME, firstName);
    }


    @Override
    public void setLastName(String lastName) {
        save(LAST_NAME, lastName);
    }

    @Override
    public void setEmail(String email) {
        save(EMAIL, email);
    }

    @Override
    public void setLanguage(String language) {
        save(LANGUAGE, language);
    }

    @Override
    public void setAvatar(String avatar) {
        save(AVATAR, avatar);
    }

    @Override
    public String getAvatar() {
        return sharedPreferences.getString(AVATAR, "");
    }


    @Override
    public String getLanguage() {
        return sharedPreferences.getString(LANGUAGE, "en");
    }

    @Override
    public void setCurrencyCode(String currencyCode) {
        sharedPreferences.edit().putString(CURRENCY_CODE, currencyCode).apply();
    }

    @Override
    public String getCurrencyCode() {
        return sharedPreferences.getString(CURRENCY_CODE, "USD");
    }
}
