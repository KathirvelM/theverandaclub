package com.verandah.club.ui.dialog;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.verandah.club.R;
import com.verandah.club.base.BaseActivity;
import com.verandah.club.base.BaseDialogFragment;
import com.verandah.club.data.model.home.Card;
import com.verandah.club.utils.ResourceUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class DialogContent extends BaseDialogFragment {

    @Override
    public int getLayout() {
        return R.layout.dialog_drop;
    }

    public static DialogContent newInstance(BaseActivity baseActivity, Card card) {
        DialogContent dialogWarning = new DialogContent();
        dialogWarning.setActivity(baseActivity);
        dialogWarning.setCard(card);
        dialogWarning.setCustomLayout(R.layout.dialog_content);
        return dialogWarning;
    }

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.tvAuthor)
    TextView tvAuthor;

    LinearLayout llContainer;

    public void setCard(Card card) {
        this.card = card;
    }

    Card card;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView.loadData(card.getContent(), "text/html; charset=utf-8", "utf-8");
        webView.setBackgroundColor(Color.TRANSPARENT);

        tvAuthor.setText(card.getAuthor());
        tvAuthor.setVisibility(card.getAuthor()!=null?View.VISIBLE:View.GONE);

        llContainer = view.findViewById(R.id.llContainer);
        llContainer.setBackground(ResourceUtils.getDrawable(R.drawable.bg_q));
    }

}