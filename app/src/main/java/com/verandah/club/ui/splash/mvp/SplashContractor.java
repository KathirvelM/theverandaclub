package com.verandah.club.ui.splash.mvp;

import android.content.Context;

import com.verandah.club.ViewInterface;
import com.verandah.club.data.model.VersionInfo;

public interface SplashContractor {

    interface View extends ViewInterface {

        void showLoginActivity();

        void showDashBoardActivity();

        void openUpdateDialog(VersionInfo versionInfo);
    }

    interface Presenter {

        void startAnimation(Context context);

        void onContinueApp();

        void close();
    }

    interface Model {

        void requestVersionUpdate();
        void close();

    }

}
