package ru.sinjvf.testtranslate.main.pages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import ru.sinjvf.testtranslate.R;
import ru.sinjvf.testtranslate.data.Lang;
import ru.sinjvf.testtranslate.data.SingleTranslation;
import ru.sinjvf.testtranslate.data.TranslationText;


/**
 * Created by Sinjvf on 17.04.2017.
 * main fragment with translation form
 */

public class TranslateFragment extends SuperPageFragment<TranslateView, TranslatePresenter> implements TranslateView {
    //init views
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
    @BindView(R.id.language)
    TextView languageView;
    @BindView(R.id.swap_langs)
    ImageView swapLangView;
    @BindView(R.id.add_to_favorite)
    CheckBox addToFavorite;
    @BindView(R.id.minor_translations_container)
    LinearLayout trContainer;
    @BindView(R.id.translate_from)
    TextView detectLangView;

    @BindColor(android.R.color.black)
    int blackColor;


    private static final String TR_KEY = "trKey";
    private static final String LANG_KEY = "langKey";


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
        super.init();
        initListeners();
    }

    //get fragment instance
    public static TranslateFragment getInstance() {
        return new TranslateFragment();
    }

    //spinner with languages lists
    //isFromSpinner - first spinner (from language) or second (to language)
    //langsList - list of languages
    //defaultLang - default language
    @Override
    public String updateSpinner(boolean isFromSpinner, List<Lang> langsList, String defaultLang) {
        Spinner spinner = (isFromSpinner) ? fromLangView : toLangView;
        return updateOneSpinner(spinner, langsList, defaultLang);
    }

    //set adapter to spinner
    public String updateOneSpinner(Spinner spinner, List<Lang> langs, String defaultStr) {
        List<String> langsNames  = new ArrayList<>();
        for (Lang singleLang: langs){
            langsNames.add(singleLang.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, langsNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(defaultStr);
        if (spinnerPosition == -1) spinnerPosition = 0;
        Log.d(TAG, "updateOneSpinner: " + spinnerPosition);
        spinner.setSelection(spinnerPosition);
        return langs.get(spinnerPosition).getDesc();

    }


    //all events listeners
    private void initListeners() {

        //listeners of events in spinners
        subs.add(RxAdapterView.itemSelections(fromLangView)
                .skip(1)
                .subscribe((event) -> presenter.fromLangChanged((String) fromLangView.getSelectedItem())));
        subs.add(RxAdapterView.itemSelections(toLangView)
                .skip(1)
                .subscribe((event) -> presenter.toLangChanged((String) toLangView.getSelectedItem())));
        //click "done" in text for translation
        subs.add(RxTextView.editorActions(textToTranslateView)
                .subscribe((event) -> {
                    presenter.setNewText(textToTranslateView.getText().toString());
                    hideKeyboard();
                }));
        //click "clearText" button
        subs.add(RxView.clicks(clearTextView)
                .subscribe((event) -> clearText()));
        //click "favorite" star image
        subs.add(RxCompoundButton.checkedChanges(addToFavorite)
                .subscribe((aBoolean) -> presenter.favoriteClick(aBoolean)));
        //click "swap"
        subs.add(RxView.clicks(swapLangView)
                .subscribe((event) -> presenter.swapClick()));
    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //clear text in translation form and clear last translation views
    private void clearText() {
        textToTranslateView.setText("");
        setFieldsVisibility(false);
        detectLangView.setVisibility(View.GONE);
    }

    //set visibility of translation fields.
    //show = true - visible
    //show = false - gone
    private void setFieldsVisibility(boolean show) {
        languageView.setVisibility(show ? View.VISIBLE : View.GONE);
        mainTranslationView.setVisibility(show ? View.VISIBLE : View.GONE);
        addToFavorite.setVisibility(show ? View.VISIBLE : View.GONE);
        licenseView.setVisibility(show ? View.VISIBLE : View.GONE);
        if (!show)
            trContainer.removeAllViews();
    }

    @Override
    public TranslatePresenter createPresenter() {
        return new TranslatePresenter();
    }


    //set text in translation fields
    //we have a few minor translations, so using the recycler is unnecessarily
    @Override
    public void setTranslation(SingleTranslation translation, String langName) {
        if (translation == null) return;
        List<TranslationText> trList = translation.getTranslationList();
        setFieldsVisibility(true);
        mainTranslationView.setText(translation.getMainTranslation());
        languageView.setText(langName);
        //set minor translations
        trContainer.removeAllViews();
        if (trList == null || trList.size() == 0) return;
        LayoutInflater inflater = getLayoutInflater(null);
        for (int i = 1; i < trList.size(); i++) {
            RelativeLayout minorTranslate = (RelativeLayout) inflater.inflate(R.layout.it_minor_translation, trContainer, false);
            TextView indexView = (TextView) minorTranslate.findViewById(R.id.index);
            TextView translationView = (TextView) minorTranslate.findViewById(R.id.translation);
            indexView.setText(String.valueOf(i));
            translationView.setText(trList.get(i).getText());
            trContainer.addView(minorTranslate);
        }
    }

    //set spannable string "translate from" and click listener on it
    @Override
    public void setDetectedLang(String langName, String langDesc) {
        detectLangView.setText(generateSpannableText(langName));
        detectLangView.setVisibility(View.VISIBLE);
        subs.add(RxView.clicks(detectLangView)
                .subscribe((event) -> {
                    presenter.detectLangClick(langDesc, textToTranslateView.getText().toString());
                    detectLangView.setVisibility(View.GONE);
                }));
    }

    //get spannable string "translate from"
    private SpannableStringBuilder generateSpannableText(String langName) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder();
        String detectText = getString(R.string.detect_text);
        sBuilder.append(detectText)
                .append(" ")
                .append(langName);

        int beginSpan = detectText.length() + 1;
        int endSpan = detectText.length() + langName.length() + 1;

        sBuilder.setSpan(new AbsoluteSizeSpan(
                        (int) getResources().getDimension(R.dimen.to_text_size)),
                beginSpan,
                endSpan,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        sBuilder.setSpan(new ForegroundColorSpan(blackColor),
                beginSpan,
                endSpan,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sBuilder;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        outState.putString(TR_KEY, presenter.getCurrentTranslation().getMainTranslation());
        outState.putString(LANG_KEY, presenter.getCurrentLang());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: ");
        if (savedInstanceState != null) {
            String lang = savedInstanceState.getString(LANG_KEY);
            String translation = savedInstanceState.getString(TR_KEY);
            if (lang != null) {
                setFieldsVisibility(true);
                mainTranslationView.setText(translation);
                languageView.setText(lang);
            }
        }
    }
}
