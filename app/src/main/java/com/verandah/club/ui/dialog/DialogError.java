package com.verandah.club.ui.dialog;


import com.verandah.club.base.BaseActivity;
import com.verandah.club.base.BaseDialogFragment;
import com.verandah.club.utils.ResourceUtils;
import com.verandah.club.R;

public class DialogError extends BaseDialogFragment {

    public static DialogError newInstance(BaseActivity baseActivity) {
        DialogError dialogWarning = new DialogError();
        dialogWarning.setActivity(baseActivity);
        return dialogWarning;
    }

    boolean isFinish;

    public void show(String message, boolean isFinish) {
        this.isFinish = isFinish;
        setTitle(ResourceUtils.getString(R.string.dialog_title_error));
        setPositive(ResourceUtils.getString(R.string.dialog_btn_ok));
        setMessage(message);
        show();
    }

    @Override
    public void onClickPositive() {
        dismiss();
        if (isFinish) baseActivity.finish();
    }
}