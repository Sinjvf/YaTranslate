package ru.sinjvf.testtranslate.main;

import android.content.res.Resources;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class MainPresenter extends MvpBasePresenter<MainView> {

    private final String TAG = "My_Tag:"+getClass().getSimpleName();
    private MainModel model = new MainModel();
    CompositeSubscription compositeSubscription ;

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        init();
    }

    //init model and set pages and tabs
    public void init(){
        if (isViewAttached()) {
            compositeSubscription = new CompositeSubscription();
            model.initTabsAndPages(getView().getResources());
            getView().initPages(model.getFragments());
            getView().initTabs(model.getTabs());
            setTitle(0);
        }
    }

    //change toolbar text
    public void setTitle(int position){
        if (isViewAttached()) {
            Resources res = getView().getResources();
            String title = model.getFragments().get(position).getTitle(res);
            getView().setToolbarText(title);
        }else return ;
    }

}
