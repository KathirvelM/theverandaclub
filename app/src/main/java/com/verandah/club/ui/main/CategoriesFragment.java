package com.verandah.club.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.verandah.club.PresenterInterface;
import com.verandah.club.base.BaseFragment;
import com.verandah.club.data.model.home.Category;
import com.verandah.club.R;

import java.util.List;

import butterknife.BindView;

public class CategoriesFragment extends BaseFragment implements Type {

    @BindView(R.id.rv)
    RecyclerView rv;

    public interface Listener {
        void onClickItem(String name, List<Category> categories, Category category);
    }

    public static CategoriesFragment newInstance(Listener listener, List<Category> categoryList) {
        CategoriesFragment categoriesFragment = new CategoriesFragment();
        categoriesFragment.setListener(listener,categoryList);
        return categoriesFragment;
    }

    Listener listener;
    List<Category> categoryList;

    private void setListener(Listener listener, List<Category> categoryList) {
        this.listener=listener;
        this.categoryList=categoryList;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv.setAdapter(new CategoriesAdapter(new CategoriesAdapter.Listener() {
            @Override
            public void onClickItem(String name, List<Category> categories, Category category) {
                if(category==null){
                    showToast("No Item Found");
                }else{
                    listener.onClickItem(name,categories,category);
                }
            }

        }, categoryList));

    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, parent, false);

    }

    @Override
    public PresenterInterface getPresenterInterface() {
        return null;
    }

}
