package ru.sinjvf.testtranslate.main.pages;

import ru.sinjvf.testtranslate.R;


/**
 * Created by Sinjvf on 17.04.2017.
 * fragment with favorite translations
 */

public class FavoritesFragment extends HistoryFavoritesFragment<FavoritesPresenter> {

    @Override
    protected int getIconId(){
        return R.drawable.ic_favorite;
    }

    @Override
    protected int getTitleId(){
        return R.string.title_favorites;
    }


    public static FavoritesFragment getInstance(){
        return new FavoritesFragment();
    }

    @Override
    public FavoritesPresenter createPresenter() {
        return new FavoritesPresenter();
    }

}
