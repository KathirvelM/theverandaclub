package com.verandah.club.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import butterknife.Unbinder;

public abstract class BasePinFragment extends DialogFragment implements DialogInterface, View.OnClickListener {

    FrameLayout flContainer;

    ImageButton btnClose;

    ViewGroup view;

    protected BaseActivity baseActivity;

    FragmentManager fragmentManager;

    public BasePinFragment() {
        super();
    }

    int x = 0;
    int y = 0;
    int w = 0;
    int h = 0;

    public void setXY(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        Log.d("SIZE",
                "X:"+x+
                ", Y:"+y+
                ", W:"+w+
                ", H:"+h
        );
    }

    public void setActivity(BaseActivity baseActivity) {
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
        return R.layout.dialog_pin;
    }

    @BindView(R.id.flPin)
    LinearLayout flPin;

    @BindView(R.id.ivPinTop)
    ImageView ivPinTop;
    @BindView(R.id.ivPinBottom)
    ImageView ivPinBottom;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // getDialog().getWindow().setBackgroundDrawableResource(R.color.colorDialogScreen);


        measure();

       // ivPin.setY(y);
    }


    void measure(){
        flPin = view.findViewById(R.id.flPin);
        ivPinTop= view.findViewById(R.id.ivPinTop);
        ivPinBottom= view.findViewById(R.id.ivPinBottom);

        flContainer.post(() -> {
            int activityHeight = baseActivity.getWindow().getDecorView().getHeight();

            if((activityHeight/2)>y){
                flPin.setY(y);
                ivPinTop.setX(x+(w/2)-(ivPinTop.getWidth()/2));
            }else{
                ivPinTop.setVisibility(View.GONE);
                ivPinBottom.setVisibility(View.VISIBLE);
                flPin.setY(y- flContainer.getHeight()-ivPinTop.getHeight());
                ivPinBottom.setX(x+(w/2)-(ivPinTop.getWidth()/2));
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        view = (ViewGroup) inflater.inflate(getLayout(), parent, false);
        view.setOnClickListener(v -> {
            if(isCancelOnOutSideTouch && isCancelable()) dismiss();
        });

        flContainer = view.findViewById(R.id.flContainer);
        if (customLayout != 0)
            flContainer.addView(inflater.inflate(customLayout, flContainer, false));

        btnClose = view.findViewById(R.id.btnClose);
        unbinder = ButterKnife.bind(this, view);

        if (btnClose != null) btnClose.setOnClickListener(this);
        return view;
    }

    int customLayout;
    public void setCustomLayout(int customLayout) {
        this.customLayout = customLayout;
    }

    Unbinder unbinder;

    public void onCreate(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.PinView);
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


    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setMessage(String title) {

    }

    @Override
    public void setPositive(String title) {

    }

    @Override
    public void setNegative(String title) {

    }
    @Override
    public void onClickPositive() {

    }

    @Override
    public void onClickNegative() {

    }

    protected void setTargetView(View targetView) {

        int[] xy = new int[2];

        targetView.getLocationOnScreen(xy);

        setXY(xy[0],xy[1],
                targetView.getWidth(),
                targetView.getHeight());
    }
}