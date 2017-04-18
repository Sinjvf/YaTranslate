package ru.sinjvf.testtranslate.main.pages;

import java.util.List;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public interface TranslateView extends SuperPageView {
    void updateFromSpinner(List<String> langsTo, String defaultTo);
    void updateToSpinner(List<String> langsTo,  String defaultTo);
    void setLang(String str);
}
