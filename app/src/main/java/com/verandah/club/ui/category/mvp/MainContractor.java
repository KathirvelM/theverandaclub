package com.verandah.club.ui.category.mvp;

import com.verandah.club.ViewInterface;
import com.verandah.club.data.model.category.CategoryResponse;

import io.reactivex.disposables.Disposable;

public interface MainContractor {

    interface View extends ViewInterface {
        void load(CategoryResponse mainResponse);
    }

    interface Presenter {

        void requestMain(String issueId, String categoryId);
        void load(CategoryResponse mainResponse);
    }

    interface Model {
        Disposable requestMain(String issueId, String categoryId);
    }

}
