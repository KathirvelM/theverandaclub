package com.verandah.club.ui.main.mvp;

import com.verandah.club.data.model.home.MainResponse;
import com.verandah.club.data.source.AppRepository;

public class MainPresenter implements MainContractor.Presenter {

    private MainContractor.View mSplashView;
    private AppRepository appRepository;
    private MainModel model;

    public MainPresenter(MainContractor.View view, AppRepository appRepository) {
        this.mSplashView = view;
        this.appRepository = appRepository;
        model = new MainModel(this, appRepository);
    }

    public void apiError(String message) {
        mSplashView.hideLoadingView();
        mSplashView.showConnectionErrorRetry(message, () -> {
            requestMain(issueId);
        });
    }

    public void load(MainResponse data) {
        mSplashView.hideLoadingView();
        mSplashView.load(data);
    }

    String issueId;
    @Override
    public void requestMain(String issueId) {
        this.issueId=issueId;
        mSplashView.showLoadingView();
        model.requestMain(issueId);
    }

    public MainModel getModel() {
        return model;
    }
}
