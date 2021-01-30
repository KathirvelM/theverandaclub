package com.verandah.club.ui.category;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import com.verandah.club.PresenterInterface;
import com.verandah.club.base.BaseActivity;
import com.verandah.club.data.model.home.Category;
import com.verandah.club.ui.main.MainActivity;

import com.verandah.club.R;

import java.util.List;

import butterknife.BindView;

public class CategoryActivity extends BaseActivity {

    DemoCollectionPagerAdapter demoCollectionPagerAdapter;

    @BindView(R.id.vp)
    ViewPager vp;

    @BindView(R.id.tl)
    TabLayout tl;

    static String s_name;
    static String s_issueId;
    static List<Category> s_categories;
    static Category s_category;

    String name;
    String issueId;
    List<Category> categories;
    Category category;

    public static Intent createIntent(MainActivity mainActivity, String name, String issueId, List<Category> categories, Category category) {
        Intent intent = new Intent(mainActivity,CategoryActivity.class);
        CategoryActivity.s_name = name;
        CategoryActivity.s_issueId = issueId;
        CategoryActivity.s_categories = categories;
        CategoryActivity.s_category = category;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar();

        name = s_name;
        issueId = s_issueId;
        categories = s_categories;
        category = s_category;

        setTitle(name);

        tl.setupWithViewPager(vp);

        demoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(demoCollectionPagerAdapter);

        vp.setCurrentItem(categories.indexOf(category),false);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_category;
    }

    @Override
    public PresenterInterface getPresenterInterface() {
        return null;
    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return CategoryFragment.newInstance(issueId,String.valueOf(categories.get(i).getId()));
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categories.get(position).getName();
        }
    }

}