package com.verandah.club.ui.main;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.verandah.club.custom.MyAdapter;
import com.verandah.club.data.model.home.Category;
import com.verandah.club.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CategoriesAdapter extends MyAdapter<MyAdapter.ItemViewHolder> {

    public interface Listener{
        void onClickItem(String name, List<Category> categories, Category category);
    }

    Listener listener;

    List<Category> categoryList;

    public CategoriesAdapter(Listener listener, List<Category> categoryList) {
        super(null);
        this.listener=listener;
        this.categoryList=categoryList;
    }

    @Override
    public ItemViewHolder onCreateHolder(int viewType) {
        return new ItemViewHolder(this);
    }

    @Override
    public int _getItemCount() {
        return categoryList.size();
    }

    @Override
    public boolean isLoadMoreEnabled() {
        return false;
    }

    class ItemViewHolder extends MyAdapter.ItemViewHolder{

        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.rv)
        RecyclerView rv;
        @BindView(R.id.v)
        View v;

        public ItemViewHolder(MyAdapter myAdapter) {
            super(myAdapter, R.layout.item_categories_head);
        }

        Category category;

        @Override
        public void update(int position) {
            super.update(position);

            Arrays.asList(
                    R.color.color_1,
                    R.color.color_2,
                    R.color.color_3,
                    R.color.color_4,
                    R.color.color_5,
                    R.color.color_6,
                    R.color.color_7);

            category = categoryList.get(position);

            tv.setText(category.getName());
            v.setBackgroundResource(Arrays.asList(
                    R.color.color_1,
                    R.color.color_2,
                    R.color.color_3,
                    R.color.color_4,
                    R.color.color_5,
                    R.color.color_6,
                    R.color.color_7).get(position%7));
        //    v.setBackgroundColor(Integer.parseInt("#FFAB00".replaceFirst("#",""), 16));

            rv.setAdapter(new SubcategoriesAdapter(new SubcategoriesAdapter.Listener() {
                @Override
                public void onClickItem(Category categoryChild) {
                    listener.onClickItem(ItemViewHolder.this.category.getName(),category.getCategoryList(),categoryChild);
                }
            },category.getCategoryList()));
        }

        @OnClick(R.id.llClick)
        public void onClick(){
            if(category.getCategoryList().isEmpty()){
                listener.onClickItem(category.getName(),categoryList,null);
            }else{
                listener.onClickItem(category.getName(),categoryList,category);
            }
        }
    }
}
