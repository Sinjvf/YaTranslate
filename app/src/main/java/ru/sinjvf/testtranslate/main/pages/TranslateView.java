package ru.sinjvf.testtranslate.main.pages;

import java.util.List;

import ru.sinjvf.testtranslate.data.SingleTranslation;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public interface TranslateView extends SuperPageView {
    void updateSpinner(boolean isFromSpinner, List<String> langsList,  String defaultLang);
    void setTranslation(SingleTranslation translation, String langName);

}
