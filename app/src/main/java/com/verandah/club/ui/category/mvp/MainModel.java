package com.verandah.club.ui.category.mvp;

import com.verandah.club.base.BaseModel;
import com.verandah.club.data.model.BaseResponse;
import com.verandah.club.data.model.category.CategoryResponse;
import com.verandah.club.data.rest.ApiClient;
import com.verandah.club.data.rest.ApiInterface;
import com.verandah.club.data.source.AppRepository;
import com.verandah.club.utils.AppUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

public class MainModel extends BaseModel implements MainContractor.Model {

    private MainPresenter presenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    MainModel(MainPresenter presenter, AppRepository appRepository1) {
        this.presenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public Disposable requestMain(String issueId,String categoryId) {
        return addRequest(
                apiInterface
                        .requestCategory(formData(categoryId),
                                formData(issueId)),
                new DisposableSingleObserver<BaseResponse<CategoryResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<CategoryResponse> baseResponse) {
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
