package com.verandah.club.custom;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.verandah.club.R;
import com.verandah.club.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

public abstract class MyAdapter<VH extends MyAdapter.ItemViewHolder> extends AbstractAdapter<VH> implements View.OnClickListener {

    boolean isNoItemVisible = false;
    boolean isLoadingVisible = false;
    boolean isLoadingErrorVisible = false;
    boolean isLoadingMoreVisible = false;
    boolean isLoadingMoreErrorVisible = false;

    public boolean isNoItemVisible() {
        return isNoItemVisible;
    }

    public boolean isLoadingVisible() {
        return isLoadingVisible;
    }

    public boolean isLoadingErrorVisible() {
        return isLoadingErrorVisible;
    }

    public MyAdapter(Listener listener) {
        this.listener = listener;
    }

    public void reset(){
        setNoItemVisible(false);
        setLoadingVisible(true);
        isLoadingErrorVisible = false;
        isLoadingMoreVisible = false;
        isLoadingMoreErrorVisible = false;
        notifyDataSetChanged();
        if(endlessRvScrollListener!=null)endlessRvScrollListener.reset();
    }

    public void reLoad() {
        reset();
        listener.onClickLoad(0);
    }

    public int getPageIndex() {
        return endlessRvScrollListener.getCurrentPage();
    }

    public String getPageNumber() {
        return String.valueOf(endlessRvScrollListener.getCurrentPage()+1);
    }

    String errorMessage;

    public void onLoadFailed(String error) {
        errorMessage = error;
        if (isLoadingVisible) {
            setLoadingVisible(false);
            setLoadingErrorVisible(true);
            notifyDataSetChanged();
        } else if (isLoadingMoreVisible) {
            isLoadingMoreVisible = false;
            isLoadingMoreErrorVisible = true;
            notifyItemChanged(getItemCount());
        }
    }

    private void setLoadingVisible(boolean isLoading){
        this.isLoadingVisible = isLoading;
    }

    private void setLoadingErrorVisible(boolean isLoadingErrorVisible){
        this.isLoadingErrorVisible = isLoadingErrorVisible;
    }

    private void setNoItemVisible(boolean isNoItem){
        this.isNoItemVisible = isNoItem;
    }

    public void onLoadFinished(int count) {

            if (isLoadingVisible) {
                setLoadingVisible(false);
                if (count == 0) {
                    setNoItemVisible(true);
                    // notifyItemChanged(isHeader()?1:0);

                } else {
                    //  _notifyItemRemoved(isHeader()?1:0);
                }
                notifyDataSetChanged();
            } else if (isLoadingMoreVisible) {
                isLoadingMoreVisible = false;
                if (count == 0) $notifyItemRemoved(getItemCount());
            } else if (count == 0 && !isLoadMoreEnabled()) {
                setNoItemVisible(true);
                notifyDataSetChanged();
            }

            if (count != 0) {
                int startPosition = getItemCount();
                int endPosition = startPosition + count;
                //           Log.d("NOTIFY INSERTED : ","("+startPosition+","+endPosition+")");
                notifyItemRangeInserted(startPosition, endPosition);
                if (endlessRvScrollListener != null)
                    endlessRvScrollListener.loadingFinished(getItemCount());
            }

    }

    public interface Listener {
        void onClickLoad(int offset);
    }

    protected Listener listener;

    public void onClickLoad() {
        Log.d("ON_CLICK", "" + _getItemCount());
        if (_getItemCount() == 0) {
            if (isLoadingErrorVisible) {
                setLoadingErrorVisible(false);
                //_notifyItemRemoved(isHeader()?1:0);
            }
            setLoadingVisible(true);
            //notifyItemInserted(isHeader()?1:0);
            notifyDataSetChanged();
        } else {
            if (isLoadingMoreErrorVisible) {
                isLoadingMoreErrorVisible = false;
                $notifyItemRemoved(getItemCount());
            }

            if (isLoadMoreEnabled()) {
                isLoadingMoreVisible = true;
                $notifyItemInserted(getItemCount()-1);
            }
        }
        if (listener != null)
            listener.onClickLoad(_getItemCount());
    }

    public void _notifyItemRemoved(int position) {
        if(isNoItemEnabled() && !isNoItemVisible() && (_getItemCount()==0)){
            setNoItemVisible(true);
            _notifyItemChanged(0);
        }
        else
        $notifyItemRemoved((isHeader()?position+1:position));
    }

    public void $notifyItemRemoved(int position) {
        if(position>0)notifyItemChanged(position-1);
        notifyItemRemoved(position);
    }

