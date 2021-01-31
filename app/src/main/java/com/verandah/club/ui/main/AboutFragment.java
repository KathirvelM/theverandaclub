package com.verandah.club.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.verandah.club.PresenterInterface;
import com.verandah.club.R;
import com.verandah.club.base.BaseApplication;
import com.verandah.club.base.BaseFragment;
import com.verandah.club.data.model.BaseResponse;
import com.verandah.club.data.rest.AboutResponse;
import com.verandah.club.data.rest.ApiClient;
import com.verandah.club.data.rest.ApiInterface;
import com.verandah.club.data.source.AppRepository;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AboutFragment extends BaseFragment implements Type {

    static String screenName = "";
    @BindView(R.id.webview)
    WebView webView;
    private AppRepository appRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiInterface apiInterface;

    public static AboutFragment newInstance(String ascreenName) {
        screenName = ascreenName;
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        //  args.putSerializable(ARG_RES_ID, screenName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appRepository = BaseApplication.getAppRepository();
        apiInterface = ApiClient.getApiInterface();
        if (screenName.equalsIgnoreCase(getString(R.string.screen_about))) {
            getData();
        } else if (screenName.equalsIgnoreCase(getString(R.string.screen_tc))) {
            getTermsConditions();
        }else if (screenName.equalsIgnoreCase(getString(R.string.screen_privacy))) {
            getPrivacy();
        }

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
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            webView.setWebChromeClient(new WebChromeClient());
                            String data = "<html><body>" + baseResponse.getData().getContent().trim() + "</body></html>";
                         /*   if (!data.contains("https://")) {
                                data = data.replace("//www.youtube", "https://www.youtube").replace("640", "100%").replace("360", "200");
                            }*/
                            webView.loadData(data, "text/html", "UTF-8");

                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("About", "onError: ");
                        //mPresenter.apiError(AppUtils.getError(e));
                    }
                }));
    }


    private void getTermsConditions() {
        disposable.add(apiInterface
                .getTermsConditions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<AboutResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<AboutResponse> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            webView.setWebChromeClient(new WebChromeClient());
                            String data = "<html><body>" + baseResponse.getData().getContent().trim() + "</body></html>";
                         /*   if (!data.contains("https://")) {
                                data = data.replace("//www.youtube", "https://www.youtube").replace("640", "100%").replace("360", "200");
                            }*/
                            webView.loadData(data, "text/html", "UTF-8");

                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("About", "onError: ");
                        //mPresenter.apiError(AppUtils.getError(e));
                    }
                }));
    }


    private void getPrivacy() {
        disposable.add(apiInterface
                .getPrivacy()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<AboutResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<AboutResponse> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            webView.setWebChromeClient(new WebChromeClient());
                            String data = "<html><body>" + baseResponse.getData().getContent().trim() + "</body></html>";
                         /*   if (!data.contains("https://")) {
                                data = data.replace("//www.youtube", "https://www.youtube").replace("640", "100%").replace("360", "200");
                            }*/
                            webView.loadData(data, "text/html", "UTF-8");

                        } else {

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
