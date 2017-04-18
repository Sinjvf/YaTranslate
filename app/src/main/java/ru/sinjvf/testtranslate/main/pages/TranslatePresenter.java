package ru.sinjvf.testtranslate.main.pages;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import ru.sinjvf.testtranslate.data.DaoSession;
import ru.sinjvf.testtranslate.data.JoinLangs;
import ru.sinjvf.testtranslate.data.JoinLangsDao;
import ru.sinjvf.testtranslate.data.Lang;
import ru.sinjvf.testtranslate.data.LangDao;
import ru.sinjvf.testtranslate.data.LangPair;
import ru.sinjvf.testtranslate.retrofit.ServerCallback;
import ru.sinjvf.testtranslate.retrofit.ServerHandler;
import ru.sinjvf.testtranslate.retrofit.responses.GetLangsResponse;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class TranslatePresenter extends SuperPagePresenter<TranslateView> {
    private LangPair currentLangPair = new LangPair("ru-en");
    private DaoSession daoSession;
    private LangDao langDao;
    private JoinLangsDao joinLangsDao;

    private ServerHandler handler = new ServerHandler();


    @Override
    public void attachView(TranslateView view) {
        super.attachView(view);
        daoSession = getView().getApp().getDaoSession();
        langDao = daoSession.getLangDao();
        joinLangsDao = daoSession.getJoinLangsDao();
        List<Lang> langs = langDao.queryBuilder()
                .list();
        if (langs==null || langs.size()==0){
            handler.getLang(currentLangPair.getFrom(), getLangs());
        }else {
            setSpinners();
        }

    }


    public void fromLangChanged(String newLang) {
        Log.d(TAG, "fromLangChanged: " + newLang);
        if (newLang == null) return;

        List<Lang> fromName = langDao.queryBuilder()
                .where(LangDao.Properties.Name.eq(newLang))
                .list();
        if (fromName != null && fromName.size() != 0) {
            currentLangPair.setFrom(fromName.get(0).getDesc());
            initToSpinner();
        }
        getView().setLang(currentLangPair.getStr());
    }

    public void toLangChanged(String newLang) {
        Log.d(TAG, "toLangChanged: " + newLang);
        if (newLang == null) return;

        List<Lang> toName = langDao.queryBuilder()
                .where(LangDao.Properties.Name.eq(newLang))
                .list();
        if (toName != null && toName.size() != 0) {
            currentLangPair.setTo(toName.get(0).getDesc());
        }
        getView().setLang(currentLangPair.getStr());
    }

    public void setNewText(String text) {
        Log.d(TAG, "setNewText: " + text);

    }


    private void saveLangs(GetLangsResponse response) {
        LangPair langPair;
        long fromId;
        long toId;
        JoinLangs join;
        Map<String, String> descMap = response.getLangMap();
        for (String pairStr : response.getDirList()) {
            Log.d(TAG, "saveLangs: "+pairStr);
            langPair = new LangPair(pairStr);
            fromId = insertOrIgnoreLang(langPair.getFrom(), descMap);
            toId = insertOrIgnoreLang(langPair.getTo(), descMap);
            join = new JoinLangs();
            join.setFromId(fromId);
            join.setToId(toId);
            joinLangsDao.insert(join);
        }
        Log.d(TAG, "fill tables: ");
        setSpinners();
    }

    private void setSpinners() {
        initFromSpinner();
        initToSpinner();
    }

    private void initFromSpinner(){
        List<Lang> queryListAll = langDao.queryBuilder()
                .list();
        List<String> fromList = new ArrayList<>();
        for (Lang singleLang : queryListAll) {
            if (singleLang.getLangList() != null && singleLang.getLangList().size() != 0) {
                fromList.add(singleLang.getName());
            }
        }
        List<Lang> fromNameList = langDao.queryBuilder()
                .where(LangDao.Properties.Desc.eq(currentLangPair.getFrom()))
                .list();

        String fromName="";
        if (fromNameList != null && fromNameList.size() != 0){
            fromName = fromNameList.get(0).getName();
        }
        getView().updateFromSpinner(fromList, fromName);
    }
    private void initToSpinner(){
        List<Lang> queryListAll = langDao.queryBuilder()
                .where(LangDao.Properties.Desc.eq(currentLangPair.getFrom()))
                .list();
        List<String> toList = new ArrayList<>();
        for (Lang singleLang : queryListAll.get(0).getLangList()) {
            if (singleLang.getLangList() != null && singleLang.getLangList().size() != 0) {
                toList.add(singleLang.getName());
            }
        }
        List<Lang> toNameList = langDao.queryBuilder()
                .where(LangDao.Properties.Desc.eq(currentLangPair.getTo()))
                .list();

        String toName="";
        if (toNameList != null && toNameList.size() != 0){
            toName = toNameList.get(0).getName();
        }
        getView().updateToSpinner(toList, toName);
    }


    //return id
    private long insertOrIgnoreLang(String desc, Map<String, String> descMap) {
        Lang lang = new Lang();
        lang.setDesc(desc);
        lang.setName(descMap.get(desc));
        List<Lang> queryListFrom = langDao.queryBuilder()
                .where(LangDao.Properties.Desc.eq(lang.getDesc()))
                .list();
        if (queryListFrom != null && queryListFrom.size() != 0) {
            lang = queryListFrom.get(0);
        } else {
            langDao.insert(lang);
        }
        return lang.getId();
    }

    public ServerCallback<GetLangsResponse> getLangs() {
        return new ServerCallback<GetLangsResponse>() {
            @Override
            public void onSuccess(Response<GetLangsResponse> response) {
                super.onSuccess(response);
                Log.d(TAG, "onSuccess: ");
                saveLangs(response.body());
            }


        };
    }
}
