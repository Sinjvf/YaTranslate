package ru.sinjvf.testtranslate.main.pages;

import android.util.Log;

import java.util.List;

import ru.sinjvf.testtranslate.data.DaoSession;
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


    @Override
    public void attachView(TranslateView view) {
        super.attachView(view);
        daoSession = getView().getApp().getDaoSession();
        if (!LangUtils.isLangsExist(daoSession)){
            handler.getLang(currentLangPair.getFrom(), getLangs());
        }else {
            setSpinners();
        }

    }


    public void fromLangChanged(String newLang) {
        Log.d(TAG, "fromLangChanged: " + newLang);
        if (newLang == null) return;
        currentLangPair.setFrom(LangUtils.getDescByName(newLang, daoSession));
        if (!isViewAttached())return;
        initSpinner(false);
    }

    public void toLangChanged(String newLang) {
        Log.d(TAG, "toLangChanged: " + newLang);
        if (newLang == null) return;
        currentLangPair.setTo(LangUtils.getDescByName(newLang, daoSession));
        if (!isViewAttached())return;
    }

    public void setNewText(String text) {
        Log.d(TAG, "setNewText: " + text);
        currentTranslation = TranslateUtils.getByText(text, currentLangPair.getStr(), daoSession);
        if (currentTranslation!=null){
            setTranslatedText();
            Log.d(TAG, "setNewText: "+currentLangPair.getStr()+", "+currentTranslation.getLang());
        }else {
            handler.translate(text, currentLangPair.getStr(), translate(text));
        }
    }

    public void favoriteClick(boolean checked){
        if(currentTranslation!=null) {
            currentTranslation.setIsFavorite(checked);
            TranslateUtils.update(currentTranslation, daoSession);
        }
    }
    public void swapClick(){
        if (!isViewAttached())return;
        String tmp = currentLangPair.getFrom();
        currentLangPair.setFrom(currentLangPair.getTo());
        currentLangPair.setTo(tmp);
        setSpinners();

    }

    private void saveLangs(GetLangsResponse response) {
        LangUtils.saveLangs(response, daoSession);
        setSpinners();
    }

    private void setSpinners() {
        initSpinner(true);
        initSpinner(false);
    }

    private void initSpinner(boolean isFromSpinner) {
        if (!isViewAttached()) return;
        List<String> list = (isFromSpinner)?LangUtils.getFromList(daoSession):LangUtils.getToList(currentLangPair.getFrom(), daoSession);
        String desc = (isFromSpinner)?currentLangPair.getFrom():currentLangPair.getTo();
        String name = LangUtils.getNameByDesc(desc, daoSession);
        getView().updateSpinner(isFromSpinner, list, name);

    }

    private void saveTranslation(String text, TranslateResponse response){
        Log.d(TAG, "setTranslatedText: ");
        long id = TranslateUtils.insert(response, text, daoSession);
        currentTranslation = TranslateUtils.getById(id, daoSession);
        setTranslatedText();
    }

    private void setTranslatedText(){
        if (!isViewAttached())return;
        String langName = LangUtils.getNameByDesc(currentLangPair.getTo(), daoSession);
        getView().setTranslation(currentTranslation, langName);
    }


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

    public ServerCallback<TranslateResponse> translate(String text) {
        return new ServerCallback<TranslateResponse>(getView()) {
            @Override
            public void onSuccess(TranslateResponse response) {
                Log.d(TAG, "onSuccess: ");
                saveTranslation(text, response);
            }

        };
    }
}
