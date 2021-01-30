package com.verandah.club.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.verandah.club.custom.MyAdapter;
import com.verandah.club.data.model.home.Category;
import com.verandah.club.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SheetRestaurantCourse extends BottomSheetDialogFragment{
/*

    public class Item {
        List<Item> itemList;
        String name;

        public Item(String name) {
            this.name = name;
        }

        public Item(List<Item> itemList, String name) {
            this.itemList = itemList;
            this.name = name;
        }
    }

    List<Item> itemListIn = new ArrayList<>(Arrays.asList(
            new Item("The Verandah"),
            new Item("Mahabarata"),
            new Item("Helth & Wellness"),
            new Item("Health Tips"),
            new Item("Hygiene Tips"),
            new Item("Physical Education And Training"),
            new Item("Personalities"),
            new Item("Wellness - Ayurveda, Sidha, Naturo, Yoga"),
            new Item("Environment & Ecology")
    ));


    List<Item> itemList = new ArrayList<>(Arrays.asList(
            new Item(itemListIn,"The Verandah"),
            new Item(itemListIn,"Mahabarata"),
            new Item(itemListIn,"Helth & Wellness"),
            new Item(itemListIn,"Physical Education And Training"),
            new Item(itemListIn,"Personalities"),
            new Item(itemListIn,"Wellness - Ayurveda, Sidha, Naturo, Yoga"),
            new Item(itemListIn,"Environment & Ecology")
    ));
*/


    @BindView(R.id.rv)
    RecyclerView rv;


    public interface Listener {
        void onClickItem(Category item);
    }

    public static SheetRestaurantCourse newInstance(Listener listener, List<Category> categoryList) {
        SheetRestaurantCourse sheet = new SheetRestaurantCourse();
        sheet.setListener(listener,categoryList);
        return sheet;
    }

    Listener listener;
    List<Category> categoryList;

    private void setListener(Listener listener, List<Category> categoryList) {
        this.listener=listener;
        this.categoryList=categoryList;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetTheme);
    }

    @OnClick(R.id.btnClose)
    public void setCloseDialog() {
        dismiss();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, R.style.BottomSheetTheme);
        View view = View.inflate(getContext(), R.layout.sheet_course, null);
        ButterKnife.bind(this, view);
        dialog.setContentView(view);
        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        dialog.show();



    /*    sheetCourseAdapter = new SheetRestaurantCourseAdapter(this, values);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(sheetCourseAdapter);*/

        rv.setAdapter(new Adapter(categoryList));

    }


    public class Adapter extends MyAdapter<MyAdapter.ItemViewHolder> {

        List<Category> itemList;

        public Adapter(List<Category> itemList) {
            super(null);
            this.itemList=new ArrayList<>(itemList);
        }

        @Override
        public ItemViewHolder onCreateHolder(int viewType) {
            if(viewType==0){
                return new Adapter.TitleHolder(this);
            }else{
                return new Adapter.ItemHolder(this);
            }
        }

        @Override
        public int _getItemViewType(int position) {
            if(itemList.get(position).getCategoryList() != null){
                return 0;
            }else{
                return 1;
            }
        }

        @Override
        public int _getItemCount() {
            return itemList.size();
        }

        @Override
        public boolean isLoadMoreEnabled() {
            return false;
        }

        Integer oldPos;


        public class TitleHolder extends ItemViewHolder{

            @BindView(R.id.tv)
            public CheckedTextView tv;
            @BindView(R.id.tvCheck)
            public CheckedTextView tvCheck;

            @OnClick(R.id.tv)
            public void onClick() {
                SheetRestaurantCourse.this.listener.onClickItem(itemList.get(_getAdapterPosition()));
            }

            @OnClick(R.id.tvCheck)
            public void onClickCheck(){

                Category category = itemList.get(_getAdapterPosition());
                List<Category> itemListIn =  category.getCategoryList();


                boolean checked = !tvCheck.isChecked();
                tvCheck.setChecked(checked);

                if (oldPos != null) {
                    int past = oldPos;
                    oldPos = null;
                    itemList.removeAll(itemListIn);
                    _notifyItemRangeRemoved(past+1, itemListIn.size());
                    _notifyItemChanged(past);
                }

                if (checked) {
                    oldPos = _getAdapterPosition();
                    itemList.addAll(oldPos+1, itemListIn);
                    _notifyItemRangeInserted(oldPos+1, itemListIn.size());
                }
           /*     itemView.setOnClickListener(view -> {


                });*/
            }

            public TitleHolder(MyAdapter myAdapter) {
                super(myAdapter, R.layout.item_category);
            }

            @Override
            public void update(int position) {
                super.update(position);
                tvCheck.setVisibility(itemList.get(_getAdapterPosition()).getCategoryList().size()==0?View.GONE:View.VISIBLE);
                ((Activity)tv.getContext()).runOnUiThread(() -> {
                    tv.setText(itemList.get(position).getName());
                    tvCheck.setChecked(oldPos!=null && oldPos.equals(position));
                });
            }

        }


        public class ItemHolder extends ItemViewHolder implements View.OnClickListener {

            @BindView(R.id.tv)
            public TextView tv;

            public ItemHolder(MyAdapter myAdapter) {
                super(myAdapter, R.layout.item_category_sub);
                itemView.setOnClickListener(this);
            }

            @Override
            public void update(int position) {
                super.update(position);
                tv.setText(itemList.get(position).getName());
            }

            @Override
            public void onClick(View view) {
                SheetRestaurantCourse.this.listener.onClickItem(itemList.get(_getAdapterPosition()));
            }
        }
    }
}