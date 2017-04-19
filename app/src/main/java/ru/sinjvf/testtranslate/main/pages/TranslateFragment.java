package ru.sinjvf.testtranslate.main.pages;

import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
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

import java.util.List;

import butterknife.BindView;
import ru.sinjvf.testtranslate.R;
import ru.sinjvf.testtranslate.data.SingleTranslation;
import ru.sinjvf.testtranslate.data.TranslationText;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by Sinjvf on 17.04.2017.
 * main fragnet with translation form
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
    @BindView(R.id.license)
    TextView licenseView;
    @BindView(R.id.add_to_favorite)
    CheckBox addToFavorite;
    @BindView(R.id.minor_translations_container)
    LinearLayout trContainer;


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
        setSpan();
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
    public void updateSpinner(boolean isFromSpinner, List<String> langsList, String defaultLang) {
        Spinner spinner = (isFromSpinner) ? fromLangView : toLangView;
        updateOneSpinner(spinner, langsList, defaultLang);
    }

    //set adapter to spinner
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
    //set spanned text with link needed in license
    private void setSpan(){
        String text = getString(R.string.license)+" "+getString(R.string.service_name);
        Spannable spannableLicence = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                String licenseUrl =  getString(R.string.license_url);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(licenseUrl));
                startActivity(browserIntent);
            }
        };
        int posStart = text.indexOf(getString(R.string.service_name));
        int posEnd = text.length();
        spannableLicence.setSpan(new UnderlineSpan(), posStart, posEnd,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        spannableLicence.setSpan(clickableSpan, posStart, posEnd,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        licenseView.setText(spannableLicence);
        licenseView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    //all events listeners
    private void initListeners() {
        subs = new CompositeSubscription();
        //listeners of events in spinners
        subs.add(RxAdapterView.itemSelections(fromLangView)
                .skip(1)
                .subscribe((event) -> presenter.fromLangChanged((String) fromLangView.getSelectedItem())));
        subs.add(RxAdapterView.itemSelections(toLangView)
                .skip(1)
                .subscribe((event) -> presenter.toLangChanged((String) toLangView.getSelectedItem())));
        //click "done" in text for translation
        subs.add(RxTextView.editorActions(textToTranslateView)
                .subscribe((event) -> presenter.setNewText(textToTranslateView.getText().toString())));
        //click "clearText" button
        subs.add(RxView.clicks(clearTextView)
                .subscribe((event) -> clearText()));
        //click "favorite" star image
        subs.add(RxCompoundButton.checkedChanges(addToFavorite)
                .subscribe((aBoolean) -> presenter.favoriteClick(aBoolean)));
    }

    //clear text in translation form and clear last translation views
    private void clearText() {
        textToTranslateView.setText("");
        setFieldsVisibility(false);
    }

    //set visibility of translation fields.
    //show = true - visible
    //show = false - gone
    private void setFieldsVisibility(boolean show){

        languageView.setVisibility(show?View.VISIBLE:View.GONE);
        mainTranslationView.setVisibility(show?View.VISIBLE:View.GONE);
        addToFavorite.setVisibility(show?View.VISIBLE:View.GONE);
        licenseView.setVisibility(show?View.VISIBLE:View.GONE);
        if(!show)
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
        List<TranslationText> trList = translation.getTranslationList();
        setFieldsVisibility(true);
        if (trList == null || trList.size() == 0) return;
        mainTranslationView.setText(trList.get(0).getText());
        languageView.setText(langName);
        trContainer.removeAllViews();
        LayoutInflater inflater = getLayoutInflater(null);
        //set minor translations
        for (int i = 1; i < trList.size(); i++) {
            RelativeLayout minorTranslate = (RelativeLayout) inflater.inflate(R.layout.it_minor_translation, trContainer, false);
            TextView indexView = (TextView)minorTranslate.findViewById(R.id.index);
            TextView translationView = (TextView)minorTranslate.findViewById(R.id.translation);
            indexView.setText(String.valueOf(i));
            translationView.setText(trList.get(i).getText());
            trContainer.addView(minorTranslate);
        }
    }
}
