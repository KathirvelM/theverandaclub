package com.verandah.club.ui.main;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;


import com.verandah.club.utils.ResourceUtils;
import com.verandah.club.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomTabs {

    MainActivity mainActivity;
    LinearLayout llTab;
    TabListener tabListener;
    BackEnableListener backEnableListener;

    View previousTab = null;

    @BindView(R.id.flAbout)
    LinearLayout flBooking ;
    @BindView(R.id.flSearch)
    LinearLayout flCalendar;
    @BindView(R.id.flHome)
    LinearLayout flListing ;
    @BindView(R.id.flStore)
    LinearLayout flExplore ;
    @BindView(R.id.flCategories)
    LinearLayout flSaved   ;


    public BottomTabs(MainActivity mainActivity, LinearLayout llTab, TabListener tabListener, BackEnableListener backEnableListener) {
        this.mainActivity = mainActivity;
        this.llTab = llTab;
        this.tabListener = tabListener;
        this.backEnableListener = backEnableListener;

        ButterKnife.bind(this, llTab);


    }

    @OnClick({R.id.flAbout,/*R.id.flSearch,*/R.id.flHome,R.id.flStore,R.id.flCategories})
    public void onClick(View view) {
         selectTab(view);
        tabListener.onTabSelected(view.getId());
    }

    public void selectTab(View view) {
        if (view == null) {
            backEnableListener.enableBackButton(false);
        }
        if (previousTab != view) {
            if (previousTab != null) {
                changeColor(previousTab, android.R.color.transparent);
            }
            if (previousTab != view && view != null) {
                backEnableListener.enableBackButton(true);
                changeColor(view, R.color.colorAccent);
            }
        }
        previousTab = view;
    }


    void changeColor(View view, int color) {

        View firstChild = (ImageView)((ViewGroup)view).getChildAt(0);

        if(!(firstChild instanceof ImageView)){
            return;
        }

        Drawable drawable =((ImageView) firstChild).getDrawable().mutate();

        int bgColor = ContextCompat.getColor(mainActivity, android.R.color.darker_gray);
        int transparent = ContextCompat.getColor(mainActivity, R.color.colorAccent);
        ValueAnimator bgAnim = ValueAnimator.ofObject(new ArgbEvaluator(), bgColor, transparent);
        bgAnim.setDuration(300);
        //bgAnim.setInterpolator(new DecelerateInterpolator());
        bgAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //Log.d("anim",animation.getAnimatedFraction()+"")
                //Image.this.clearColorFilter();
                drawable.setColorFilter(new PorterDuffColorFilter(getColorWithAlpha(ResourceUtils.getColor(color), animation.getAnimatedFraction()), PorterDuff.Mode.SRC_ATOP));
            }
        });
        bgAnim.start();
    }

    private int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    public interface TabListener {
        void onTabSelected(int selectedTabPosition);
    }

    public interface BackEnableListener {
        void enableBackButton(boolean enable);
    }
}