package ru.sinjvf.testtranslate.main.pages;

import java.util.List;

import ru.sinjvf.testtranslate.data.Lang;
import ru.sinjvf.testtranslate.data.SingleTranslation;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public interface TranslateView extends SuperPageView {
    String updateSpinner(boolean isFromSpinner, List<Lang> langsList, String defaultLang);
    void setTranslation(SingleTranslation translation, String langName);
    void setDetectedLang(String langName, String langDesc);

}
