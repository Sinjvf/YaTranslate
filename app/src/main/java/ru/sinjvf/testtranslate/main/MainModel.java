package ru.sinjvf.testtranslate.main;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;

import ru.sinjvf.testtranslate.main.pages.FavoritesFragment;
import ru.sinjvf.testtranslate.main.pages.HistoryFragment;
import ru.sinjvf.testtranslate.main.pages.SuperPageFragment;
import ru.sinjvf.testtranslate.main.pages.TranslateFragment;

/**
 * Created by Sinjvf on 17.04.2017.
 * Model for main activity - contains fragments and tab init
 */

public class MainModel {


    private HashMap<Integer, Drawable> tabs = new HashMap<>();
    private HashMap<Integer, SuperPageFragment> fragments = new HashMap<>();

    public HashMap<Integer, Drawable> getTabs() {
        return tabs;
    }
    public HashMap<Integer, SuperPageFragment> getFragments() {
        return fragments;
    }

    public void initTabsAndPages(Resources resources) {
        fragments.put(0, TranslateFragment.getInstance());
        fragments.put(1, HistoryFragment.getInstance());
        fragments.put(2, FavoritesFragment.getInstance());

        for (Map.Entry<Integer, SuperPageFragment> entry : fragments.entrySet()){
            tabs.put(entry.getKey(), entry.getValue().getPict(resources));
        }
    }


}
