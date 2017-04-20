package ru.sinjvf.testtranslate.main.pages;

import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import ru.sinjvf.testtranslate.data.DaoSession;
import ru.sinjvf.testtranslate.data.Lang;
import ru.sinjvf.testtranslate.data.LangPair;
import ru.sinjvf.testtranslate.data.SingleTranslation;
import ru.sinjvf.testtranslate.retrofit.ServerCallback;
import ru.sinjvf.testtranslate.retrofit.ServerHandler;
import ru.sinjvf.testtranslate.retrofit.responses.GetLangsResponse;
import ru.sinjvf.testtranslate.retrofit.responses.TranslateResponse;
import ru.sinjvf.testtranslate.utils.LangUtils;
import ru.sinjvf.testtranslate.utils.TranslateUtils;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class TranslatePresenter extends SuperPagePresenter<TranslateView> {
    private LangPair currentLangPair = new LangPair("ru-en");
    private SingleTranslation currentTranslation;
    private DaoSession daoSession;

    private ServerHandler handler = new ServerHandler();


    //mvp presenter's lifecycle method
    @Override
    public void attachView(TranslateView view) {
        super.attachView(view);
        daoSession = getView().getApp().getDaoSession();
        if (!LangUtils.isLangsExist(daoSession)) {
            getView().showProgress(true);
            handler.getLang(currentLangPair.getFrom(), getLangs());
        } else {
            currentLangPair =setSpinners();
        }
    }


    //change the lang in spinner with "from" languages
    public void fromLangChanged(String newLang) {
        Log.d(TAG, "fromLangChanged: " + newLang);
        if (newLang == null) return;
        currentLangPair.setFrom(LangUtils.getDescByName(newLang, daoSession));
        if (!isViewAttached()) return;
        initSpinner(false);
    }

    //change the lang in spinner with "to" languages
    public void toLangChanged(String newLang) {
        Log.d(TAG, "toLangChanged: " + newLang);
        if (newLang == null) return;
        currentLangPair.setTo(LangUtils.getDescByName(newLang, daoSession));

        if (!isViewAttached()) return;
    }

    //text in translation field invoke "done" action
    public void setNewText(String text) {
        Log.d(TAG, "setNewText: " + text);
        getTranslation(text);
        handler.detectLang(text, currentLangPair.getFrom(), detect());
    }

    //check if translation exist in db, if not - sent server request
    private void getTranslation(String text){
        currentTranslation = TranslateUtils.getByText(text, currentLangPair.getStr(), daoSession);
        if (currentTranslation != null) {
            setTranslatedText();
        } else {
            if (isViewAttached())getView().showProgress(true);
            handler.translate(text, currentLangPair.getStr(), translate(text));
        }
    }

    //click the "add to favorite" star
    public void favoriteClick(boolean checked) {
        if (currentTranslation != null) {
            currentTranslation.setIsFavorite(checked);
            TranslateUtils.update(currentTranslation, daoSession);
        }
    }

    //click the image "swap languages"
    public void swapClick() {
        if (!isViewAttached()) return;
        String tmp = currentLangPair.getFrom();
        currentLangPair.setFrom(currentLangPair.getTo());
        currentLangPair.setTo(tmp);
        currentLangPair = setSpinners();

    }

    //click the text "translate with: <lang name>"
    public void detectLangClick(String langDesc, String text) {
        if (!isViewAttached()) return;
        Log.d(TAG, "detectLangClick: ");
        currentLangPair.setFrom(langDesc);
        currentLangPair = setSpinners();
        getTranslation(text);
    }

    //save the list of languages
    private void saveLangs(GetLangsResponse response) {
        Log.d(TAG, "saveLangs: ");
        LangUtils.saveLangs(response, daoSession);
        setSpinners();
    }

    //update spinners' lists
    //return new spinners pair
    private LangPair setSpinners() {
        Log.d(TAG, "setSpinners: ");
        String from = initSpinner(true);
        String to = initSpinner(false);
        return new LangPair(from + "-" + to);
    }

    //update single spinner's list
    //isFromSpinner = true - update "from" spinner
    //else update "to" spinner
    //return description of new lang in spinner
    private String initSpinner(boolean isFromSpinner) {
        if (!isViewAttached()) return "";
        List<Lang> list = (isFromSpinner) ? LangUtils.getFromList(daoSession) : LangUtils.getToList(currentLangPair.getFrom(), daoSession);
        String desc = (isFromSpinner) ? currentLangPair.getFrom() : currentLangPair.getTo();
        String name = LangUtils.getNameByDesc(desc, daoSession);
        return getView().updateSpinner(isFromSpinner, list, name);
    }

    //save new translation in DB
    private void saveTranslation(String text, TranslateResponse response) {
        Log.d(TAG, "saveTranslation: ");
        long id = TranslateUtils.insert(response, text, daoSession);
        currentTranslation = TranslateUtils.getById(id, daoSession);
        setTranslatedText();
    }

    //set translation text in the screen
    private void setTranslatedText() {
        Log.d(TAG, "setTranslatedText: ");
        if (!isViewAttached()) return;
        String langName = LangUtils.getNameByDesc(currentLangPair.getTo(), daoSession);
        getView().setTranslation(currentTranslation, langName);
    }

    //set the text "translate with: <lang name>" in the screen
    private void setDetectedLangs(TranslateResponse response) {
        Log.d(TAG, "setDetectedLangs: ");
        if (!isViewAttached()) return;
        if (!response.getLang().equals(currentLangPair.getFrom())) {
            String langName = LangUtils.getNameByDesc(response.getLang(), daoSession);
            //not all languages supported in this api
            if (!TextUtils.isEmpty(langName)) {
                getView().setDetectedLang(langName, response.getLang());
            }
        }
    }


    //callback of get langs request response
    public ServerCallback<GetLangsResponse> getLangs() {
        return new ServerCallback<GetLangsResponse>(getView()) {
            @Override
            public void onSuccess(GetLangsResponse response) {
                super.onSuccess(response);
                Log.d(TAG, "onSuccess: ");
                saveLangs(response);
            }

        };
    }

    //callback of translate request response
    public ServerCallback<TranslateResponse> translate(String text) {
        return new ServerCallback<TranslateResponse>(getView()) {
            @Override
            public void onSuccess(TranslateResponse response) {
                Log.d(TAG, "onSuccess: ");
                saveTranslation(text, response);
            }
        };
    }

    //callback of detect request response
    //nothing show when error occur
    public ServerCallback<TranslateResponse> detect() {
        return new ServerCallback<TranslateResponse>(getView()) {
            @Override
            public void onSuccess(TranslateResponse response) {
                Log.d(TAG, "onSuccess: ");
                setDetectedLangs(response);
            }

            @Override
            public void onError(ResponseBody response) {

            }

            @Override
            public void onFailure(Call<TranslateResponse> call, Throwable t) {

            }
        };
    }
}
