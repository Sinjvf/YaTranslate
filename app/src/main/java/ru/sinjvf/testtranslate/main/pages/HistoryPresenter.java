package ru.sinjvf.testtranslate.main.pages;

import java.util.ArrayList;
import java.util.List;

import ru.sinjvf.testtranslate.data.SingleTranslation;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class HistoryPresenter extends SuperPagePresenter<HistoryView> {

    public List<SingleTranslation> getList(){
        return new ArrayList<>();
    }
}
