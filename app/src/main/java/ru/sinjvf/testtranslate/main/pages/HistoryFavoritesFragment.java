package ru.sinjvf.testtranslate.main.pages;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import ru.sinjvf.testtranslate.R;
import ru.sinjvf.testtranslate.data.SingleTranslation;


/**
 * Created by Sinjvf on 18.04.2017.
 * super fragment for fragment with favorite translations or with history
 */

abstract public class HistoryFavoritesFragment<P extends HistoryFavoritesPresenter> extends SuperPageFragment<HistoryFavoritesView, P> implements HistoryFavoritesView {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search)
    SearchView searchView;
    private HistoryFavoritesAdapter adapter;

    //recyclerView initianalisation
    @Override
    public void init() {
        super.init();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.searchChange(newText);
                return false;
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HistoryFavoritesAdapter(presenter);
        adapter.setList(presenter.getList());
        recyclerView.setAdapter(adapter);
        //show license text only when we have translations
        licenseView.setVisibility((adapter.getItemCount()==0)?View.GONE:View.VISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fr_history_favorites;
    }


    @Override
    public void deleteItem(int position) {
        adapter.deleteItem(position);
    }

    @Override
    public void setList(List<SingleTranslation> list) {
        if(adapter!=null) {
            adapter.setList(list);
            //show license text only when we have translations
            licenseView.setVisibility((adapter.getItemCount()==0)?View.GONE:View.VISIBLE);
        }
    }
}
