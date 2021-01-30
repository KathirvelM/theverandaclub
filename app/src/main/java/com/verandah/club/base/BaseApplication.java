package com.verandah.club.base;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.google.android.gms.ads.MobileAds;
import com.verandah.club.data.rest.ApiClient;
import com.verandah.club.data.rest.ApiInterface;
import com.verandah.club.data.source.AppRepository;
import com.verandah.club.data.source.sharedpreference.AppPreferenceDataSource;
import com.verandah.club.R;


public class BaseApplication extends MultiDexApplication {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    protected static AppRepository appRepository;
    private static ApiInterface apiInterface;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appRepository = new AppRepository(new AppPreferenceDataSource(context));
        apiInterface = ApiClient.getApiInterface();

        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }

    public static AppRepository getAppRepository() {
        return appRepository;
    }

    public static ApiInterface getApiInterface() {
        return apiInterface;
    }
}
