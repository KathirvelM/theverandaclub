package com.verandah.club.ui.news.mvp;

import com.verandah.club.data.model.home.Article;
import com.verandah.club.data.source.AppRepository;

public class NewsPresenter implements NewsContractor.Presenter {

    private NewsContractor.View mSplashView;
    private AppRepository appRepository;
    private NewsModel model;

    public NewsPresenter(NewsContractor.View view, AppRepository appRepository) {
        this.mSplashView = view;
        this.appRepository = appRepository;
        model = new NewsModel(this, appRepository);
    }

    public void apiError(String message) {
        mSplashView.hideLoadingView();
        mSplashView.showConnectionErrorRetry(message, () -> {
            requestMain(articleId,type);
        });
    }

    public void load(Article data) {
        mSplashView.hideLoadingView();
        mSplashView.load(data);
    }

    String articleId;
    String type;
    @Override
    public void requestMain(String articleId, String type) {
        this.articleId=articleId;
        this.type=type;
        mSplashView.showLoadingView();
        model.requestMain(articleId,type);
    }

    public NewsModel getModel() {
        return model;
    }
}
