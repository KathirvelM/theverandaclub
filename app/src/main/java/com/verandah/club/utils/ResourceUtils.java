package com.verandah.club.utils;


import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.verandah.club.base.BaseApplication;


public class ResourceUtils {

    public static int getColor(int resource){
        return BaseApplication.getContext().getResources().getColor(resource);
    }

    public static String getString(int resource){
        return BaseApplication.getContext().getResources().getString(resource);
    }

    public static String[] getStringArray(int resource){
        return BaseApplication.getContext().getResources().getStringArray(resource);
    }

    public static int[] getIntArray(int resource){
        return BaseApplication.getContext().getResources().getIntArray(resource);
    }

    public static boolean getBoolean(int resource){
        return BaseApplication.getContext().getResources().getBoolean(resource);
    }

    public static int getDimension(int resource){
        return BaseApplication.getContext().getResources().getDimensionPixelSize(resource);
    }

    public static String getString(int resource, Object... formatArgs){
        return BaseApplication.getContext().getResources().getString(resource ,formatArgs);
    }

    public static int getInt(int resource){
        return BaseApplication.getContext().getResources().getInteger(resource);
    }

    public static Drawable getDrawable(int resource){
        return BaseApplication.getContext().getResources().getDrawable(resource);
    }

    public static void setChangeColor(ImageView imageView, int color){
        imageView.setColorFilter(getColor(color));
    }



}
