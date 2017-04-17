package ru.sinjvf.testtranslate.main.pages;

import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import ru.sinjvf.testtranslate.R;


/**
 * Created by Sinjvf on 17.04.2017.
 */

public class FavoritesFragment extends SuperPageFragment<FavoritesView, FavoritesPresenter> implements FavoritesView {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private FavoritesAdapter adapter;

    @Override
    public void init() {
        adapter = new FavoritesAdapter();
        adapter.setList(presenter.getList());
        recyclerView.setAdapter(adapter);
        //recyclerView
    }

    @Override
    protected int getIconId(){
        return R.drawable.ic_favorite;
    }

    @Override
    protected int getTitleId(){
        return R.string.title_favorites;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fr_favorites;
    }


    public static FavoritesFragment getInstance(){
        return new FavoritesFragment();
    }

    @Override
    public FavoritesPresenter createPresenter() {
        return new FavoritesPresenter();
    }


}
