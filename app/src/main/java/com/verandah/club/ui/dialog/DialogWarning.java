package com.verandah.club.ui.dialog;


import com.verandah.club.base.BaseActivity;
import com.verandah.club.base.BaseDialogFragment;
import com.verandah.club.utils.ResourceUtils;
import com.verandah.club.R;

public class DialogWarning extends BaseDialogFragment {


    public static DialogWarning newInstance(BaseActivity baseActivity) {
        DialogWarning dialogWarning = new DialogWarning();
        dialogWarning.setActivity(baseActivity);
        return dialogWarning;
    }

    boolean isFinish;

    public void show(String message, boolean isFinish) {
        this.isFinish = isFinish;
        setTitle(ResourceUtils.getString(R.string.dialog_title_warning));
        setPositive(ResourceUtils.getString(R.string.dialog_btn_ok));
        setMessage(message);
        show();
    }

    public void show(String title, String message, boolean isFinish) {
        this.isFinish = isFinish;
        setTitle(title);
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