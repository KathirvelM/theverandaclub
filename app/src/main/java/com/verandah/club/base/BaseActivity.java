package com.verandah.club.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.verandah.club.PresenterInterface;
import com.verandah.club.ViewInterface;
import com.verandah.club.custom.CenteredTitleToolbar;
import com.verandah.club.data.source.AppRepository;
import com.verandah.club.utils.NetworkUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import com.verandah.club.ui.dialog.DialogConnectionError;
import com.verandah.club.ui.dialog.DialogConnectionErrorRetry;
import com.verandah.club.ui.dialog.DialogError;
import com.verandah.club.ui.dialog.DialogWarning;

import com.verandah.club.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements ViewInterface {

    protected Context context;
    public AppRepository appRepository;
    public CenteredTitleToolbar toolbar;

    @Override
    public Context getContext() {
        return context;
    }

    List<String> loadingCountList = new ArrayList<>();

    public void showLoadingView(int loadingCount) {
        loadingCountList.add(String.valueOf(loadingCount));
        this.showLoadingView();
    }

    public void hideLoadingView(int loadingCount) {
        getHandler().postDelayed(() -> {
            for (String loadingCountString : loadingCountList) {
                if (loadingCountString.equals(String.valueOf(loadingCount))) {
                    loadingCountList.remove(String.valueOf(loadingCount));
                    break;
                }
            }
            if (loadingCountList.size() == 0)
                this.hideLoadingView();
        }, 1000);
    }

    public void hideLoadingView(int loadingCount, boolean force) {
        getHandler().postDelayed(() -> {
            for (String loadingCountString : loadingCountList) {
                if (loadingCountString.equals(String.valueOf(loadingCount))) {
                    loadingCountList.remove(String.valueOf(loadingCount));
                    break;
                }
            }
            if (loadingCountList.size() == 0)
                this.hideLoadingView();
        }, force ? 0 : 1000);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appRepository = BaseApplication.getAppRepository();
        if (getLayout() != 0)
            setContentView(getLayout());
        ButterKnife.bind(this);
        context = this;
    }

    public abstract @LayoutRes
    int getLayout();

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    protected Dialog progressDialog;


    public void showLoadingView() {
        if (progressDialog == null) progressDialog = loading();
        if (progressDialog != null)
            if (!isDestroyed()) {
                progressDialog.show();
            }
    }

    public Dialog loading() {
        Dialog dialog = new Dialog(this/*, R.style.AppCompatAlertDialogStyle*/);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_loading, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        return dialog;
    }

    public void hideLoadingView() {
        if (progressDialog != null) {
            if (!isDestroyed()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
    }

    public void setupToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleCentered(true);
        toolbar.setTitleColor(getResources().getColor(R.color.tv));
        toolbar.setImage(getResources().getDrawable(R.drawable.logo2));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideKeyboard();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void changeActivity(Intent i) {
        startActivity(i);
    }

    public void changeActivityForResult(Intent i, int reqCode) {
        startActivityForResult(i, reqCode);
    }

    public void changeActivity(Class clz) {
        Intent i = new Intent(this, clz);
        changeActivity(i);
    }

    public void changeActivityClearBackStack(Class clz) {
        changeActivityClearBackStack(new Intent(this, clz));
    }

    public void changeActivityClearBackStack(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        changeActivity(intent);
    }

    @Override
    public void showError(String message, boolean isFinish) {
        DialogError.newInstance(this).show(message, isFinish);
    }

    @Override
    public void showError(String message) {
        DialogError.newInstance(this).show(message, false);
    }

    @Override
    public void showWarning(String message, boolean isFinish) {
        DialogWarning.newInstance(this).show(message, isFinish);
    }

    @Override
    public void showWarning(String message) {
        DialogWarning.newInstance(this).show(message, false);
    }


    @Override
    public void showSuccess(String title, String message, boolean isFinish) {
        DialogWarning.newInstance(this).show(title, message, isFinish);
    }

    @Override
    public void showConnectionError(String string, boolean isFinish) {
        DialogConnectionError.newInstance(this).show(string, isFinish);
    }

    @Override
    public void showConnectionErrorRetry(String string, DialogConnectionErrorRetry.RetryListener retryListener) {
        DialogConnectionErrorRetry.newInstance(this).show(string, retryListener);
    }

    @Override
    public void showTokenExpired(String string) {

    }

    @Override
    public void showConnectionError(String string) {
        showConnectionError(string, false);
    }

    public void showToast(String message) {
        if (message != null && !message.equalsIgnoreCase("Nill")) {
            if (!message.equals("")) {
                showCustomSnackbar(message);
            }
        }
    }

    List<View> snackbar = new ArrayList<>();

    private void showCustomSnackbar(String message) {

        View customSnackbar = LayoutInflater.from(context).inflate(R.layout.custom_snackbar, null, false);
        ((TextView) customSnackbar.findViewById(R.id.text)).setText(message);

        snackbar.add(customSnackbar);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.format = PixelFormat.TRANSLUCENT;
        lp.gravity = Gravity.BOTTOM;
        lp.flags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        customSnackbar.setLayoutParams(lp);

        if (getWindowManager() != null) {
            getWindowManager().addView(customSnackbar, customSnackbar.getLayoutParams());

            Point m = new Point();
            getWindowManager().getDefaultDisplay().getSize(m);
            int childMeasureSpecWidth = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(m.x, View.MeasureSpec.EXACTLY), 0, lp.width);
            int childMeasureSpecHeight = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(m.y, View.MeasureSpec.EXACTLY), 0, lp.height);
            customSnackbar.measure(childMeasureSpecWidth, childMeasureSpecHeight);

            customSnackbar.setTranslationY(customSnackbar.getMeasuredHeight());
            customSnackbar.animate()
                    .setDuration(300)
                    .translationX(0.0f)
                    .translationY(0.0f);
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                customSnackbar.animate()
                        .setDuration(300)
                        .translationX(0.0f)
                        .translationY(customSnackbar.getMeasuredHeight())
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
                                if (windowManager != null) {
                                    if (!isDestroyed) {
                                        customSnackbar.setVisibility(View.GONE);
                                        if (snackbar.contains(customSnackbar)) {
                                            windowManager.removeViewImmediate(customSnackbar);
                                            snackbar.remove(customSnackbar);
                                        }
                                    }
                                }
                            }
                        });
            }
        };
        getHandler().postDelayed(runnable, 1700);
    }

    public Handler getHandler() {
        if (handler == null) handler = new Handler();
        return handler;
    }

    Handler handler;

    boolean isDestroyed;

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    protected void onDestroy() {
        PresenterInterface presenterInterface = getPresenterInterface();
        if (presenterInterface != null) presenterInterface.close();

        if (progressDialog != null) progressDialog.dismiss();
        progressDialog = null;
        getHandler().removeCallbacksAndMessages(null);
        for (View v : snackbar) {
            getWindowManager().removeViewImmediate(v);
        }
        isDestroyed = true;
        super.onDestroy();
    }

    public void addFragment(int container, Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.add(container, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public Fragment replaceFragment(int container, Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        return replaceFragment(fm, container, fragment, tag);
    }

    public Fragment replaceFragment(FragmentManager fm, int container, Fragment fragment, String
            tag) {
        hideKeyboard();
        try {
            fm.popBackStackImmediate();
        } catch (IllegalStateException ignored) {
            // There's no way to avoid getting this if saveInstanceState has already been called.
        }

        FragmentTransaction ft = fm.beginTransaction();

        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(container, fragment, tag);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();

        return fragment;
    }

    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public int getActionBarHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }


    int requestCode;

    int REQ_WRITE_EXTERNAL_STORAGE = 2323;

    CropImageView.CropShape cropShape = CropImageView.CropShape.OVAL;
    int ratioX;
    int ratioY;

    void onAllowed(int requestCode) {
        if (requestCode == REQ_WRITE_EXTERNAL_STORAGE)
            pickImage(this.requestCode, cropShape, ratioX, ratioY);
    }

    public void pickImage(int requestCode, CropImageView.CropShape cropShape, int ratioX,
                          int ratioY) {
        this.requestCode = requestCode;
        this.cropShape = cropShape;
        this.ratioX = ratioX;
        this.ratioY = ratioY;
        if (isAllowed(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, REQ_WRITE_EXTERNAL_STORAGE)) {
            CropImage.startPickImageActivity(this);
        }
    }

    public List<Fragment> getFragments() {
        return getSupportFragmentManager().getFragments();
    }

    @Override
    public void onImageChosen(int requestCode, Uri uri) {
        for (Fragment fragment : getFragments()) {
            if (fragment instanceof BaseFragment) {
                ((BaseFragment) fragment).onImageChosen(requestCode, uri);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            CropImage.activity(imageUri)
                    .setCropShape(cropShape)
                    .setFixAspectRatio(true)
                    .setAspectRatio(ratioX, ratioY)
                    .setRequestedSize(0, 512)
                    /*.setBackgroundColor(Color.BLACK)*/
                    .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setOutputCompressQuality(40)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                onImageChosen(this.requestCode, result.getUri());
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public static boolean isAllowed(AppCompatActivity activity, String permission,
                                    int requestCode) {
        if (ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            return false;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            onAllowed(requestCode);
      /*  else
            onCancelled(requestCode);*/
    }


    public String getString(EditText editText) {
        return editText.getText().toString();
    }

}