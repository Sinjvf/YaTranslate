package ru.sinjvf.testtranslate.main;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.sinjvf.testtranslate.R;
import ru.sinjvf.testtranslate.main.pages.SuperPageFragment;

/**
 * Created by Sinjvf on 17.04.2017.
 * Main activity - contains viewPager with tabs
 */

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView {

    private final String TAG = "My_Tag:" + getClass().getSimpleName();
    //init views
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindColor(android.R.color.black)
    int iconColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");
    }

    public void initToolbar() {
        setSupportActionBar(toolbar);
    }

    //to changing toolbar text
    @Override
    public void setToolbarText(@NotNull String text) {
        ActionBar actionbar = getSupportActionBar();
        if (actionbar == null) return;
        actionbar.setTitle(text);
        Log.d(TAG, "setToolbarText: " + text);
    }



    //set icons on tabs
    @Override
    public void initTabs(HashMap<Integer, Drawable> tabsData) {
        if (tabsData == null) return;

        for (Map.Entry<Integer, Drawable> item : tabsData.entrySet()) {
            TabLayout.Tab tab = tabLayout.getTabAt(item.getKey());
            Drawable icon = item.getValue();
            icon.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
            tab.setIcon(icon);
        }


    }

    //set ViewPager data
    //change title when page in viewPager changes
    //init when page change - because history and favorites pages affect each other
    @Override
    public void initPages(HashMap<Integer, SuperPageFragment> pagersData) {
        if (pagersData == null) return;
        MainTabAdapter pagerAdapter = new MainTabAdapter(getSupportFragmentManager(), pagersData);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "change page");
                presenter.setTitle(position);
                ((SuperPageFragment) pagerAdapter.getItem(position)).init();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

}
