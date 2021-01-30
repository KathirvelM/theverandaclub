package com.verandah.club.ui.main;

import android.widget.TextView;

import com.verandah.club.custom.MyAdapter;
import com.verandah.club.data.model.home.Category;
import com.verandah.club.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SubcategoriesAdapter extends MyAdapter<MyAdapter.ItemViewHolder> {

    public interface Listener {
        void onClickItem(Category category);
    }

    Listener listener;
    List<Category> categoryList;

    public SubcategoriesAdapter(Listener listener, List<Category> categoryList) {
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

        Category category;

        @BindView(R.id.tv)
        TextView tv;

        public ItemViewHolder(MyAdapter myAdapter) {
            super(myAdapter, R.layout.item_categories_item);

        }

        @Override
        public void update(int position) {
            super.update(position);
            category = categoryList.get(position);
            tv.setText(category.getName());
        }

        @OnClick(R.id.llContainer)
        public void onClick(){
            listener.onClickItem(category);
        }

    }
}
