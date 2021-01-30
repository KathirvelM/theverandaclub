package com.verandah.club.ui.dialog;


import com.verandah.club.base.BaseActivity;
import com.verandah.club.base.BaseDialogFragment;
import com.verandah.club.utils.ResourceUtils;
import com.verandah.club.R;

public class DialogConnectionErrorRetry extends BaseDialogFragment {

    public interface RetryListener {
        void onClickRetry();
    }

    RetryListener retryListener;

    public static DialogConnectionErrorRetry newInstance(BaseActivity baseActivity) {
        DialogConnectionErrorRetry dialogConnectionRetry = new DialogConnectionErrorRetry();
        dialogConnectionRetry.setActivity(baseActivity);
        dialogConnectionRetry.setCancelable(false);
        return dialogConnectionRetry;
    }

    public void show(String message, RetryListener retryListener) {
        this.retryListener = retryListener;
        setTitle(ResourceUtils.getString(R.string.dialog_title_connection_error));
        setPositive(ResourceUtils.getString(R.string.dialog_btn_retry));
        setNegative(ResourceUtils.getString(R.string.dialog_btn_exit));
        setMessage(message);
        show();
    }

    @Override
    public void onClickPositive() {
        dismiss();
        retryListener.onClickRetry();
    }

    @Override
    public void onClickNegative() {
        baseActivity.finish();
    }
}