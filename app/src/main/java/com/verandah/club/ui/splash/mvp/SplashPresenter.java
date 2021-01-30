package com.verandah.club.ui.splash.mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;

import com.verandah.club.BuildConfig;
import com.verandah.club.data.model.VersionInfo;
import com.verandah.club.data.source.AppRepository;


public class SplashPresenter implements SplashContractor.Presenter {

    private static final int TIME_OUT = 3500;

    boolean isDelayFinished = false;

    private SplashContractor.View mSplashView;
    private AppRepository appRepository;
    private SplashModel model;

    VersionInfo versionInfo;

    public SplashPresenter(SplashContractor.View view, AppRepository appRepository) {
        this.mSplashView = view;
        this.appRepository = appRepository;
        model = new SplashModel(this, appRepository);
        model.requestVersionUpdate();
    }

    @SuppressLint("NewApi")
    @Override
    public void startAnimation(Context context) {
        new Handler().postDelayed(() -> {
            isDelayFinished = true;
            checkVersionAndDelay();
        }, TIME_OUT);
        mSplashView.showLoadingView();
        model.requestVersionUpdate();
    }

    void checkVersionAndDelay() {
        if(versionInfo!=null && isDelayFinished){
            if(BuildConfig.VERSION_CODE>=versionInfo.getVersionCode()){
                onContinueApp();
            }else{
                mSplashView.openUpdateDialog(versionInfo);
            }
        }
    }

    @Override
    public void onContinueApp(){
        if (appRepository.isLoggedIn()) {
            mSplashView.showDashBoardActivity();
        } else {
            mSplashView.showLoginActivity();
        }
    }

    @Override
    public void close() {
        model.close();
    }

    public void onReceiveVersionInfo(VersionInfo versionInfo) {
        mSplashView.hideLoadingView();
        this.versionInfo = versionInfo;
        checkVersionAndDelay();
    }

    public void apiError(String message) {
        mSplashView.hideLoadingView();
        mSplashView.showError(message);
    }

}
