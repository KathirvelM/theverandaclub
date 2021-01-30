package com.verandah.club.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.verandah.club.PresenterInterface;
import com.verandah.club.base.BaseFragment;
import com.verandah.club.data.model.category.CategoryResponse;
import com.verandah.club.data.model.home.ArticleData;
import com.verandah.club.ui.category.mvp.MainContractor;
import com.verandah.club.ui.category.mvp.MainPresenter;
import com.verandah.club.ui.main.Container;
import com.verandah.club.ui.main.Type;
import com.verandah.club.ui.news.NewsActivity;

import com.verandah.club.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CategoryFragment extends BaseFragment implements Type, MainContractor.View {

    @BindView(R.id.rv)
    RecyclerView rv;


    public static CategoryFragment newInstance(String issueId, String categoryId) {
        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.setValues(issueId,categoryId);
        return categoryFragment;
    }

    private void setValues(String issueId, String categoryId) {
        this.issueId=issueId;
        this.categoryId=categoryId;
    }

    MainPresenter mainPresenter;

    List<Container> containerList = new ArrayList<>();

    String issueId;
    String categoryId;

    CategoryAdapter categoryAdapter;


    List<ArticleData> articleList;
    CategoryResponse mainResponse;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainPresenter = new MainPresenter(this,appRepository);

        rv.setAdapter(categoryAdapter=new CategoryAdapter(containerList, new CategoryAdapter.Listener() {
            @Override
            public void onClickItem(String articleId, String typeId) {
                changeActivity(NewsActivity.createIntent(getContext(),String.valueOf(articleId),typeId));
            }

            @Override
            public void onClickLoad(int offset) {
                mainPresenter.requestMain(issueId,categoryId);
            }
        }));

    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, parent, false);
    }

    @Override
    public void load(CategoryResponse mainResponse) {

        this.mainResponse=mainResponse;
        this.articleList =  mainResponse.getArticleList();

        for(ArticleData article : this.articleList){
            containerList.add(new Container(LIST,  null,article));
        }

        if(!mainResponse.getPrevious_articles().getData().isEmpty())
            containerList.add(new Container(HEADING, null,"Previous Articles"));


        for(ArticleData article : mainResponse.getPrevious_articles().getData()){
            containerList.add(new Container(LIST,  null,article));
        }

        categoryAdapter.onLoadFinished(containerList.size());
    }

    @Override
    public PresenterInterface getPresenterInterface() {
        return null;
    }

}
