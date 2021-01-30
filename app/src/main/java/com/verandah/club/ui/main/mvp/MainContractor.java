package com.verandah.club.ui.main.mvp;

import com.verandah.club.ViewInterface;
import com.verandah.club.data.model.home.MainResponse;

import io.reactivex.disposables.Disposable;

public interface MainContractor {

    interface View extends ViewInterface {
        void load(MainResponse mainResponse);
    }

    interface Presenter {

        void requestMain(String issueId);
        void load(MainResponse mainResponse);
    }

    interface Model {
        Disposable requestMain(String issueId);
    }

}
