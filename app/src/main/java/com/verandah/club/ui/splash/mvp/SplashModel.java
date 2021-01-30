package com.verandah.club.ui.splash.mvp;

import com.verandah.club.data.model.BaseResponse;
import com.verandah.club.data.model.VersionUpdateResponse;
import com.verandah.club.data.rest.ApiClient;
import com.verandah.club.data.rest.ApiInterface;
import com.verandah.club.data.source.AppRepository;
import com.verandah.club.utils.AppUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SplashModel implements SplashContractor.Model {

    private SplashPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    SplashModel(SplashPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public void requestVersionUpdate() {
        disposable.add(apiInterface
                .checkUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<VersionUpdateResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<VersionUpdateResponse> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mPresenter.onReceiveVersionInfo(baseResponse.getData().getAndroid());
                        } else {
                            mPresenter.apiError(baseResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPresenter.apiError(AppUtils.getError(e));
                    }
                }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
