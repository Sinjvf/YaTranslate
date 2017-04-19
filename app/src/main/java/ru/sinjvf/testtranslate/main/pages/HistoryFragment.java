package ru.sinjvf.testtranslate.main.pages;

import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import ru.sinjvf.testtranslate.R;


/**
 * Created by Sinjvf on 17.04.2017.
 * Fragment with translations history
 */

public class HistoryFragment extends SuperPageFragment<HistoryView, HistoryPresenter> implements HistoryView {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private FavoritesAdapter adapter;


    //recyclerView initianalisation
    @Override
    public void init() {
        adapter = new FavoritesAdapter();
        adapter.setList(presenter.getList());
        recyclerView.setAdapter(adapter);
    }
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
