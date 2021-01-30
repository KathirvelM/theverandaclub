package com.verandah.club.custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.verandah.club.R;
import com.verandah.club.ui.main.MainActivity;


public class CenteredTitleToolbar extends Toolbar {

    //private TextView _titleTextView;
    private ImageView _titleTextView;
    private int _screenWidth;
    private boolean _centerTitle = false;

    public CenteredTitleToolbar(Context context) {
        super(context);
        init();
    }

    public CenteredTitleToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CenteredTitleToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        _screenWidth = getScreenSize().x;
      //  _titleTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.title, this,false);
        _titleTextView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.title, this,false);
        addView(_titleTextView);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        _screenWidth = getWidth();
        if (_centerTitle) {
            int[] location = new int[2];
            _titleTextView.getLocationOnScreen(location);
            _titleTextView.setTranslationX((getScreenSize().x-_screenWidth)+_titleTextView.getTranslationX() + (-location[0] + _screenWidth / 2 - _titleTextView.getWidth() / 2));
        }else{
            _titleTextView.setTranslationX(0);
        }
    }
    @Override
    public void setTitle(CharSequence title) {
    //    _titleTextView.setText(title);
        requestLayout();
        selectTitle();
    }

    @Override
    public void setTitle(int titleRes) {
      //  _titleTextView.setText(getResources().getString(titleRes));
        selectTitle();
    }

    public void setTitleCentered(boolean centered) {
        _centerTitle = centered;
        requestLayout();
    }

    public void setTitleColor(int color) {
      //  _titleTextView.setTextColor(color);
    }

    private Point getScreenSize() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);

        return screenSize;
    }

    public void setTitleAlpha(float alpha) {
        _titleTextView.setAlpha(alpha);
    }

    boolean reflected = false;

/*    public boolean reflectTitle() {
        if(!reflected){
            _titleTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            _titleTextView.setMarqueeRepeatLimit(-1);
        }
        return true;
    }*/

    public void selectTitle() {
        if (_titleTextView != null)
            _titleTextView.setSelected(true);
    }

    public void setImage(Drawable drawable) {
        _titleTextView.setImageDrawable(drawable);
        _titleTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                view.getContext().startActivity(i);
            }
        });
    }
}