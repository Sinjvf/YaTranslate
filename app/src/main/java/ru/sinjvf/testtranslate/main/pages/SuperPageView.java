package ru.sinjvf.testtranslate.main.pages;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public interface SuperPageView extends MvpView {
    String getTitle(Resources res);
    Drawable getPict(Resources res);
    void init();
    Resources getResources();
}
