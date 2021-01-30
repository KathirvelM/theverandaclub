package com.verandah.club.data.rest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.verandah.club.data.source.AppRepository;
import com.verandah.club.base.BaseApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class ApiInterceptor implements Interceptor {
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_ACCEPT = "Accept";
    private static final String AUTHORIZATION = "Authorization";

    // private static final String TOKEN_KEY = "Bearer ";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        AppRepository mAppPreference = BaseApplication.getAppRepository();
        String mToken = !mAppPreference.getOAuthKey().equals("") ? /*TOKEN_KEY +*/ mAppPreference.getOAuthKey() : "";
        Log.d("TOKEN", mToken);
        Request chainRequest = chain.request();
        Builder builder = chainRequest.newBuilder();
        builder.header(HEADER_CONTENT_TYPE, "application/json");
        builder.header(HEADER_ACCEPT, "application/json");
        builder.header(AUTHORIZATION, mToken);
        Request request = builder.build();
        return chain.proceed(request);
    }
}
