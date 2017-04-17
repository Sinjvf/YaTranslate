package ru.sinjvf.testtranslate.main.pages;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public abstract class SuperPageFragment<V extends SuperPageView, P extends SuperPagePresenter<V>> extends MvpFragment<V, P> implements SuperPageView {

    protected final String TAG = "My_Tag:"+getClass().getSimpleName();
    protected Unbinder unbinder;
    protected CompositeSubscription subs = new CompositeSubscription();


    @Override
    public Drawable getPict(Resources res) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return res.getDrawable(getIconId(), null);
        } else {
            return res.getDrawable(getIconId());
        }
    }


    @Override
    public String getTitle(Resources res) {
        return res.getString(getTitleId());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
        if (subs != null)
            subs.unsubscribe();
    }


    protected abstract int getIconId();
    protected abstract int getTitleId();
    protected abstract int getLayoutId();
    public abstract void init();
}
