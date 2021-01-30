package com.verandah.club.ui.main;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import com.verandah.club.PresenterInterface;
import com.verandah.club.ui.dialog.DialogExit;
import com.verandah.club.ui.dialog.PinEdition;
import com.verandah.club.ui.main.mvp.MainContractor;
import com.verandah.club.ui.main.mvp.MainPresenter;

import com.verandah.club.base.BaseActivity;
import com.verandah.club.data.model.home.Issue;
import com.verandah.club.data.model.home.MainResponse;
import com.verandah.club.ui.category.CategoryActivity;
import com.verandah.club.utils.DateTime;
import com.verandah.club.utils.ResourceUtils;
import com.verandah.club.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContractor.View {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                currentFragment = replaceFragment(getSupportFragmentManager(), R.id.flContainer, AboutFragment.newInstance(), ResourceUtils.getString(R.string.tab_about));
                setTitle(ResourceUtils.getString(R.string.tab_about));
                toolbar.setVisibility(View.VISIBLE);
                bottomTabs.selectTab(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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

    @OnClick(R.id.flSearch)
    public void onClickSearch(){
        pinEdition = PinEdition.newInstance(this,flSearch,(Issue issue) -> {
            mainPresenter.requestMain(String.valueOf(issue.getId()));
            setIssue(issue);
            issueId = String.valueOf(issue.getId());
        },mainResponse.getVolumeList());
        pinEdition.show();
    }

    Issue issue;

    void setIssue(Issue issue){
        this.issue=issue;
        tvMonth.setText(new DateTime(issue.getDate()).getDisplayMonth());
        tvDate.setText(new DateTime(issue.getDate()).getDisplayDateOnly());
    }

/*    @OnClick(R.id.flCategories)
    public void onClickCategories(){
        SheetRestaurantCourse.newInstance(item -> {
            changeActivity(CategoryActivity.createIntent(this,issueId,String.valueOf(item.getId())));
        },mainResponse.getCategoryList()).show(getSupportFragmentManager(), "course");
    }*/

    BottomTabs bottomTabs;

    MainPresenter mainPresenter;


    String issueId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        layoutParams.setMargins(0, getStatusBarHeight(), 0, 0);
        toolbar.setLayoutParams(layoutParams);

        mainPresenter = new MainPresenter(this,appRepository);
        mainPresenter.requestMain(issueId);
    }

    InterstitialAd mInterstitialAd;

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
    
    Fragment currentFragment;

    public void onBackPressed() {
        if (!(currentFragment instanceof HomeFragment)) {
            openDashboardFragment();
        }else{
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


    MainResponse mainResponse;

    @Override
    public void load(MainResponse mainResponse) {

        try {
            setIssue( issue!=null ? issue : mainResponse.getVolumeList().get(0).getIssueList().get(0));
        }catch (Exception e){
            //igonre
        }

        if(pinEdition!=null && pinEdition.isShowing()){
            pinEdition.dismiss();
        }

        this.mainResponse = mainResponse;

        bottomTabs = new BottomTabs(this, llTab, selectedId -> {
            switch (selectedId) {
                case R.id.flAbout:
                    currentFragment = replaceFragment(getSupportFragmentManager(), R.id.flContainer, FavouriteFragment.newInstance(), ResourceUtils.getString(R.string.tab_favourite));
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

                        changeActivity(CategoryActivity.createIntent(this,name,issueId,categories, item));

                    },mainResponse.getCategoryList()), ResourceUtils.getString(R.string.tab_categories));
                    setTitle(ResourceUtils.getString(R.string.tab_categories));
                    toolbar.setVisibility(View.VISIBLE);
                    bottomTabs.selectTab(findViewById(R.id.flCategories));
                    break;
            }
        }, backEnableListener = enable -> {
            Log.d("Back",String.valueOf(enable));
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
}