package com.verandah.club.data.source;

public class AppRepository implements AppDataSource {

    private AppDataSource mSharedPrefercenseDataSource;

    public void delete() {
        mSharedPrefercenseDataSource.delete();
    }

    public AppRepository(AppDataSource sharedPreferences) {
        this.mSharedPrefercenseDataSource = sharedPreferences;
    }

    @Override
    public void saveIsLoggedIn(boolean isLoggedIn) {
        mSharedPrefercenseDataSource.saveIsLoggedIn(isLoggedIn);
    }

    @Override
    public boolean isLoggedIn() {
        return mSharedPrefercenseDataSource.isLoggedIn();
    }

    @Override
    public void setUserId(String userId) {
        mSharedPrefercenseDataSource.setUserId(userId);
    }

    @Override
    public String getUserId() {
        return mSharedPrefercenseDataSource.getUserId();
    }

    @Override
    public void setOAuthKey(String oAuthKey) {
        mSharedPrefercenseDataSource.setOAuthKey(oAuthKey);
    }

    @Override
    public String getOAuthKey() {
        return mSharedPrefercenseDataSource.getOAuthKey();
    }

    @Override
    public void saveIsShowIntro(boolean status) {
        mSharedPrefercenseDataSource.saveIsShowIntro(status);
    }

    @Override
    public boolean isShowIntro() {
        return mSharedPrefercenseDataSource.isShowIntro();
    }

    @Override
    public void setHalalOption(int status) {
        mSharedPrefercenseDataSource.setHalalOption(status);
    }

    @Override
    public int getHalalOption() {
        return mSharedPrefercenseDataSource.getHalalOption();
    }

    @Override
    public void setFCMToken(String fcmToken) {
        mSharedPrefercenseDataSource.setFCMToken(fcmToken);
    }

    @Override
    public String getFCMToken() {
        return mSharedPrefercenseDataSource.getFCMToken();
    }


    @Override
    public void setFirstName(String firstName) {
        mSharedPrefercenseDataSource.setFirstName(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        mSharedPrefercenseDataSource.setLastName(lastName);
    }

    @Override
    public void setEmail(String email) {
        mSharedPrefercenseDataSource.setEmail(email);
    }

    @Override
    public String getFirstName() {
        return mSharedPrefercenseDataSource.getFirstName();
    }

    @Override
    public String getLastName() {
        return mSharedPrefercenseDataSource.getLastName();
    }

    @Override
    public String getEmail() {
        return mSharedPrefercenseDataSource.getEmail();
    }

    @Override
    public void setLanguage(String language) {
        mSharedPrefercenseDataSource.setLanguage(language);
    }

    @Override
    public void setAvatar(String avatar) {
        mSharedPrefercenseDataSource.setAvatar(avatar);
    }

    @Override
    public void setCurrencyCode(String currencyCode) {
        mSharedPrefercenseDataSource.setCurrencyCode(currencyCode);
    }

    @Override
    public String getAvatar() {
        return mSharedPrefercenseDataSource.getAvatar();
    }

    @Override
    public String getCurrencyCode() {
        return mSharedPrefercenseDataSource.getCurrencyCode();
    }

    @Override
    public String getLanguage() {
        return mSharedPrefercenseDataSource.getLanguage();
    }


}
