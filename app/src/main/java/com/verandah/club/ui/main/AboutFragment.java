package com.verandah.club.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.verandah.club.PresenterInterface;
import com.verandah.club.base.BaseApplication;
import com.verandah.club.base.BaseFragment;
import com.verandah.club.R;
import com.verandah.club.data.model.BaseResponse;
import com.verandah.club.data.model.VersionUpdateResponse;
import com.verandah.club.data.rest.AboutResponse;
import com.verandah.club.data.rest.ApiClient;
import com.verandah.club.data.rest.ApiInterface;
import com.verandah.club.data.source.AppRepository;
import com.verandah.club.ui.splash.mvp.SplashModel;
import com.verandah.club.utils.AppUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AboutFragment extends BaseFragment implements Type {

    private AppRepository appRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiInterface apiInterface;

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        /*args.putSerializable(ARG_RES_ID, resId);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appRepository = BaseApplication.getAppRepository();
        apiInterface = ApiClient.getApiInterface();

        getData();
    }

    private void getData() {
        disposable.add(apiInterface
                .getAbout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<AboutResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<AboutResponse> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            //mPresenter.onReceiveVersionInfo(baseResponse.getData().getAndroid());
                        } else {
                            //mPresenter.apiError(baseResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("About", "onError: ");
                        //mPresenter.apiError(AppUtils.getError(e));
                    }
                }));
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, parent, false);

    }

    @Override
    public PresenterInterface getPresenterInterface() {
        return null;
    }

}
