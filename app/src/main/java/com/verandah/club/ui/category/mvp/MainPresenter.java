package com.verandah.club.ui.category.mvp;

import com.verandah.club.data.model.category.CategoryResponse;
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
      //  mSplashView.hideLoadingView();
        mSplashView.showConnectionErrorRetry(message, () -> {
            requestMain(issueId,categoryId);
        });
    }

    public void load(CategoryResponse data) {
     //   mSplashView.hideLoadingView();
        mSplashView.load(data);
    }

    String issueId;
    String categoryId;
    @Override
    public void requestMain(String issueId, String categoryId) {
        this.issueId=issueId;
        this.categoryId=categoryId;
     //   mSplashView.showLoadingView();
        model.requestMain(issueId,categoryId);
    }

    public MainModel getModel() {
        return model;
    }
}
