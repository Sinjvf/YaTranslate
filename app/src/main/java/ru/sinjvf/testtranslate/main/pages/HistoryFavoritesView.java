package ru.sinjvf.testtranslate.main.pages;

import java.util.List;

import ru.sinjvf.testtranslate.data.SingleTranslation;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public interface HistoryFavoritesView extends SuperPageView {
    void deleteItem(int position);
    void setList(List<SingleTranslation> list);
}
