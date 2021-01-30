package com.verandah.club.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.verandah.club.PresenterInterface;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.verandah.club.DialogInterface;

import com.verandah.club.ui.dialog.DialogConnectionErrorRetry;

import com.verandah.club.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public abstract class BaseDialogFragment extends DialogFragment implements DialogInterface, View.OnClickListener {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.btnPositive)
    Button btnPositive;
    @BindView(R.id.btnNegative)
    Button btnNegative;
    FrameLayout flContainer;

    protected ImageButton btnClose;

    ViewGroup view;


    protected BaseActivity baseActivity;

    FragmentManager fragmentManager;

    public BaseDialogFragment() {
        super();
    }

    protected void setActivity(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        fragmentManager = baseActivity.getSupportFragmentManager();
    }

    boolean isShowing = false;

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    boolean isCancelOnOutSideTouch;

    @Override
    public void setCanceledOnTouchOutside(boolean isCancelOnOutSideTouch) {
        this.isCancelOnOutSideTouch = isCancelOnOutSideTouch;
    }

    @Override
    public void show() {
        if (isShowing) {
            dismiss();
        }
        isShowing = true;
        show(fragmentManager, null);
    }

    @Override
    public void onDismiss(android.content.DialogInterface dialog) {
        super.onDismiss(dialog);
        isShowing = false;
    }

    @Override
    public int getLayout() {
        return R.layout.dialog;
    }

    String title;
    String message;
    String positive;
    String negative;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setBackgroundDrawableResource(R.color.colorDialogScreen);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {

        view = (ViewGroup) inflater.inflate(getLayout(), parent, false);
        view.setOnClickListener(v -> {
            if (isCancelOnOutSideTouch && isCancelable()) dismiss();
        });

        flContainer = view.findViewById(R.id.flContainer);
        if (customLayout != 0)
            flContainer.addView(inflater.inflate(customLayout, flContainer, false));
        btnClose = view.findViewById(R.id.btnClose);
        unbinder = ButterKnife.bind(this, view);

        setText(btnNegative, negative);
        setText(btnPositive, positive);
        setText(tvMessage, message);
        setText(tvTitle, title);

        if (btnClose != null) btnClose.setOnClickListener(this);
        return view;
    }

    int customLayout;

    public void setCustomLayout(int customLayout) {
        this.customLayout = customLayout;
    }

    @OnClick(R.id.btnPositive)
    @Override
    public void onClickPositive() {
    }

    @OnClick(R.id.btnNegative)
    @Override
    public void onClickNegative() {
    }

    void setText(TextView view, String title) {
        if (view != null)
            if (title != null) {
                view.setText(title);
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        setText(tvTitle, title);
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
        setText(tvMessage, message);
    }

    @Override
    public void setPositive(String positive) {
        this.positive = positive;
        setText(btnPositive, positive);
    }

    @Override
    public void setNegative(String negative) {
        this.negative = negative;
        setText(btnNegative, negative);
    }


    Unbinder unbinder;

    public void onCreate(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog);
        super.onCreate(savedInstanceState);
    }

    public void showLoadingView() {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).showLoadingView();
    }

    public void hideLoadingView() {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).hideLoadingView();
    }

    public void showLoadingView(int requestId) {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).showLoadingView(requestId);
    }

    public void hideLoadingView(int requestId) {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).hideLoadingView(requestId);
    }

    public void hideLoadingView(int requestId, boolean force) {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).hideLoadingView(requestId, force);
    }

    @Override
    public void pickImage(int requestCode, CropImageView.CropShape cropShape, int ratioX, int ratioY) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.pickImage(requestCode,
                cropShape,
                ratioX,
                ratioY);
    }

    @Override
    public void onImageChosen(int requestCode, Uri uri) {

    }

    public void showWarning(String message) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showWarning(message);
    }

    public void showError(String message) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showError(message);
    }

    public void showConnectionError(String message) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showConnectionError(message);
    }

    public void showWarning(String message, boolean isFinish) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showWarning(message, isFinish);
    }

    public void showError(String message, boolean isFinish) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showError(message, isFinish);
    }

    public void showConnectionError(String message, boolean isFinish) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showConnectionError(message, isFinish);
    }


    @Override
    public void showSuccess(String title, String message, boolean isFinish) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showSuccess(title, message, isFinish);
    }

    public void showTokenExpired(String message) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showTokenExpired(message);
    }

    @Override
    public PresenterInterface getPresenterInterface() {
        return null;
    }

    public void showConnectionErrorRetry(String message, DialogConnectionErrorRetry.RetryListener retryListener) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showConnectionErrorRetry(message, retryListener);
    }

    public void showToast(int message) {
        showToast(getResources().getString(message));
    }

    public void showToast(String message) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showToast(message);
    }

    public void changeActivity(Intent i) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.changeActivity(i);
    }

    public void changeActivityForResult(Intent i) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.startActivityForResult(i, 0);
    }

    public void changeActivity(Class clz) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.changeActivity(clz);
    }

    public void changeActivityClearBackStack(Class clz) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.changeActivityClearBackStack(clz);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClose:
                dismiss();
                break;
        }
    }
}