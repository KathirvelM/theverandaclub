package com.verandah.club.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.verandah.club.ui.news.NewsActivity;
import com.verandah.club.PresenterInterface;
import com.verandah.club.base.BaseFragment;
import com.verandah.club.data.model.home.Article;
import com.verandah.club.data.model.home.MainResponse;
import com.verandah.club.data.model.home.Section;
import com.verandah.club.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
public class HomeFragment extends BaseFragment implements Type {

    List<Container> containerList =new ArrayList<>(); /*Arrays.asList(
            new Container(R.layout.item_main_logo, null, null),
            new Container(RV     ,SLIDER,  null),
            new Container(HEADING,  null,"Latest"),
            new Container(RV     ,SLIDER_SMALL,  null),
            new Container(HEADING,  null,"Trending"),
            new Container(LIST,  null,null),
            new Container(GOOGLE_AD,  null,null),
            new Container(LIST,  null,null),
            new Container(GOOGLE_AD,  null,null),
            new Container(LIST,  null,null)
    );*/

    List<Section> sectionList;

    public void setSectionList(MainResponse mainResponse) {
        this.sectionList = mainResponse.getSectionList();
        for(Section section: sectionList){
            int pos = sectionList.indexOf(section);
            switch (pos){
                case 0:
                  //  containerList.add(new Container(R.layout.item_main_logo, null, null));
                    containerList.add(new Container(RV     ,SLIDER,  section));

                    containerList.add(new Container(CARD     ,null,  mainResponse.getThoughts()));
                    containerList.add(new Container(CARD     ,null,  mainResponse.getQuotes()));
                    containerList.add(new Container(CARD     ,null,  mainResponse.getQuotes()));
                    break;
                case 1:
                case 2:
                    if(section.getName()!=null && !section.getName().isEmpty())
                    containerList.add(new Container(HEADING, null, section.getName()));
                    containerList.add(new Container(RV, SLIDER_SMALL, section));
                    break;
                default:
                    if(section.getName()!=null && !section.getName().isEmpty())
                    containerList.add(new Container(HEADING, null, section.getName()));
                    for(Article article : section.getArticleList()){
                        if(article.getArticle_data()!=null)
                        containerList.add(new Container(LIST,  null,article));
                    }
                    break;
            }
        }
    }

    public static HomeFragment newInstance(MainResponse mainResponse) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        /*args.putSerializable(ARG_RES_ID, resId);*/
        fragment.setArguments(args);
        fragment.setSectionList(mainResponse);
        return fragment;
    }

    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.adView)
     com.google.android.gms.ads.AdView googleAdView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv.setAdapter(new HomeAdapter(containerList, new HomeAdapter.Listener() {
            @Override
            public void onClickItem(String articleId, String typeId) {

                 //   changeActivity(NewsActivity.class);
                    changeActivity(NewsActivity.createIntent(getContext(),String.valueOf(articleId),typeId));

            }
        }));

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
     //   googleAdView.setAdUnitId(context.getString(R.string.bsanner_home_footer));
    //    googleAdView.setAdSize(AdSize.BANNER);
        googleAdView.loadAd(adRequest);
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, parent, false);

    }

    @Override
    public PresenterInterface getPresenterInterface() {
        return null;
    }

}
