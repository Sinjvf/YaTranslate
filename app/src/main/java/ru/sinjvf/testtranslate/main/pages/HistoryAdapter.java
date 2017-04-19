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
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.SimpleViewHolder> {



    private List<SingleTranslation> list;

    @Override
    public HistoryAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        layoutId = R.layout.rec_it_favorite ;
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new SimpleViewHolder(v);
    }



    @Override
    public void onBindViewHolder(HistoryAdapter.SimpleViewHolder holder, int position) {
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

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView iconView;
        @BindView(R.id.language)
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

        public void bind(int pos) {
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
