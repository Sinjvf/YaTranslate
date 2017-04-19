package ru.sinjvf.testtranslate.main.pages;

import android.util.Log;

import java.util.List;

import ru.sinjvf.testtranslate.data.SingleTranslation;
import ru.sinjvf.testtranslate.utils.TranslateUtils;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class FavoritesPresenter extends HistoryFavoritesPresenter {

    public List<SingleTranslation> getList(){
        Log.d(TAG, "getList: ");
        return TranslateUtils.getFavorites(daoSession);
    }

    @Override
    public void clickFavorite(SingleTranslation translation, boolean ischecked, int position) {
        super.clickFavorite(translation, ischecked, position);
        //translations now not favorite remove from list
        if (!isViewAttached())return;
        getView().deleteItem(position);
    }


}
