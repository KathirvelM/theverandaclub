package com.verandah.club;

public interface DialogInterface extends ViewInterface{
    void setCanceledOnTouchOutside(boolean isCancel);
    boolean isShowing();
    int getLayout();
    void show();
    void setTitle(String title);
    void setMessage(String title);
    void setPositive(String title);
    void setNegative(String title);
    void setCustomLayout(int customLayout);
    public void onClickPositive();
    public void onClickNegative();
}
