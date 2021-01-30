package com.verandah.club.ui.news.mvp;

import com.verandah.club.base.BaseModel;
import com.verandah.club.data.model.BaseResponse;
import com.verandah.club.data.model.home.Article;
import com.verandah.club.data.rest.ApiClient;
import com.verandah.club.data.rest.ApiInterface;
import com.verandah.club.data.source.AppRepository;
import com.verandah.club.utils.AppUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

public class NewsModel extends BaseModel implements NewsContractor.Model {

    private NewsPresenter presenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    NewsModel(NewsPresenter presenter, AppRepository appRepository1) {
        this.presenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public Disposable requestMain(String articleId, String type) {

        if(type.equals("1")){
            return addRequest(
                    apiInterface
                            .requestArticle(formData(articleId),formData("empty")),
                    new DisposableSingleObserver<BaseResponse<Article>>() {
                        @Override
                        public void onSuccess(BaseResponse<Article> baseResponse) {
                            if (baseResponse.isSuccess()) {
                                presenter.load(baseResponse.getData());
                            } else {
                                presenter.apiError(baseResponse.getMessage());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            presenter.apiError(AppUtils.getError(e));
                        }
                    }
            );
        }else{
            return addRequest(
                    apiInterface
                            .requestSponserAd(formData(articleId),formData("empty")),
                    new DisposableSingleObserver<BaseResponse<Article>>() {
                        @Override
                        public void onSuccess(BaseResponse<Article> baseResponse) {
                            if (baseResponse.isSuccess()) {
                                presenter.load(baseResponse.getData());
                            } else {
                                presenter.apiError(baseResponse.getMessage());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            presenter.apiError(AppUtils.getError(e));
                        }
                    }
            );
        }
    }
}
