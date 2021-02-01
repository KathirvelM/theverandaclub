package com.verandah.club.ui.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.verandah.club.ui.news.mvp.NewsContractor;
import com.verandah.club.ui.news.mvp.NewsPresenter;
import com.verandah.club.PresenterInterface;
import com.verandah.club.base.BaseActivity;
import com.verandah.club.data.model.home.Article;
import com.verandah.club.data.model.home.ArticleData;
import com.verandah.club.utils.PreferenceUtils;
import com.verandah.club.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsActivity extends BaseActivity implements NewsContractor.View {

    private String TAG = NewsActivity.class.getSimpleName();
    InterstitialAd mInterstitialAd;

    static String ARG_ARTICLE_ID = "ARG_ARTICLE_ID";
    static String ARG_TYPE = "ARG_TYPE";

    public static Intent createIntent(Context context, String articleId, String type){
        Intent intent = new Intent(context, NewsActivity.class);
        intent.putExtra(ARG_ARTICLE_ID,articleId);
        intent.putExtra(ARG_TYPE,type);
        return intent;
    }

    NewsPresenter mainPresenter;

    String ARG_FAV = "Fav";

    public List<Article> getFavArticles(){
       return new Gson().fromJson(PreferenceUtils.readString(this,ARG_FAV,"[]"), new TypeToken<List<Article>>(){}.getType());
    }

    public void setFavArticles(List<Article> articleList){
        PreferenceUtils.writeString(this,ARG_FAV,new Gson().toJson(articleList));
    }

    boolean isFav(Article article){
        List<Article> articleList = getFavArticles();
        for(Article articleTmp : new ArrayList<>(articleList)){
            if(article.getArticle_data().getId().equals(articleTmp.getArticle_data().getId())){
                return true;
            }
        }
        return false;
    }

    void setFav(Article article, boolean isFav, boolean update, String type){

        setResult(isFav?Activity.RESULT_CANCELED:Activity.RESULT_OK);

        article.setType(type);

        ivFav.setImageResource(isFav?R.drawable.ic_nav_fav_active:R.drawable.ic_nav_fav);

        if(!update) return;

        List<Article> articleList = getFavArticles();

        if(isFav){
            articleList.add(article);
        }else{
            for(Article articleTmp : new ArrayList<>(articleList)){
                if(article.getArticle_data().getId().equals(articleTmp.getArticle_data().getId())){
                    articleList.remove(articleTmp);
                }
            }
        }

        setFavArticles(articleList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setupToolBar();
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        googleAdView.loadAd(adRequest);

        mainPresenter = new NewsPresenter(this,appRepository);

        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

      //  AdRequest adRequest = new AdRequest.Builder().build();

        // Load ads into Interstitial Ads
     //   mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });

        mainPresenter.requestMain(
                getIntent().getStringExtra(ARG_ARTICLE_ID),
                type = getIntent().getStringExtra(ARG_TYPE)
        );

    }

    String type;

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_news;
    }

    @Override
    public PresenterInterface getPresenterInterface() {
        return null;
    }

    @BindView(R.id.ivBanner)
    ImageView ivBanner;

    @BindView(R.id.ivFav)
    ImageView ivFav;

    @BindView(R.id.adView)
    com.google.android.gms.ads.AdView googleAdView;

    @OnClick(R.id.ivFav)
    public void onClickFav(){
        setFav(article,!isFav(article),true,type);
    }

    @OnClick(R.id.ivShare)
    public void onClickShare(){

        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, article.getArticle_data().getTitle());
        share.putExtra(Intent.EXTRA_TEXT, article.getArticle_data().getTitle() +" - "+ article.getArticle_data().getUrl());

        startActivity(Intent.createChooser(share, "Share"));
    }

    @BindView(R.id.rv)
    RecyclerView rv;

    NewsAdapter newsAdapter;

    Article article;

    @Override
    public void load(Article article) {
        this.article = article;

        setFav(article,isFav(article),false, type);

        ArticleData articleData = article.getArticle_data();

        rv.setAdapter(newsAdapter = new NewsAdapter(new NewsAdapter.Listener() {
            @Override
            public void onClickItem(String articleId, String typeId) {
                changeActivity(NewsActivity.createIntent(getContext(),String.valueOf(articleId),typeId));
            }

            @Override
            public void onClickLoad(int offset) {

            }
        },article));

        setTitle(articleData.getTitle());

        Glide.with(this).load(articleData.getImage()).into(ivBanner);

    }

}