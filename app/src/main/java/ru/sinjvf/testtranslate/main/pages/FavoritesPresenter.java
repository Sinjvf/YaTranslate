package ru.sinjvf.testtranslate.main.pages;

import java.util.List;

import ru.sinjvf.testtranslate.data.DaoSession;
import ru.sinjvf.testtranslate.data.SingleTranslation;
import ru.sinjvf.testtranslate.utils.TranslateUtils;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class FavoritesPresenter extends SuperPagePresenter<FavoritesView> {
    private DaoSession daoSession;
    @Override
    public void attachView(FavoritesView view) {
        super.attachView(view);
        daoSession = getView().getApp().getDaoSession();
    }

    public List<SingleTranslation> getList(){
        return TranslateUtils.getFavorites(daoSession);
    }
}
