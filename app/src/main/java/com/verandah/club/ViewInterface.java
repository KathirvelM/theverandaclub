package com.verandah.club;

import android.content.Context;
import android.net.Uri;

import com.theartofdev.edmodo.cropper.CropImageView;
import com.verandah.club.ui.dialog.DialogConnectionErrorRetry;


public interface ViewInterface {

    void showLoadingView();
    void hideLoadingView();

    void showLoadingView(int loadingCount);
    void hideLoadingView(int loadingCount);

    void hideLoadingView(int loadingCount, boolean isForce);

    void showWarning(String message);
    void showWarning(String message, boolean isFinish);
    void showSuccess(String title, String message, boolean isFinish);
    void showError(String message);
    void showError(String message, boolean isFinish);
    void showToast(String message);

    void showConnectionError(String string);
    void showConnectionError(String message, boolean isFinish);
    void showConnectionErrorRetry(String string, DialogConnectionErrorRetry.RetryListener retryListener);

    void showTokenExpired(String string);

    PresenterInterface getPresenterInterface();
    Context getContext();

    void pickImage(int requestCode, CropImageView.CropShape cropShape, int ratioX, int ratioY);
    void onImageChosen(int requestCode, Uri uri);

}
