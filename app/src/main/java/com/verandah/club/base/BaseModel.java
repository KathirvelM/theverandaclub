package com.verandah.club.base;

import android.net.Uri;

import com.verandah.club.data.rest.ApiInterface;
import com.verandah.club.ModelInterface;

import java.io.File;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public abstract class BaseModel<T> implements ModelInterface {
    CompositeDisposable disposable = new CompositeDisposable();
    protected ApiInterface apiInterface = BaseApplication.getApiInterface();

    public DisposableSingleObserver<T> addRequest(Single<T> singleResponse, DisposableSingleObserver<T> disposableSingleObserver) {
        disposable.add(singleResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableSingleObserver));

        return disposableSingleObserver;
    }

    public void close() {
        disposable.dispose();
    }

    protected RequestBody formData(String string) {
        if (string == null) return null;
        return RequestBody.create(MediaType.parse("multipart/form-data"), string);
    }

    protected MultipartBody.Part image(String paramName, Uri uri) {
        if(uri==null) return  null;
        File file = new File(uri.getPath());
        RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return  MultipartBody.Part.createFormData(paramName, file.getName(), mFile);
    }
}
