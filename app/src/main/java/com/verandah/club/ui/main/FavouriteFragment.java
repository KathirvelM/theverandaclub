package com.verandah.club.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.verandah.club.ui.news.NewsActivity;
import com.verandah.club.PresenterInterface;
import com.verandah.club.base.BaseFragment;
import com.verandah.club.data.model.home.Article;
import com.verandah.club.utils.PreferenceUtils;
import com.verandah.club.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FavouriteFragment extends BaseFragment implements Type {

    @BindView(R.id.rv)
    RecyclerView rv;

    public static FavouriteFragment newInstance() {
        FavouriteFragment categoryFragment = new FavouriteFragment();
        return categoryFragment;
    }

    List<Container> containerList = new ArrayList<>();

    FavouriteAdapter categoryAdapter;

    String ARG_FAV = "Fav";
    List<Article> articleList;
    public List<Article> getFavArticles(){
        return new Gson().fromJson(PreferenceUtils.readString(getContext(),ARG_FAV,"[]"), new TypeToken<List<Article>>(){}.getType());
    }

    public void setFavArticles(List<Article> articleList){
        PreferenceUtils.writeString(getContext(),ARG_FAV,new Gson().toJson(articleList));
    }

    void setFav(Article article, boolean isFav, boolean update, String type){

        article.setType(type);

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

    int REQ_FAV_REMOVED = 3;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_FAV_REMOVED) {
            int removePos = containerList.indexOf(container);
            containerList.remove(container);
            categoryAdapter._notifyItemRemoved(removePos);
        }
    }

    Container container;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        articleList = getFavArticles();

        rv.setAdapter(categoryAdapter=new FavouriteAdapter(containerList, new FavouriteAdapter.Listener() {
            @Override
            public void onClickItem(String articleId, String typeId, Container container) {
                FavouriteFragment.this.container = container;
                changeActivityForResult(NewsActivity.createIntent(getContext(),String.valueOf(articleId),typeId),REQ_FAV_REMOVED);
            }

            @Override
            public void onClickLoad(int offset) {

            }
        }));

        for(Article article : this.articleList){
            if(article.getType()==null){
                setFav(article,false,true,"");
            }else
            containerList.add(new Container(LIST,  Integer.parseInt(article.getType()),article.getArticle_data()));
        }

        categoryAdapter.onLoadFinished(containerList.size());
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav, parent, false);
    }

    @Override
    public PresenterInterface getPresenterInterface() {
        return null;
    }

}
