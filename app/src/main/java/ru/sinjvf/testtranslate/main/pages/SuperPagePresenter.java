package ru.sinjvf.testtranslate.main.pages;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class SuperPagePresenter<V extends SuperPageView> extends MvpBasePresenter<V> {

    protected final String TAG = "My_Tag:"+getClass().getSimpleName();
    @Override
    public void attachView(V view) {
        super.attachView(view);
        if (isViewAttached()) {
         //   getView().init();
        }
    }


}
