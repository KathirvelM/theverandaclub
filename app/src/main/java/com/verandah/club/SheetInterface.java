package com.verandah.club;

public interface SheetInterface extends ViewInterface {
    boolean isShowing();

    int getLayout();

    void show();

    void setCustomLayout(int customLayout);

}
