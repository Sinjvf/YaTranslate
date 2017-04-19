package ru.sinjvf.testtranslate.main.pages;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import ru.sinjvf.testtranslate.R;


/**
 * Created by Sinjvf on 17.04.2017.
 * fragment with favorite translations
 */

public class FavoritesFragment extends SuperPageFragment<HistoryFavoritesView, FavoritesPresenter> implements HistoryFavoritesView {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private HistoryFavoritesAdapter adapter;

    //recyclerView initianalisation
    @Override
    public void init() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HistoryFavoritesAdapter(presenter);
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


    @Override
    public void deleteItem(int position) {
        adapter.deleteItem(position);
    }
}
