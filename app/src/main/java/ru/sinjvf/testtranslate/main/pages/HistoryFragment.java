package ru.sinjvf.testtranslate.main.pages;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import butterknife.BindView;
import ru.sinjvf.testtranslate.R;


/**
 * Created by Sinjvf on 17.04.2017.
 * Fragment with translations history
 */

public class HistoryFragment extends SuperPageFragment<HistoryFavoritesView, HistoryPresenter> implements HistoryFavoritesView {
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
        Log.d(TAG, "init: "+presenter.getList().size());
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

    @Override
    public void deleteItem(int position) {
        adapter.deleteItem(position);
    }
}
