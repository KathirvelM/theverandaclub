package com.verandah.club.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.verandah.club.PresenterInterface;
import com.verandah.club.R;
import com.verandah.club.base.BaseApplication;
import com.verandah.club.base.BaseFragment;
import com.verandah.club.data.model.BaseResponse;
import com.verandah.club.data.rest.ApiClient;
import com.verandah.club.data.rest.ApiInterface;
import com.verandah.club.data.rest.ContactResponse;
import com.verandah.club.data.rest.ContactUsAdapterModel;
import com.verandah.club.data.source.AppRepository;
import com.verandah.club.ui.main.adapters.ContactusAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ContactusFragment extends BaseFragment implements Type {


    @BindView(R.id.call_rv)
    RecyclerView call_rv;

    @BindView(R.id.txt_aboutus)
    TextView aboutUsTv;

    @BindView(R.id.app_store_img)
    ImageView appStoreImg;

    @BindView(R.id.play_store_img)
    ImageView playStoreImg;

    @BindView(R.id.img_fb)
    ImageView fbImg;

    @BindView(R.id.img_insta)
    ImageView instaImg;

    @BindView(R.id.img_twitter)
    ImageView twitterImg;

    @BindView(R.id.img_yt)
    ImageView ytImg;


    ContactusAdapter contactusAdapter;
    private AppRepository appRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiInterface apiInterface;

    public static ContactusFragment newInstance() {

        ContactusFragment fragment = new ContactusFragment();
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
        getData();
    }

    private void getData() {
        showLoadingView();
        disposable.add(apiInterface
                .getcontact()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ContactResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<ContactResponse> baseResponse) {
                        if (baseResponse.isSuccess()) {


                            appStoreImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (baseResponse.getData().getApp().getIos() != null && baseResponse.getData().getApp().getIos().length() > 0)
                                        openUrl(baseResponse.getData().getApp().getIos());
                                    else showToast("Coming Soon");
                                }
                            });

                            playStoreImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (baseResponse.getData().getApp().getAndroid() != null && baseResponse.getData().getApp().getAndroid().length() > 0)
                                        openUrl(baseResponse.getData().getApp().getAndroid());
                                    else showToast("Coming Soon");
                                }
                            });


                            ytImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (baseResponse.getData().getSocial().getYoutube() != null && baseResponse.getData().getSocial().getYoutube().length() > 0)
                                        openUrl(baseResponse.getData().getSocial().getYoutube());
                                    else showToast("Coming Soon");
                                }
                            });

                            fbImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (baseResponse.getData().getSocial().getFacebook() != null && baseResponse.getData().getSocial().getFacebook().length() > 0)
                                        openUrl(baseResponse.getData().getSocial().getFacebook());
                                    else showToast("Coming Soon");
                                }
                            });

                            instaImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (baseResponse.getData().getSocial().getInstagram() != null && baseResponse.getData().getSocial().getInstagram().length() > 0)
                                        openUrl(baseResponse.getData().getSocial().getInstagram());
                                    else showToast("Coming Soon");
                                }
                            });

                            twitterImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (baseResponse.getData().getSocial().getTwitter() != null && baseResponse.getData().getSocial().getTwitter().length() > 0)
                                        openUrl(baseResponse.getData().getSocial().getTwitter());
                                    else showToast("Coming Soon");
                                }
                            });
                            aboutUsTv.setText(baseResponse.getData().getContent());
                            ArrayList<ContactUsAdapterModel> data = new ArrayList<>();

                            ContactUsAdapterModel cc = new ContactUsAdapterModel();
                            cc.setType("phone");
                            cc.setValue("");
                            data.add(cc);

                            for (int i = 0; i < baseResponse.getData().getphone().size(); i++) {
                                ContactUsAdapterModel mContactUsAdapterModel = new ContactUsAdapterModel();
                                mContactUsAdapterModel.setType("");
                                mContactUsAdapterModel.setValue(baseResponse.getData().getphone().get(i));
                                data.add(mContactUsAdapterModel);
                            }

                            ContactUsAdapterModel bb = new ContactUsAdapterModel();
                            bb.setType("email");
                            bb.setValue("");
                            data.add(bb);

                            for (int i = 0; i < baseResponse.getData().getEmail().size(); i++) {
                                ContactUsAdapterModel mContactUsAdapterModel = new ContactUsAdapterModel();
                                mContactUsAdapterModel.setType("");
                                mContactUsAdapterModel.setValue(baseResponse.getData().getEmail().get(i));
                                data.add(mContactUsAdapterModel);
                            }

                            ContactUsAdapterModel aa = new ContactUsAdapterModel();
                            aa.setType("address");
                            aa.setValue("");
                            data.add(aa);

                            for (int i = 0; i < baseResponse.getData().getaddress().size(); i++) {
                                ContactUsAdapterModel mContactUsAdapterModel = new ContactUsAdapterModel();
                                mContactUsAdapterModel.setType("");
                                mContactUsAdapterModel.setValue(baseResponse.getData().getaddress().get(i));
                                data.add(mContactUsAdapterModel);
                            }


                            contactusAdapter = new ContactusAdapter(new ContactusAdapter.Listener() {
                                @Override
                                public void onClickItem(String articleId, String typeId) {

                                }

                                @Override
                                public void onClickLoad(int offset) {

                                }
                            }, data, context);
                            call_rv.setHasFixedSize(true);
                            call_rv.setLayoutManager(new LinearLayoutManager(context));
                            call_rv.setAdapter(contactusAdapter);


                        } else {

                        }
                        hideLoadingView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoadingView();
                        Log.d("About", "onError: ");
                        //mPresenter.apiError(AppUtils.getError(e));
                    }
                }));
    }


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_us, parent, false);

    }

    @Override
    public PresenterInterface getPresenterInterface() {
        return null;
    }

}
