package com.verandah.club.ui.splash;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.verandah.club.BuildConfig;
import com.verandah.club.PresenterInterface;
import com.verandah.club.base.BaseActivity;
import com.verandah.club.data.model.VersionInfo;
import com.verandah.club.ui.splash.mvp.SplashContractor;
import com.verandah.club.ui.splash.mvp.SplashPresenter;
import com.verandah.club.R;
import com.verandah.club.ui.main.MainActivity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements SplashContractor.View {

    SplashPresenter splashPresenter;

    private static final String TAG = "Splash";

    @BindView(R.id.iv_splash)
    ImageView ivSplash;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.tvVersion)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        fireBaseRegister();


        tvVersion.setText(getString(R.string.version_name, BuildConfig.VERSION_NAME));

        checkOreo();
        splashPresenter = new SplashPresenter(this, appRepository);
        splashPresenter.startAnimation(this);
        String appToken = appRepository.getFCMToken();
        Log.i(TAG, "fireBaseRegister: " + appToken);
        if (appToken.equals("")) {
      //    fireBaseRegister();
        }
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    private void checkOreo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.app_name);
            String channelName = getString(R.string.app_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_HIGH));
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public PresenterInterface getPresenterInterface() {
        return null;
    }


    Dialog dialog;

    @Override
    public void showLoginActivity() {
        changeActivity(MainActivity.class);
        finish();
    }

    @Override
    public void showDashBoardActivity() {
        changeActivity(MainActivity.class);
        finish();
    }

    @Override
    public void openUpdateDialog(VersionInfo versionInfo) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final TextView tvMessage = dialog.findViewById(R.id.tv_message);
        final Button btnSkip = dialog.findViewById(R.id.btn_skip);
        final Button btnContinue = dialog.findViewById(R.id.btn_continue);
        final TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.update_available));
        btnContinue.setText(getString(R.string.btn_update));
        dialog.setCancelable(false);
        tvMessage.setText(getString(R.string.update_description,versionInfo.getVersion()));
        btnContinue.setOnClickListener(v -> {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        });

        btnSkip.setText(getString(R.string.btn_skip_update));
        btnContinue.setText(getString(R.string.btn_update_now));
        btnSkip.setOnClickListener(v -> {
            if (versionInfo.isForceUpdate()) {
                dialog.dismiss();
                finish();
            } else {
                dialog.dismiss();
                splashPresenter.onContinueApp();
            }
        });
        dialog.show();
    }

    public void fireBaseRegister() {
        FirebaseInstanceId.getInstance().
                getInstanceId().
                addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();
                    Log.i(TAG, "fireBaseRegister: " + token);
                    appRepository.setFCMToken(token);
                });
    }


    @Override
    public void showLoadingView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        progressBar.setVisibility(View.GONE);
    }


}