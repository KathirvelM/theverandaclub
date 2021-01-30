package com.verandah.club.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.verandah.club.FragmentInterface;
import com.verandah.club.data.source.AppRepository;
import com.theartofdev.edmodo.cropper.CropImageView;

import com.verandah.club.ui.dialog.DialogConnectionErrorRetry;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment implements FragmentInterface {

    protected Context context;
    Unbinder unbinder;
    protected AppRepository appRepository;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        View view = provideYourFragmentView(inflater, parent, savedInstanseState);
        context = this.getActivity();
        unbinder = ButterKnife.bind(this, view);
        appRepository = BaseApplication.getAppRepository();
        return view;
    }

    public abstract View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

    public void showLoadingView() {
        if(getActivity()!=null)
        ((BaseActivity)getActivity()).showLoadingView();
    }

    public void hideLoadingView() {
        if(getActivity()!=null)
        ((BaseActivity)getActivity()).hideLoadingView();
    }

    public void showLoadingView(int requestId) {
        if(getActivity()!=null)
            ((BaseActivity)getActivity()).showLoadingView(requestId);
    }

    public void hideLoadingView(int requestId) {
        if(getActivity()!=null)
            ((BaseActivity)getActivity()).hideLoadingView(requestId);
    }

    public void hideLoadingView(int requestId, boolean force) {
        if(getActivity()!=null)
            ((BaseActivity)getActivity()).hideLoadingView(requestId,force);
    }

    public void showWarning(String message){
       BaseActivity baseActivity = ((BaseActivity)getActivity());
       if(baseActivity!=null)baseActivity.showWarning(message);
    }

    public void showError(String message){
       BaseActivity baseActivity = ((BaseActivity)getActivity());
       if(baseActivity!=null)baseActivity.showError(message);
    }

    public void showConnectionError(String message){
        BaseActivity baseActivity = ((BaseActivity)getActivity());
        if(baseActivity!=null)baseActivity.showConnectionError(message);
    }


    @Override
    public void showSuccess(String title, String message, boolean isFinish) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showSuccess(title,message,isFinish);
    }

    public void showWarning(String message, boolean isFinish) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showWarning(message,isFinish);
    }

    public void showError(String message, boolean isFinish) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showError(message,isFinish);
    }

    public void showConnectionError(String message, boolean isFinish) {
        BaseActivity baseActivity = ((BaseActivity) getActivity());
        if (baseActivity != null) baseActivity.showConnectionError(message,isFinish);
    }

    public void showTokenExpired(String message){
        BaseActivity baseActivity = ((BaseActivity)getActivity());
        if(baseActivity!=null)baseActivity.showTokenExpired(message);
    }

    @Override
    public void pickImage(int requestCode, CropImageView.CropShape cropShape, int ratioX, int ratioY) {
        BaseActivity baseActivity = ((BaseActivity)getActivity());
        if(baseActivity!=null)baseActivity.pickImage(requestCode,
                cropShape,
                ratioX,
                ratioY);
    }

    public void showConnectionErrorRetry(String message, DialogConnectionErrorRetry.RetryListener retryListener){
        BaseActivity baseActivity = ((BaseActivity)getActivity());
        if(baseActivity!=null)baseActivity.showConnectionErrorRetry(message, retryListener);
    }

    public void showToast(int message) {
        showToast(getResources().getString(message));
    }

    public void showToast(String message) {
        BaseActivity baseActivity = ((BaseActivity)getActivity());
        if(baseActivity!=null)baseActivity.showToast(message);
    }



    public void changeActivity(Intent i) {
        startActivity(i);
    }

    public void changeActivityForResult(Intent i, int reqCode) {
        startActivityForResult(i, reqCode);
    }

    public void changeActivity(Class clz) {
        Intent i = new Intent(getContext(), clz);
        changeActivity(i);
    }

    public void changeActivityClearBackStack(Class clz) {
        Intent i = new Intent(getContext(), clz);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        changeActivity(i);
    }

    @Override
    public void onDestroyView() {
        if(getPresenterInterface()!=null)getPresenterInterface().close();
        super.onDestroyView();
        if(unbinder!=null)unbinder.unbind();
    }

    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public List<Fragment> getVisibleFragments() {
        return getChildFragmentManager().getFragments();
    }

    @Override
    public void onImageChosen(int requestCode, Uri uri) {
        for(Fragment fragment: getVisibleFragments()){
            if(fragment instanceof  BaseFragment){
                ((BaseFragment)fragment).onImageChosen(requestCode,uri);
            }
        }
    }


//     public setDatePicker(){
//
//         DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.datepickerCustom, new DatePickerDialog.OnDateSetListener() {
//
//             @Override
//             public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                 try {
//                     String dateofPurchase = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
//                     Date date = simpleDateFormat.parse(dateofPurchase);
//                     etDateOfPurchase.setText(simpleDateFormat.format(date));
//                     pYear = year;
//                     pMonth = monthOfYear;
//                     pDate = dayOfMonth + 1;
//                 } catch (ParseException exception) {
//                     Log.e(EXCEPTION_PARSE, exception.toString());
//                 }
//
//
//             }
//         }, year, month, date);
//         datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
//         datePickerDialog.show();
//     }
}