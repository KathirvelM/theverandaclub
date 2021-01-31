package com.verandah.club.ui.main.adapters;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.verandah.club.R;
import com.verandah.club.custom.MyAdapter;
import com.verandah.club.data.rest.ContactUsAdapterModel;

import java.util.ArrayList;

public class ContactusAdapter extends RecyclerView.Adapter<ContactusAdapter.ViewHolder> {

    ArrayList<ContactUsAdapterModel> articleDataList;
    Listener listener;
    Context context;

    public ContactusAdapter(Listener listener, ArrayList<ContactUsAdapterModel> baseResponse, Context con) {
        this.listener = listener;
        this.articleDataList = baseResponse;
        this.context = con;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_contact_us, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactUsAdapterModel articleData = articleDataList.get(position);
        if (articleData.getType().equalsIgnoreCase("phone")) {
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.ic_call));
        } else if (articleData.getType().equalsIgnoreCase("email")) {
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.ic_world));
        } else if (articleData.getType().equalsIgnoreCase("address")) {
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.ic_location));
        } else {
            holder.img.setVisibility(View.GONE);
            holder.tv.setText(articleData.getValue());
            Linkify.addLinks(holder.tv, Linkify.ALL);
            holder.tv.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    public int getItemCount() {
        return articleDataList.size();
    }


    public interface Listener extends MyAdapter.Listener {
        void onClickItem(String articleId, String typeId);
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv;
        ImageView img;


        public ViewHolder(View myAdapter) {
            super(myAdapter);
            this.tv = (TextView) myAdapter.findViewById(R.id.tv);
            this.img = (ImageView) myAdapter.findViewById(R.id.img);
        }


        @Override
        public void onClick(View view) {
        }

    }

}
