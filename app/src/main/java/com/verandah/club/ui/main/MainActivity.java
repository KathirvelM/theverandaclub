package com.verandah.club.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.JsonObject;
import com.verandah.club.PresenterInterface;
import com.verandah.club.R;
import com.verandah.club.base.BaseActivity;
import com.verandah.club.data.model.home.Issue;
import com.verandah.club.data.model.home.MainResponse;
import com.verandah.club.data.rest.ApiClient;
import com.verandah.club.data.rest.ApiInterface;
import com.verandah.club.ui.category.CategoryActivity;
import com.verandah.club.ui.dialog.DialogExit;
import com.verandah.club.ui.dialog.PinEdition;
import com.verandah.club.ui.main.mvp.MainContractor;
import com.verandah.club.ui.main.mvp.MainPresenter;
import com.verandah.club.utils.DateTime;
import com.verandah.club.utils.ResourceUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements MainContractor.View {

    BottomTabs.BackEnableListener backEnableListener;
    @BindView(R.id.llTab)
    LinearLayout llTab;
    @BindView(R.id.flSearch)
    LinearLayout flSearch;
    @BindView(R.id.tvMonth)
    TextView tvMonth;
    @BindView(R.id.tvDate)
    TextView tvDate;
    PinEdition pinEdition;
    Issue issue;
    BottomTabs bottomTabs;
    MainPresenter mainPresenter;
    String issueId = "";
    InterstitialAd mInterstitialAd;
    /*    @OnClick(R.id.flCategories)
        public void onClickCategories(){
            SheetRestaurantCourse.newInstance(item -> {
                changeActivity(CategoryActivity.createIntent(this,issueId,String.valueOf(item.getId())));
            },mainResponse.getCategoryList()).show(getSupportFragmentManager(), "course");
        }*/
    Fragment currentFragment;
    MainResponse mainResponse;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiInterface apiInterface;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                currentFragment = replaceFragment(getSupportFragmentManager(), R.id.flContainer, AboutFragment.newInstance(getString(R.string.screen_about)), ResourceUtils.getString(R.string.tab_about));
                setTitle(ResourceUtils.getString(R.string.tab_about));
                toolbar.setVisibility(View.VISIBLE);
                bottomTabs.selectTab(null);
                return true;
            case R.id.terms:
                currentFragment = replaceFragment(getSupportFragmentManager(), R.id.flContainer, AboutFragment.newInstance(getString(R.string.screen_tc)), ResourceUtils.getString(R.string.tab_about));
                setTitle(ResourceUtils.getString(R.string.tab_about));
                toolbar.setVisibility(View.VISIBLE);
                bottomTabs.selectTab(null);
                return true;
            case R.id.privacy:
                currentFragment = replaceFragment(getSupportFragmentManager(), R.id.flContainer, AboutFragment.newInstance(getString(R.string.screen_privacy)), ResourceUtils.getString(R.string.tab_about));
                setTitle(ResourceUtils.getString(R.string.tab_about));
                toolbar.setVisibility(View.VISIBLE);
                bottomTabs.selectTab(null);
                return true;

            case R.id.contactus:
                currentFragment = replaceFragment(getSupportFragmentManager(), R.id.flContainer, ContactusFragment.newInstance(), ResourceUtils.getString(R.string.tab_about));
                setTitle(ResourceUtils.getString(R.string.tab_about));
                toolbar.setVisibility(View.VISIBLE);
                bottomTabs.selectTab(null);
                return true;

            case R.id.subcription:
                open();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @OnClick(R.id.flSearch)
    public void onClickSearch() {
        pinEdition = PinEdition.newInstance(this, flSearch, (Issue issue) -> {
            mainPresenter.requestMain(String.valueOf(issue.getId()));
            setIssue(issue);
            issueId = String.valueOf(issue.getId());
        }, mainResponse.getVolumeList());
        pinEdition.show();
    }

    void setIssue(Issue issue) {
        this.issue = issue;
        tvMonth.setText(new DateTime(issue.getDate()).getDisplayMonth());
        tvDate.setText(new DateTime(issue.getDate()).getDisplayDateOnly());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        apiInterface = ApiClient.getApiInterface();
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        layoutParams.setMargins(0, getStatusBarHeight(), 0, 0);
        toolbar.setLayoutParams(layoutParams);

        mainPresenter = new MainPresenter(this, appRepository);
        mainPresenter.requestMain(issueId);
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    void openDashboardFragment() {
        currentFragment = replaceFragment(getSupportFragmentManager(), R.id.flContainer, HomeFragment.newInstance(mainResponse), ResourceUtils.getString(R.string.tab_home));
        bottomTabs.selectTab(findViewById(R.id.flHome));
        //  toolbar.setVisibility(View.GONE);
    }

    public void onBackPressed() {
        if (!(currentFragment instanceof HomeFragment)) {
            openDashboardFragment();
        } else {
            DialogExit.newInstance(this).show();
            // super.onBackPressed();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public PresenterInterface getPresenterInterface() {
        return null;
    }

    public void onClick(View view) {
        pinEdition.dismiss();
    }

    @Override
    public void load(MainResponse mainResponse) {

        try {
            setIssue(issue != null ? issue : mainResponse.getVolumeList().get(0).getIssueList().get(0));
        } catch (Exception e) {
            //igonre
        }

        if (pinEdition != null && pinEdition.isShowing()) {
            pinEdition.dismiss();
        }

        this.mainResponse = mainResponse;

        bottomTabs = new BottomTabs(this, llTab, selectedId -> {
            switch (selectedId) {
                case R.id.flAbout:
                    currentFragment = replaceFragment(getSupportFragmentManager(), R.id.flContainer, AboutFragment.newInstance(getString(R.string.screen_about)), ResourceUtils.getString(R.string.tab_favourite));
                    setTitle(ResourceUtils.getString(R.string.tab_favourite));
                    toolbar.setVisibility(View.VISIBLE);
                    bottomTabs.selectTab(findViewById(R.id.flAbout));
                    break;
                case R.id.flSearch:
                    /*currentFragment = replaceFragment(getSupportFragmentManager(), R.id.flContainer, StoreFragment.newInstance(), ResourceUtils.getString(R.string.tab_search));
                    setTitle(ResourceUtils.getString(R.string.tab_search));
                    toolbar.setVisibility(View.VISIBLE);*/
                    break;
                case R.id.flHome:
                    openDashboardFragment();
                    break;
                case R.id.flStore:
                    currentFragment = replaceFragment(getSupportFragmentManager(), R.id.flContainer, StoreFragment.newInstance(), ResourceUtils.getString(R.string.tab_store));
                    setTitle(ResourceUtils.getString(R.string.tab_store));
                    toolbar.setVisibility(View.VISIBLE);
                    bottomTabs.selectTab(findViewById(R.id.flStore));
                    break;
                case R.id.flCategories:
                    currentFragment = replaceFragment(getSupportFragmentManager(), R.id.flContainer, CategoriesFragment.newInstance((name, categories, item) -> {

                        changeActivity(CategoryActivity.createIntent(this, name, issueId, categories, item));

                    }, mainResponse.getCategoryList()), ResourceUtils.getString(R.string.tab_categories));
                    setTitle(ResourceUtils.getString(R.string.tab_categories));
                    toolbar.setVisibility(View.VISIBLE);
                    bottomTabs.selectTab(findViewById(R.id.flCategories));
                    break;
            }
        }, backEnableListener = enable -> {
            Log.d("Back", String.valueOf(enable));
        });

        openDashboardFragment();
        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        //    mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });


        bottomTabs.selectTab(findViewById(R.id.flHome));
    }



    private void sendSubscriberemail(String email) {
        showLoadingView();

        disposable.add(apiInterface
                .subscriberEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<JsonObject>() {
                    @Override
                    public void onSuccess(@NonNull JsonObject jsonObject) {
                        // if (jsonObject.get("success").toString().equals("1")) {
                        showToast(jsonObject.get("message").toString());
                        //  } else {
                        //      showToast("Something went wrong. please try again");
                        //  }
                        hideLoadingView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoadingView();
                    }
                }));
    }

    private void open() {
        //     EditText editTextUsername = null;
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        final EditText editTextUsername = view.findViewById(R.id.edit_username);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setTitle("News Letter")
                .setPositiveButton("Subscribe", null) //Set to null. We override the onclick
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        EditText finalEditTextUsername = editTextUsername;
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        String username = editTextUsername.getText().toString();

                        if (Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                            sendSubscriberemail(username);
                            dialog.dismiss();
                        } else {
                            showToast("Enter Valid email address");
                        }
                    }
                });
            }
        });

        dialog.show();
    }
}