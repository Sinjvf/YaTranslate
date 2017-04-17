package ru.sinjvf.testtranslate.main;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.HashMap;

import ru.sinjvf.testtranslate.main.pages.SuperPageFragment;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public interface MainView extends MvpView {
    void setToolbarText(String text);

    void initialization();
    void initTabs(HashMap<Integer, Drawable> tabsData);
    void initPages(HashMap<Integer, SuperPageFragment> pagersData);
    Resources getResources();

}
