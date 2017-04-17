package ru.sinjvf.testtranslate.main.pages;

import ru.sinjvf.testtranslate.R;


/**
 * Created by Sinjvf on 17.04.2017.
 */

public class HistoryFragment extends SuperPageFragment<HistoryView, HistoryPresenter> implements HistoryView {

    @Override
    protected int getIconId(){
        return R.drawable.ic_history;
    }
    @Override
    protected int getTitleId(){
        return R.string.title_history;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fr_history;
    }

    public static HistoryFragment getInstance(){
        return new HistoryFragment();
    }

    @Override
    public HistoryPresenter createPresenter() {
        return new HistoryPresenter();
    }


}
