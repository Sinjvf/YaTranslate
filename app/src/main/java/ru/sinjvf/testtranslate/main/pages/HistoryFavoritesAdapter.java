package ru.sinjvf.testtranslate.main.pages;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.sinjvf.testtranslate.R;
import ru.sinjvf.testtranslate.data.SingleTranslation;


/**
 * Created by Sinjvf on 17.04.2017.
 * Recycler Adapter for fragment with favorite translations or with history
 */

public class HistoryFavoritesAdapter extends RecyclerView.Adapter<HistoryFavoritesAdapter.SimpleViewHolder> {
    private List<SingleTranslation> list;
    private HistoryFavoritesPresenter presenter;

    public HistoryFavoritesAdapter(HistoryFavoritesPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public HistoryFavoritesAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_it_favorite, parent, false);
        return new SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryFavoritesAdapter.SimpleViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<SingleTranslation> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        CheckBox iconView;
        @BindView(R.id.text)
        TextView mainWordView;
        @BindView(R.id.translation)
        TextView mainTranslationView;
        @BindView(R.id.lang_info)
        TextView langInfoView;
        @BindView(R.id.trash)
        ImageView trashView;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int pos) {
            try {
                SingleTranslation translation = list.get(pos);
                mainWordView.setText(translation.getText());
                mainTranslationView.setText(translation.getTranslationList().get(0).getText());
                langInfoView.setText(translation.getLang());
                iconView.setChecked(translation.getIsFavorite());

                RxCompoundButton.checkedChanges(iconView)
                        .skip(1)
                        .subscribe((aBoolean) -> presenter.clickFavorite(translation, aBoolean, getAdapterPosition()));
                RxView.clicks(trashView)
                        .subscribe((event) -> presenter.clickDelete(translation, getAdapterPosition()));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
