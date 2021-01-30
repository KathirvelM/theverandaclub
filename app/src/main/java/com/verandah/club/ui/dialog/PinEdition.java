package com.verandah.club.ui.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.verandah.club.base.BaseActivity;
import com.verandah.club.base.BasePinFragment;
import com.verandah.club.custom.MyAdapter;
import com.verandah.club.data.model.home.Issue;
import com.verandah.club.data.model.home.Volume;
import com.verandah.club.utils.DateTime;
import com.verandah.club.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PinEdition extends BasePinFragment {

    public static PinEdition newInstance(BaseActivity baseActivity, View targetView, Listener listener, List<Volume> volumeList) {
        PinEdition pin = new PinEdition();
        pin.setCustomLayout(R.layout.dialog_edition);
        pin.setActivity(baseActivity);
        pin.setTargetView(targetView);
        pin.setListener(listener,volumeList);
        pin.setCanceledOnTouchOutside(true);
        return pin;
    }

    private void setListener(Listener listener, List<Volume> volumeList) {
        this.listener = listener;
        this.volumeList = volumeList;
    }

    public interface Listener {
        void onSelectIssue(Issue issue);
    }

    List<Volume> volumeList;
    Listener listener;


    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rv.setAdapter(new EditionAdapter(volumeList));

    }


    public class EditionAdapter extends MyAdapter<MyAdapter.ItemViewHolder> {

        final int VT_VOLUME = 0;
        final int VT_ISSUE = 1;

        List<Object> objectList  = new ArrayList<>();

        public EditionAdapter(List<Volume> volumeList) {
            super(null);
            setFullWidthViewTypes(Arrays.asList(
                    VT_VOLUME
            ));

            for(Volume volume : volumeList){
                objectList.add(volume);
                objectList.addAll(volume.getIssueList());
            }
        }

        @Override
        public ItemViewHolder onCreateHolder(int viewType) {
            switch (viewType){
                default:
                case VT_VOLUME:
                    return new VolumeHolder();
                case VT_ISSUE:
                    return new IssueHolder();
            }
        }


        @Override
        public int _getItemViewType(int position) {
           Object obj = objectList.get(position);
           if(obj instanceof Volume) return VT_VOLUME;
           return VT_ISSUE;
        }

        @Override
        public int _getItemCount() {
            return objectList.size();
        }

        @Override
        public boolean isLoadMoreEnabled() {
            return false;
        }

        public  class VolumeHolder extends ItemViewHolder {
            Volume volume;
            @BindView(R.id.tv)
            public TextView tv;
            public VolumeHolder() {
                super(EditionAdapter.this,R.layout.item_volume);
            }

            @Override
            public void update(int position) {
                super.update(position);
                volume = (Volume) objectList.get(position);
                tv.setText("Volume "+volume.getName());
            }
        }

        public  class IssueHolder extends MyAdapter.ItemViewHolder {
            Issue issue;

            @BindView(R.id.tvIssue)
            public TextView tvIssue;
            @BindView(R.id.tvDate)
            public TextView tvDate;
            public IssueHolder() {
                super(EditionAdapter.this,R.layout.item_issue);
            }

            @Override
            public void update(int position) {
                super.update(position);
                issue = (Issue) objectList.get(position);
                tvIssue.setText("Issue "+issue.getNumber());
                tvDate.setText(new DateTime(issue.getDate(),DateTime.SERVER_DATE).getDisplayMonthYear());
            }

            @OnClick(R.id.llContainer)
            public void onClickIssue(){
                PinEdition.this.listener.onSelectIssue(issue);
            }
        }
    }


/*

    @OnClick(R.id.btnSubmit)
    public void onClickSubmit() {
        listener.onSubmitGuestCount();
        dismiss();
    }
*/

}