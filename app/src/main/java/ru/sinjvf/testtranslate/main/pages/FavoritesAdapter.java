package ru.sinjvf.testtranslate.main.pages;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.sinjvf.testtranslate.R;
import ru.sinjvf.testtranslate.data.SingleTranslation;


/**
 * Created by Sinjvf on 17.04.2017.
 * Fragment with favorite translations
 */

public class FavoritesAdapter extends  RecyclerView.Adapter<FavoritesAdapter.SimpleViewHolder> {
    private List<SingleTranslation> list;

    @Override
    public FavoritesAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_it_favorite, parent, false);
        return new SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.SimpleViewHolder holder, int position) {
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

    public class SimpleViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.icon)
        ImageView iconView;
        @BindView(R.id.main_word)
        TextView mainWordView;
        @BindView(R.id.main_translation)
        TextView mainTranslationView;
        @BindView(R.id.lang_info)
        TextView langInfoView;
        @BindView(R.id.trash)
        ImageView trashView;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void bind(int pos){
            try {
                SingleTranslation translation = list.get(pos);
                mainWordView.setText(translation.getText());
                mainTranslationView.setText(translation.getTranslationList().get(0).getText());
                langInfoView.setText(translation.getLang());
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }
}
