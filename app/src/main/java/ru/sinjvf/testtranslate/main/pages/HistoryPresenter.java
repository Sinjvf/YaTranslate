package ru.sinjvf.testtranslate.main.pages;

import android.util.Log;

import java.util.List;

import ru.sinjvf.testtranslate.data.SingleTranslation;
import ru.sinjvf.testtranslate.utils.TranslateUtils;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class HistoryPresenter extends HistoryFavoritesPresenter {

    public List<SingleTranslation> getList(){
        Log.d(TAG, "getList: ");
        return TranslateUtils.getHistory(daoSession);
    }

    @Override
    public List<SingleTranslation> getFilteredList(String str) {
        Log.d(TAG, "getFilteredList: ");
        return TranslateUtils.getHistoryFiltred(str, daoSession);
    }

    @Override
    public void clickFavorite(SingleTranslation translation, boolean ischecked, int position) {
        super.clickFavorite(translation, ischecked, position);
    }

}
