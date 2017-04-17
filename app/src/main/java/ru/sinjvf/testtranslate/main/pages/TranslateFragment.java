package ru.sinjvf.testtranslate.main.pages;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.List;

import butterknife.BindView;
import ru.sinjvf.testtranslate.R;


/**
 * Created by Sinjvf on 17.04.2017.
 */

public class TranslateFragment extends SuperPageFragment<TranslateView, TranslatePresenter> implements TranslateView {
    @BindView(R.id.from_language)
    Spinner fromLangView;
    @BindView(R.id.to_language)
    Spinner toLangView;
    @BindView(R.id.text_to_translate)
    EditText textToTranslateView;
    @BindView(R.id.clear_text)
    ImageView clearTextView;
    @BindView(R.id.main_translation)
    TextView mainTranslationView;
    @BindView(R.id.main_word)
    TextView mainWordView;
    @BindView(R.id.add_to_favorite)
    ImageView addToFavorite;


    @Override
    protected int getIconId() {
        return R.drawable.ic_translate;
    }

    @Override
    protected int getTitleId() {
        return R.string.title_translate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fr_translate;
    }

    @Override
    public void init() {
        initSpinners();
        initListeners();
    }

    private void initSpinners() {
        List<String> languages = presenter.getLangList();
        ArrayAdapter adapter = new ArrayAdapter<>(getView().getContext(), android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromLangView.setAdapter(adapter);
        toLangView.setAdapter(adapter);
        if (languages != null) {
            try {
                fromLangView.setSelection(presenter.getDefautlFromLang());
                toLangView.setSelection(presenter.getDefautlToLang());
            } catch (Exception e) {
                //may be null pointer or index out of bounds
                e.printStackTrace();
            }
        }
        subs.add(RxAdapterView.selectionEvents(fromLangView)
                .skip(1)
                .subscribe((event) -> presenter.fromLangChanged((String) fromLangView.getSelectedItem()))
        );
        subs.add(RxAdapterView.selectionEvents(toLangView)
                .skip(1)
                .subscribe((event) -> presenter.toLangChanged((String) toLangView.getSelectedItem()))
        );
    }

    private void initListeners() {
        subs.add(RxTextView.editorActions(textToTranslateView)
                .subscribe((event) -> presenter.setNewText(textToTranslateView.getText().toString())));
        subs.add(RxView.clicks(clearTextView)
                .subscribe((event) -> clearText()));
    }

    private void clearText() {
        textToTranslateView.setText("");
    }

    public static TranslateFragment getInstance() {
        return new TranslateFragment();
    }


    @Override
    public TranslatePresenter createPresenter() {
        return new TranslatePresenter();
    }


}