    public void _notifyItemInserted(int position) {
        if(isNoItemEnabled() && isNoItemVisible() && (_getItemCount()>0)){
            setNoItemVisible(false);
            _notifyItemChanged(0);
        }
        else
            $notifyItemInserted(isHeader()?position+1:position);
    }

    public void $notifyItemInserted(int position) {
        if(position>0)notifyItemChanged(position-1);
        notifyItemInserted(position);
    }

    EndlessRecyclerViewScrollListener endlessRvScrollListener;
    RecyclerView recyclerView;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    List<Integer> fullWidthViewTypes = Arrays.asList(
            VT_NO_ITEM,
            VT_HEADER,
            VT_FOOTER,
            VT_LOADING,
            VT_LOADING_ERROR,
            VT_LOADING_MORE,
            VT_LOADING_MORE_ERROR
    );

    @Override
    public void setFullWidthViewTypes(List<Integer> viewTypes) {
        fullWidthViewTypes = new ArrayList<>(fullWidthViewTypes);
        fullWidthViewTypes.addAll(viewTypes);
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
            {
                @Override
                public int getSpanSize(int position)
                {
                    boolean isInfoView = false;

                    for(Integer integer :fullWidthViewTypes){
                        isInfoView  = integer == getItemViewType(position);
                        if(isInfoView) break;
                    }

                    return  isInfoView ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }

        isLoadingVisible = _getItemCount() == 0;
        this.recyclerView = recyclerView;
        //    Log.d("ON_CLICK","LOAD");
        if (isLoadMoreEnabled()) {
            endlessRvScrollListener = new EndlessRecyclerViewScrollListener(recyclerView.getLayoutManager()) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    MyAdapter.this.recyclerView.post(new Runnable() {
                        public void run() {
                            MyAdapter.this.onClickLoad();
                        }
                    });
                }
            };
            recyclerView.addOnScrollListener(endlessRvScrollListener);
        }

