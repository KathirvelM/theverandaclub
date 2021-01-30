package com.verandah.club.custom;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class AbstractAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements View.OnClickListener {

    public abstract VH onCreateHolder(int viewType);

    public abstract int _getItemViewType(int position);

    public abstract boolean isHeader();

    public abstract boolean isFooter();

    public abstract String getNoItemMessage();

    public abstract int getNoItemResource();

    public abstract boolean isLoadingEnabled();
    public abstract boolean isNoItemEnabled();

    public abstract MyAdapter.ItemViewHolder getHeaderViewHolder();

    public abstract MyAdapter.ItemViewHolder getFooterViewHolder();

    public abstract int _getItemCount();

    public abstract boolean isLoadMoreEnabled();

    public abstract  int getBgSingle();

    public abstract  int getBgTop();

    public abstract  int getBgCenter();

    public abstract  int getBgBottom();

    public abstract  void setFullWidthViewTypes(List<Integer> viewTypes);


}
