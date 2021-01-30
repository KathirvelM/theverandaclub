package com.verandah.club.ui.main;

import android.app.Activity;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.verandah.club.base.BaseActivity;
import com.verandah.club.custom.MyAdapter;
import com.verandah.club.data.model.home.Article;
import com.verandah.club.data.model.home.ArticleData;
import com.verandah.club.data.model.home.Card;
import com.verandah.club.data.model.home.Section;
import com.verandah.club.R;
import com.verandah.club.ui.dialog.DialogContent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeAdapter extends MyAdapter<MyAdapter.ItemViewHolder> implements Type {

    public interface Listener {
        void  onClickItem(String articleId, String typeId);
    }

    List<Container> containerList;
    Listener listener;
    public HomeAdapter(List<Container> containerList,Listener listener) {
        super(null);
        this.containerList=containerList;
        this.listener=listener;
    }

    @Override
    public ItemViewHolder onCreateHolder(int viewType) {
        switch (viewType){
            case HEADING:
                return new HeaderHolder(this);
            case RV:
                return new RvHolder(this);
            case LIST:
                return new ListHolder(this);
            case CARD:
                return new CardHolder(this);
            case GOOGLE_AD:
                return new AdsHolder(this);
        }
        return new StaticHolder(this,viewType);
    }

    @Override
    public int _getItemCount() {
        return containerList.size();
    }

    @Override
    public int _getItemViewType(int position) {
        return containerList.get(position).getItem();
    }

    @Override
    public boolean isLoadMoreEnabled() {
        return false;
    }


    class HeaderHolder extends ItemViewHolder{

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        public HeaderHolder(MyAdapter myAdapter) {
            super(myAdapter, R.layout.item_home_header);
        }

        @Override
        public void update(int position) {
            super.update(position);

            String title = (String) containerList.get(position).getObject();
            if(title!=null)
            tvTitle.setText(title);
        }
    }

    class StaticHolder extends ItemViewHolder {

        public StaticHolder(MyAdapter myAdapter, int layoutResourceId) {
            super(myAdapter, layoutResourceId);
        }
    }

    class ListHolder extends ItemViewHolder implements View.OnClickListener {

        Article article;
        ArticleData articleData;

        @BindView(R.id.ivThumb)
        ImageView ivThumb;
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.tvCategory)
        TextView tvCategory;

        public ListHolder(MyAdapter myAdapter) {
            super(myAdapter, R.layout.item_main_list);
            itemView.setOnClickListener(this);
        }

        @Override
        public void update(int position) {
            super.update(position);

            article = (Article) containerList.get(position).getObject();
            articleData = article.getArticle_data();

            Glide.with(ivThumb).load(articleData.getImage()).into(ivThumb);
            tv.setText(articleData.getTitle());
            tv.setEllipsize(TextUtils.TruncateAt.END);
            if(articleData.getCategories()!=null) tvCategory.setText(articleData.getCategories()[0]);
        }

        @Override
        public void onClick(View view) {
            HomeAdapter.this.listener.onClickItem(articleData.getId(),article.getType());
        }
    }

    class CardHolder extends ItemViewHolder {

        Card card;

        @BindView(R.id.tvContent)
        AppCompatTextView tvContent;
        @BindView(R.id.tvAuthor)
        TextView tvAuthor;

        public CardHolder(MyAdapter myAdapter) {
            super(myAdapter, R.layout.item_main_q);
        }

        @Override
        public void update(int position) {
            super.update(position);

            card = (Card) containerList.get(position).getObject();

            tvContent.setText(Html.fromHtml(Html.fromHtml(card.getContent()).toString()).toString());

            tvAuthor.setText(card.getAuthor());
            tvAuthor.setVisibility(card.getAuthor()!=null?View.VISIBLE:View.GONE);
        }

        @OnClick(R.id.rc)
        public void onClick(){
            DialogContent.newInstance((BaseActivity) itemView.getContext(),card).show();
        }

    }

    class AdsHolder extends ItemViewHolder implements View.OnClickListener {

        AdView mAdView;

        public AdsHolder(MyAdapter myAdapter) {
            super(myAdapter, R.layout.item_main_list_ad);
            itemView.setOnClickListener(this);


            mAdView = (AdView) itemView.findViewById(R.id.adView);
          //  mAdView.setAdSize(AdSize.BANNER);
          //  mAdView.setAdUnitId(mAdView.getContext().getString(R.string.banner_home_footer));

            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    // Check the LogCat to get your test device ID
                    .addTestDevice("C04B1BFFB0774708339BC273F8A43708")
                    .build();

         //   mAdView.loadAd(adRequest);
        }

        @Override
        public void onClick(View view) {
            //listener.onClickItem("");
        }
    }

    class RvHolder extends ItemViewHolder {

        @BindView(R.id.rv)
        RecyclerView rv;

        public RvHolder(MyAdapter myAdapter) {
            super(myAdapter, R.layout.item_rv);
        }

        @Override
        public void update(int position) {
            super.update(position);
            rv.setAdapter(new ItemAdapter(containerList.get(position)));
        }

        class ItemAdapter extends MyAdapter<ItemViewHolder>{

            Container container;
            List<Article> articleList;

            public ItemAdapter(Container container) {
                super(null);
                this.container = container;
                articleList = new ArrayList<>( ((Section)container.getObject()).getArticleList());

                for(Article article : new ArrayList<>(articleList)){
                    if(article.getArticle_data()==null){
                        articleList.remove(article);
                    }
                }
            }


            @Override
            public ItemViewHolder onCreateHolder(int viewType) {
                switch (viewType){
                    case SLIDER:
                        return new SliderHolder(this);
                    case SLIDER_SMALL:
                        return new SliderSmallHolder(this);
                    default:
                        return null;
                }
            }

            @Override
            public int _getItemViewType(int position) {
                return container.getType();
            }

            @Override
            public int _getItemCount() {
                return articleList.size();
            }

            @Override
            public boolean isLoadMoreEnabled() {
                return false;
            }

            class SliderHolder extends  ItemViewHolder implements View.OnClickListener {

                Article article;
                ArticleData articleData;

                @BindView(R.id.ivThumb)
                ImageView ivThumb;
                @BindView(R.id.tv)
                TextView tv;

                public SliderHolder(MyAdapter myAdapter) {
                    super(myAdapter, R.layout.item_main_slider);
                    itemView.setOnClickListener(this);

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    ((Activity) itemView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int devicewidth = (int) (displayMetrics.widthPixels * 0.8);
                    itemView.getLayoutParams().width = devicewidth;
                    itemView.requestLayout();
                }

                @Override
                public void update(int position) {
                    super.update(position);

                    article = articleList.get(position);
                    articleData = article.getArticle_data();

                    Glide.with(ivThumb).load(articleData.getImage()).into(ivThumb);
                    tv.setText(articleData.getTitle());
                    tv.setEllipsize(TextUtils.TruncateAt.END);

                    /*int drawable;

                    switch (position % 2){
                        default:
                        case 0:
                            drawable = R.drawable.image_main_slider_2;
                            break;
                        case 1:
                            drawable = R.drawable.image_main_slider;
                            break;
                    }

                    ivThumb.setImageResource(
                            drawable
                    );*/
                }

                @Override
                public void onClick(View view) {
                    HomeAdapter.this.listener.onClickItem(articleData.getId(),article.getType());
                }
            }

            class SliderSmallHolder extends ItemViewHolder implements View.OnClickListener {

                Article article;
                ArticleData articleData;
                @BindView(R.id.ivThumb)
                ImageView ivThumb;
                @BindView(R.id.tv)
                TextView tv;
                @BindView(R.id.tvCategory)
                TextView tvCategory;

                public SliderSmallHolder(MyAdapter myAdapter) {
                    super(myAdapter, R.layout.item_main_slider_small);
                    itemView.setOnClickListener(this);
                }

                @Override
                public void update(int position) {
                    super.update(position);

                    article = articleList.get(position);
                    articleData = article.getArticle_data();

                    Glide.with(ivThumb).load(articleData.getImage()).into(ivThumb);
                    tv.setText(articleData.getTitle());
                    tv.setEllipsize(TextUtils.TruncateAt.END);

                    if(articleData.getCategories()!=null) tvCategory.setText(articleData.getCategories()[0]);
                    /*int drawable;

                    switch (position % 3){
                        default:
                        case 0:
                            drawable = R.drawable.image_slider_small_0;
                            break;
                        case 1:
                            drawable = R.drawable.image_slider_small_1;
                            break;
                        case 2:
                            drawable = R.drawable.image_slider_small_2;
                            break;
                    }

                    ivThumb.setImageResource(
                            drawable
                    );*/
                }

                @Override
                public void onClick(View view) {
                    HomeAdapter.this.listener.onClickItem(articleData.getId(),article.getType());
                }
            }

        }
    }
}
