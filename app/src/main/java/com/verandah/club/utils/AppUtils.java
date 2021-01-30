package com.verandah.club.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Base64;
import android.util.Log;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.verandah.club.R;
import com.verandah.club.base.BaseApplication;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.HttpException;


public final class AppUtils {

    private AppUtils() {
        // This class is not publicly instantiable
    }


    /**
     * Gets hash key.
     *
     * @param context the context
     */
    public static void getHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }

    public static String getAutoCaps(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String getAmount(String currencyCode, String amount) {
        return currencyCode + amount;
    }

    public static String getAmountSymbol(String amount, String currencyCode) {
        return currencyCode + amount;
    }

    public static String getPercentageSymbol(String amount, String percentage) {
        return amount + percentage;
    }

    public static String getBonusPts(String points, String pts) {
        return points + " " + pts;
    }

    public static CircularProgressDrawable getProgressDrawable() {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(BaseApplication.getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }




    public static float getAlpha(float progress, float from, float to) {
        if (progress < from) {
            return 0f;
        }
        if (to < progress) {
            return 1f;
        }
        return (progress - from) * (to / (to - from));
    }



    public static String capsWord(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }



    public static SpannableStringBuilder setSuperScript(String currencySymbol, String amount) {
        SpannableStringBuilder cs = new SpannableStringBuilder(getAmount(currencySymbol, amount));
        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(0.75f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return cs;
    }




    public static ArrayList<String> getGuestCount(int maxCount) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < maxCount; i++) {
            arrayList.add("" + (i + 1));
        }
        return arrayList;
    }

    public static String getError(Throwable e) {
        String error = ResourceUtils.getString(R.string.network_error);
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            error = NetworkUtils.getErrorMessage(responseBody);
        } else if (e instanceof SocketTimeoutException) {
            error = ResourceUtils.getString(R.string.time_out_error);
        } else if (e instanceof IOException) {
            error = ResourceUtils.getString(R.string.network_error);
        } else {
            error = e.getMessage();
        }
        return error;
    }

}
