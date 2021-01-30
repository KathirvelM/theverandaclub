package com.verandah.club.ui.news.mvp;

import com.verandah.club.ViewInterface;
import com.verandah.club.data.model.home.Article;

import io.reactivex.disposables.Disposable;

public interface NewsContractor {

    interface View extends ViewInterface {
        void load(Article articlee);
    }

    interface Presenter {

        void requestMain(String articleId, String typeId);
        void load(Article article);
    }

    interface Model {
        Disposable requestMain(String articleId, String type);
    }

}
