package com.verandah.club.data.source;

public interface AppDataSource {
    void delete();

    void saveIsLoggedIn(boolean isLoggedIn);

    boolean isLoggedIn();

    void setUserId(String userId);

    String getUserId();

    void setOAuthKey(String oAuthKey);

    String getOAuthKey();

    void saveIsShowIntro(boolean status);

    boolean isShowIntro();

    void setFCMToken(String fcmToken);

    String getFCMToken();

    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setEmail(String email);
    void setLanguage(String language);
    void setHalalOption(int halalOption);
    void setAvatar(String avatar);
    void setCurrencyCode(String currencyCode);

    String getFirstName();
    String getLastName();
    String getEmail();
    String getLanguage();
    int getHalalOption();
    String getAvatar();
    String getCurrencyCode();

}
