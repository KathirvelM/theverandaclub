package com.verandah.club.ui.news;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.verandah.club.R;
import com.verandah.club.custom.MyAdapter;
import com.verandah.club.data.model.home.Article;
import com.verandah.club.data.model.home.ArticleData;
import com.verandah.club.utils.DateTime;

import java.util.List;

import butterknife.BindView;

public class NewsAdapter extends MyAdapter<MyAdapter.ItemViewHolder> {

    Article article;
    ArticleData articleData;
    List<ArticleData> articleDataList;
    Listener listener;
    boolean isnotified = false;

    public NewsAdapter(Listener listener, Article article) {
        super(listener);
        this.listener = listener;
        this.article = article;
        this.articleData = article.getArticle_data();
        this.articleDataList = article.getRelated();
    }

    @Override
    public ItemViewHolder onCreateHolder(int viewType) {
        return new ListHolder(this);
    }

    @Override
    public int _getItemCount() {
        return articleDataList == null ? 0 : articleDataList.size();
    }

    @Override
    public boolean isLoadMoreEnabled() {
        return false;
    }

    @Override
    public boolean isHeader() {
        return true;
    }

    @Override
    public ItemViewHolder getHeaderViewHolder() {
        return new HeaderHolder(this);
    }

    @Override
    public boolean isNoItemEnabled() {
        return true;
    }

    public interface Listener extends MyAdapter.Listener {
        void onClickItem(String articleId, String typeId);
    }

    class HeaderHolder extends ItemViewHolder {

        @BindView(R.id.webView)
        WebView webView;
        @BindView(R.id.tvAuthor)
        TextView tvAuthor;
        @BindView(R.id.tvDate)
        TextView tvDate;


        @BindView(R.id.tvRelated)
        TextView tvRelated;

        @BindView(R.id.llPrevious)
        LinearLayout llPrevious;
        @BindView(R.id.tvPrevious)
        TextView tvPrevious;
        @BindView(R.id.llNext)
        LinearLayout llNext;
        @BindView(R.id.tvNext)
        TextView tvNext;

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvCategory)
        TextView tvCategory;

        public HeaderHolder(MyAdapter myAdapter) {
            super(myAdapter, R.layout.header_news);

            ArticleData articleData = article.getArticle_data();

            tvAuthor.setText(articleData.getAuthor());
            tvDate.setText(new DateTime(articleData.getPublished_at()).getDisplayDate());

            setData(llPrevious, tvPrevious, article.getPrevious());
            setData(llNext, tvNext, article.getNext());

            tvRelated.setVisibility(_getItemCount() == 0 ? View.GONE : View.VISIBLE);

            tvTitle.setText(articleData.getTitle());
            if (articleData.getCategories() != null) {
                tvCategory.setText(articleData.getCategories()[0]);
            } else {
                tvCategory.setVisibility(View.GONE);
            }


            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.setWebChromeClient(new WebChromeClient());
            Log.e("Content", articleData.getContent());
            String data = "<html><body>" + articleData.getContent().trim() + "</body></html>";
            if(!data.contains("https://")){
                data = data.replace("//www.youtube","https://www.youtube").replace("640","100%").replace("360","200");
            }
            webView.loadData(data, "text/html", "UTF-8");


        }

        void setData(LinearLayout ll, TextView tv, ArticleData data) {

            ll.setVisibility(data == null ? View.GONE : View.VISIBLE);
            if (data == null) return;

            tv.setText(data.getTitle());

            ll.setOnClickListener(view -> {
                listener.onClickItem(data.getId(), "1");
            });
        }
    }

    class ListHolder extends ItemViewHolder implements View.OnClickListener {

        //    Article article;
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

            articleData = (ArticleData) articleDataList.get(position);
            //   = article.getArticle_data();

            Glide.with(ivThumb).load(articleData.getImage()).into(ivThumb);
            tv.setText(articleData.getTitle());
            tv.setEllipsize(TextUtils.TruncateAt.END);

            if (articleData.getCategories() != null)
                tvCategory.setText(articleData.getCategories()[0]);
        }

        @Override
        public void onClick(View view) {
            listener.onClickItem(articleData.getId(), "1");
        }

    }

}
