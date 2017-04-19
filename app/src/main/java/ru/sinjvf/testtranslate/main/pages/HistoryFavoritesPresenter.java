package ru.sinjvf.testtranslate.main.pages;

import android.util.Log;

import java.util.List;

import ru.sinjvf.testtranslate.data.DaoSession;
import ru.sinjvf.testtranslate.data.SingleTranslation;
import ru.sinjvf.testtranslate.utils.TranslateUtils;

/**
 * Created by Sinjvf on 18.04.2017.
 */

public abstract class HistoryFavoritesPresenter extends SuperPagePresenter<HistoryFavoritesView> {

    protected DaoSession daoSession;
    @Override
    public void attachView(HistoryFavoritesView view) {
        super.attachView(view);
        daoSession = getView().getApp().getDaoSession();
    }

    public abstract List<SingleTranslation> getList();
    public abstract List<SingleTranslation> getFilteredList(String str);

    public void clickFavorite(SingleTranslation translation, boolean ischecked, int position){
        Log.d(TAG, "clickFavorite: "+ischecked +", "+position);
        translation.setIsFavorite(ischecked);
        TranslateUtils.update(translation, daoSession);
    }

    public void clickDelete(SingleTranslation translation, int position){
        Log.d(TAG, "clickDelete: ");
        TranslateUtils.delete(translation, daoSession);
        if (!isViewAttached())return;
        getView().deleteItem(position);
    }

    public void searchChange(String str){
        if (!isViewAttached())return;
        getView().setList(getFilteredList(str));
    }
}
