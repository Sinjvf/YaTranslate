package ru.sinjvf.testtranslate.main.pages;

import ru.sinjvf.testtranslate.R;


/**
 * Created by Sinjvf on 17.04.2017.
 */

public class TranslateFragment extends SuperPageFragment<TranslateView, TranslatePresenter> implements TranslateView {

    @Override
    protected int getIconId(){
        return R.drawable.ic_translate;
    }
    @Override
    protected int getTitleId(){
        return R.string.title_translate;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fr_translate;
    }

    public static TranslateFragment getInstance(){
        return new TranslateFragment();
    }



    @Override
    public TranslatePresenter createPresenter() {
        return new TranslatePresenter();
    }


}
