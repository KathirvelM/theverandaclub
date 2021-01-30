package com.verandah.club.ui.dialog;


import com.verandah.club.base.BaseActivity;
import com.verandah.club.base.BaseDialogFragment;
import com.verandah.club.utils.ResourceUtils;
import com.verandah.club.R;

public class DialogExit extends BaseDialogFragment {

    public static DialogExit newInstance(BaseActivity baseActivity) {
        DialogExit dialogConnectionRetry = new DialogExit();
        dialogConnectionRetry.setActivity(baseActivity);
        return dialogConnectionRetry;
    }

    public void show() {
        setTitle(ResourceUtils.getString(R.string.dialog_title_exit));
        setMessage(ResourceUtils.getString(R.string.dialog_message_exit));
        setPositive(ResourceUtils.getString(R.string.dialog_btn_exit));
        setNegative(ResourceUtils.getString(R.string.dialog_btn_cancel));
        super.show();
    }

    @Override
    public void onClickPositive() {
        dismiss();
        baseActivity.finish();
    }

    @Override
    public void onClickNegative() {
        dismiss();
    }
}