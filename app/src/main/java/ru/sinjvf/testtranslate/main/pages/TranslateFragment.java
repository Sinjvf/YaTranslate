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
    @BindView(R.id.lang)
    TextView lang;

    @Override
    public void setLang(String str) {
        lang.setText(str);
    }

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
        initListeners();
    }


    @Override
    public void updateToSpinner(List<String> langsTo, String defaultTo) {
        updateOneSpinner(toLangView, langsTo, defaultTo);
    }
    @Override
    public void updateFromSpinner(List<String> langsTo, String defaultTo) {
        updateOneSpinner(fromLangView, langsTo, defaultTo);
    }

    public void updateOneSpinner(Spinner spinner, List<String> langs, String defaultStr) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, langs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        try {
            int spinnerPosition = adapter.getPosition(defaultStr);
            spinner.setSelection(spinnerPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initSelectorListeners() {
        subs.add(RxAdapterView.itemSelections(fromLangView)
                .subscribe((event) -> presenter.fromLangChanged((String) fromLangView.getSelectedItem())));
        subs.add(RxAdapterView.itemSelections(toLangView)
                .subscribe((event) -> presenter.toLangChanged((String) toLangView.getSelectedItem())));
    }

    private void initListeners() {
        initSelectorListeners();
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
