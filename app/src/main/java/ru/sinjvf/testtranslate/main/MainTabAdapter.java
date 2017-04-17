package ru.sinjvf.testtranslate.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;

import ru.sinjvf.testtranslate.main.pages.SuperPageFragment;

/**
 * Created by Sinjvf on 17.04.2017.
 * Adapter for tabs\pages selection
 */

public class MainTabAdapter extends FragmentPagerAdapter {
    private HashMap<Integer, SuperPageFragment> pagersData;



    public MainTabAdapter(FragmentManager fm,  HashMap<Integer, SuperPageFragment> pagersData) {
        super(fm);
        this.pagersData = pagersData;
    }

    @Override
    public Fragment getItem(int position) {
        return pagersData.get(position);
    }

    @Override
    public int getCount() {
        return pagersData.size();
    }
}