        super.onAttachedToRecyclerView(recyclerView);
        onClickLoad();

    }

    public final static int VT_NO_ITEM = -1000;

    public final static int VT_HEADER = -1001;
    public final static int VT_FOOTER = -1002;

    public final static int VT_LOADING = -1003;
    public final static int VT_LOADING_ERROR = -1004;

    public final static int VT_LOADING_MORE = -1005;
    public final static int VT_LOADING_MORE_ERROR = -1006;

    public ViewGroup parent;

    private ItemViewHolder noItemViewHolder;

    private ItemViewHolder header;
    private ItemViewHolder footer;

    public ItemViewHolder getHeader() {
        return header;
    }

    public ItemViewHolder getFooter() {
        return footer;
    }

    private LoadingViewHolder loadingViewHolder;
    private LoadingErrorViewHolder loadingErrorViewHolder;

    private LoadingMoreViewHolder loadingMoreViewHolder;
    private LoadingMoreErrorViewHolder loadingMoreErrorViewHolder;

    public final void _notifyItemRangeInserted(int positionStart, int itemCount) {
        int pos = positionStart -2;
        if(pos>-1)
        notifyItemChanged(pos);
        super.notifyItemRangeInserted(positionStart + (isHeader() ? 1 : 0), itemCount);
    }

    public final void _notifyItemRangeRemoved(int positionStart, int itemCount) {
        super.notifyItemRangeRemoved(positionStart + (isHeader() ? 1 : 0), itemCount);
    }

    public final void _notifyItemChanged(int position) {
        super.notifyItemChanged(position + (isHeader() ? 1 : 0));
    }

    public final void _notifyItemRangeChanged(int positionStart, int itemCount) {
        super.notifyItemRangeChanged(positionStart + (isHeader() ? 1 : 0), itemCount);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        //  Log.d("viewType", "" + viewType);
        ItemViewHolder itemViewHolder;

        switch (viewType) {
            case VT_LOADING_MORE_ERROR:
                itemViewHolder = loadingMoreErrorViewHolder = new LoadingMoreErrorViewHolder();
                break;
            case VT_LOADING_MORE:
                itemViewHolder = loadingMoreViewHolder = new LoadingMoreViewHolder();
                break;
            case VT_LOADING:
                itemViewHolder = loadingViewHolder = new LoadingViewHolder();
                break;
            case VT_NO_ITEM:
                itemViewHolder = noItemViewHolder = getNoItemViewHolder();
                break;
            case VT_LOADING_ERROR:
                itemViewHolder = loadingErrorViewHolder = new LoadingErrorViewHolder();
                break;
            case VT_HEADER:
                itemViewHolder = header = getHeaderViewHolder();
                if(headerCreatedListener !=null) headerCreatedListener.onHeaderCreated(header);
                break;
            case VT_FOOTER:
                itemViewHolder = footer = getFooterViewHolder();
                break;
            default:
                itemViewHolder = onCreateHolder(viewType);
                break;
        }
       // itemViewHolder.itemView.setTag(viewType);
        return (VH) itemViewHolder;
    }

    protected ItemViewHolder getNoItemViewHolder(){
        return new NoItemViewHolder();
    }

    HeaderCreatedListener  headerCreatedListener;

    public void setHeaderCreatedListener(HeaderCreatedListener  headerCreatedListener){
        this.headerCreatedListener = headerCreatedListener;
    }

    public interface HeaderCreatedListener{
        void onHeaderCreated(ItemViewHolder headerViewHolder);
    }

    public abstract VH onCreateHolder(int viewType);


    boolean binding = false;

    public boolean isBinding() {
        return binding;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if(position>=0){
            binding = true;
            holder.update(position - (isHeader() ? 1 : 0));
            binding = false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isNoItemEnabled() && isNoItemVisible && _getItemCount() ==0 && position == (isHeader() ? 1 : 0) && (!isLoadingVisible || !isLoadingEnabled()) && !isLoadingErrorVisible)
            return VT_NO_ITEM;
        if (isLoadingMoreVisible &&  position == getItemCount() - 1) return VT_LOADING_MORE;
        if (isLoadingMoreErrorVisible && position == getItemCount() - 1) return VT_LOADING_MORE_ERROR;
        if (isLoadingEnabled() && isLoadingVisible && position == (isHeader() ? 1 : 0)) return VT_LOADING;
        if (isFooter() && position == getItemCount() - 1) return VT_FOOTER;
        if (isHeader() && position == 0) return VT_HEADER;
        if (_getItemCount() == 0 && position == (isHeader() ? 1 : 0) && isLoadingErrorVisible)
            return VT_LOADING_ERROR;
        else return _getItemViewType(position - (isHeader() ? 1 : 0));
    }

    @Override
    public int getItemCount() {
        int countList = _getItemCount();
        int countLoading = isLoadingEnabled()&& isLoadingVisible ? 1 : 0;
        int countLoadingError = isLoadingErrorVisible && _getItemCount()==0 ? 1 : 0;
        int countNoItem = isNoItemEnabled() && isNoItemVisible && _getItemCount()==0 ? 1 : 0;
        int countHeader = isHeader() ? 1 : 0;
        int countFooter = isFooter() ? 1 : 0;
        int countLoadingMore = isLoadingMoreVisible ? 1 : 0;
        int countLoadingMoreError = isLoadingMoreErrorVisible ? 1 : 0;

/*
        Log.d("countList : ",""+countList);
        Log.d("countLoading : ",""+countLoading);
        Log.d("countNoItem : ",""+countNoItem);
        Log.d("countHeader : ",""+countHeader);
        Log.d("countFooter : ",""+countFooter);
        Log.d("countLoadingMore : ",""+countLoadingMore);
        Log.d("countLoadingMoreError",""+countLoadingMoreError);
        Log.d("countLoadingError : ",""+countLoadingError);
*/

        int itemCount = countList + countLoading + countNoItem + countHeader + countFooter + countLoadingMore + countLoadingMoreError + countLoadingError;
        //      Log.d("ITEM_COUNT : ",""+itemCount);
        return itemCount;
    }


    public abstract int _getItemCount();

    public abstract static class AbstractViewHolder extends  RecyclerView.ViewHolder{

        public AbstractViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void update(int position);
    }

    public abstract static class ItemViewHolder extends AbstractViewHolder{
        MyAdapter myAdapter;

        public ItemViewHolder(MyAdapter myAdapter, int layoutResourceId) {
            super(LayoutInflater.from(myAdapter.recyclerView.getContext()).inflate(layoutResourceId, myAdapter.recyclerView, false));
            ButterKnife.bind(this,itemView);
            this.myAdapter = myAdapter;
        }

        public ItemViewHolder(MyAdapter myAdapter, View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.myAdapter = myAdapter;
        }

        private int bg = 0;

        @Override
        public void update(int position) {
            if (getAdapterPosition() == 0) {
                if (myAdapter.getItemCount() == 1) {
                    if (myAdapter.getBgSingle() != 0)
                        setBb(myAdapter.getBgSingle());
                } else {
                    if (myAdapter.getBgTop() != 0)
                        setBb(myAdapter.getBgTop());
                }
            } else if (getAdapterPosition() == myAdapter.getItemCount() - 1) {
                if (myAdapter.getBgBottom() != 0)
                    setBb(myAdapter.getBgBottom());
            } else {
                if (myAdapter.getBgCenter() != 0)
                    setBb(myAdapter.getBgCenter());
            }
        }

        private void setBb(int bg){
            if(this.bg!=bg){
                itemView.setBackgroundResource(bg);
                this.bg=bg;
            }
        }


        public int _getAdapterPosition() {
            //        Log.d("POS", getAdapterPosition() + "");
            //        Log.d("POS_IS_HEADER", myAdapter.isHeader() + "  " + (myAdapter.isHeader() ? 1 : 0));
            return getAdapterPosition() - (myAdapter.isHeader() ? 1 : 0);
        }
    }


    class NoItemViewHolder extends ItemViewHolder {
        TextView tvNoItem;

        public NoItemViewHolder() {
            super(MyAdapter.this, R.layout.li_no_item);
            tvNoItem = itemView.findViewById(R.id.tvNoItem);
            tvNoItem.setText(getNoItemMessage() != null ? getNoItemMessage() : itemView.getResources().getString(R.string.no_item));
            tvNoItem.setCompoundDrawablesWithIntrinsicBounds(0, getNoItemResource(), 0, 0);
        }

        @Override
        public void update(int position) {
            super.update(position);
        }
    }




    class LoadingErrorViewHolder extends ItemViewHolder {
        LinearLayout llLoadingError;
        TextView tvLoadingError;

        public LoadingErrorViewHolder() {
            super(MyAdapter.this, R.layout.li_loading_error);
            llLoadingError = itemView.findViewById(R.id.llLoadingError);
            tvLoadingError = itemView.findViewById(R.id.tvLoadingError);
            // tvLoadingError.setText(getNoItemMessage() != null ? getNoItemMessage() : "Error Loading");
            tvLoadingError.setCompoundDrawablesWithIntrinsicBounds(0, getNoItemResource(), 0, 0);
            llLoadingError.setOnClickListener(MyAdapter.this);
        }

        @Override
        public void update(int position) {
            super.update(position);
            tvLoadingError.setText(ResourceUtils.getString(R.string.loading_error,errorMessage));
        }

    }

    class LoadingViewHolder extends ItemViewHolder {

        public LoadingViewHolder() {
            super(MyAdapter.this, R.layout.li_loading);
        }

        @Override
        public void update(int position) {
            super.update(position);
        }
    }

    class LoadingMoreViewHolder extends ItemViewHolder {

        public LoadingMoreViewHolder() {
            super(MyAdapter.this, R.layout.li_loading_more);
        }

        @Override
        public void update(int position) {
            super.update(position);
        }
    }

    class LoadingMoreErrorViewHolder extends ItemViewHolder {

        TextView tvLoadingMoreError;

        public LoadingMoreErrorViewHolder() {
            super(MyAdapter.this, R.layout.li_loading_more_error);
            tvLoadingMoreError = itemView.findViewById(R.id.tvLoadingMoreError);


            // tvLoadingMoreError.setText(getNoItemMessage() != null ? getNoItemMessage() : "Error Loading More");
            tvLoadingMoreError.setCompoundDrawablesWithIntrinsicBounds(0, getNoItemResource(), 0, 0);
            tvLoadingMoreError.setOnClickListener(MyAdapter.this);

        }

        @Override
        public void update(int position) {
            super.update(position);
            tvLoadingMoreError.setText(ResourceUtils.getString(R.string.loading_more_error,errorMessage));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llLoadingError:
            case R.id.tvLoadingMoreError:
                onClickLoad();
                break;
            case R.id.tvLoadingError:
                onClickLoad();
                break;
        }
    }

    @Override
    public int _getItemViewType(int position) {
        return 0;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public boolean isFooter() {
        return false;
    }

    String noItemMessage = null;

    public void setNoItemMessage(String noItemMessage) {
        this.noItemMessage = noItemMessage;
    }

    @Override
    public String getNoItemMessage() {
        return noItemMessage;
    }

    @Override
    public int getNoItemResource() {
        return 0;
    }

    @Override
    public boolean isLoadingEnabled() {
        return false;
    }

    @Override
    public boolean isNoItemEnabled() {
        return true;
    }

    @Override
    public ItemViewHolder getHeaderViewHolder() {
        return null;
    }

    @Override
    public ItemViewHolder getFooterViewHolder() {
        return null;
    }

    @Override
    public int getBgSingle() {
        return 0;
    }

    @Override
    public int getBgTop() {
        return 0;
    }

    @Override
    public int getBgCenter() {
        return 0;
    }

    @Override
    public int getBgBottom() {
        return 0;
    }


}
