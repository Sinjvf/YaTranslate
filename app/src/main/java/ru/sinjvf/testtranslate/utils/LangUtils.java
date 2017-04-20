package ru.sinjvf.testtranslate.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.sinjvf.testtranslate.data.DaoSession;
import ru.sinjvf.testtranslate.data.JoinLangs;
import ru.sinjvf.testtranslate.data.JoinLangsDao;
import ru.sinjvf.testtranslate.data.Lang;
import ru.sinjvf.testtranslate.data.LangDao;
import ru.sinjvf.testtranslate.data.LangPair;
import ru.sinjvf.testtranslate.retrofit.responses.GetLangsResponse;

/**
 * Created by Sinjvf on 18.04.2017.
 */

public class LangUtils {
    public static boolean isLangsExist(DaoSession daoSession) {
        LangDao langDao = daoSession.getLangDao();
        List<Lang> langs = langDao.queryBuilder()
                .list();
        return (langs != null && langs.size() != 0);
    }

    public static List<Lang> getFromList(DaoSession daoSession) {
        LangDao langDao = daoSession.getLangDao();
        List<Lang> queryListAll = langDao.queryBuilder()
                .list();
        List<Lang> list = new ArrayList<>();
        for (Lang singleLang : queryListAll) {
            if (singleLang.getLangList() != null && singleLang.getLangList().size() != 0) {
                list.add(singleLang);
            }
        }
        return list;
    }

    public static List<Lang> getToList(String descFrom, DaoSession daoSession) {
        LangDao langDao = daoSession.getLangDao();
        List<Lang> queryListAll = langDao.queryBuilder()
                .where(LangDao.Properties.Desc.eq(descFrom))
                .list();
        if (queryListAll == null || queryListAll.size() == 0) return new ArrayList<>();
        List<Lang> list = queryListAll.get(0).getLangList();
     /*   for (Lang singleLang : queryListAll.get(0).getLangList()) {
            if (singleLang.getLangList() != null && singleLang.getLangList().size() != 0) {
                list.add(singleLang);
            }
        }*/
        return list;
    }

    public static void saveLangs(GetLangsResponse response, DaoSession daoSession) {
        LangDao langDao = daoSession.getLangDao();
        JoinLangsDao joinLangsDao = daoSession.getJoinLangsDao();
        LangPair langPair;
        long fromId;
        long toId;
        JoinLangs join;
        Map<String, String> descMap = response.getLangMap();
        for (String pairStr : response.getDirList()) {
            langPair = new LangPair(pairStr);
            fromId = LangUtils.insertOrIgnoreLang(langPair.getFrom(), descMap, daoSession);
            toId = LangUtils.insertOrIgnoreLang(langPair.getTo(), descMap, daoSession);
            join = new JoinLangs();
            join.setFromId(fromId);
            join.setToId(toId);
            joinLangsDao.insert(join);
        }
    }

    public static String getDescByName(String name, DaoSession daoSession) {
        LangDao langDao = daoSession.getLangDao();
        List<Lang> nameList = langDao.queryBuilder()
                .where(LangDao.Properties.Name.eq(name))
                .list();

        String desc = "";
        if (nameList != null && nameList.size() != 0) {
            desc = nameList.get(0).getDesc();
        }
        return desc;
    }


    public static String getNameByDesc(String desc, DaoSession daoSession) {
        LangDao langDao = daoSession.getLangDao();
        List<Lang> nameList = langDao.queryBuilder()
                .where(LangDao.Properties.Desc.eq(desc))
                .list();

        String name = "";
        if (nameList != null && nameList.size() != 0) {
            name = nameList.get(0).getName();
        }
        return name;
    }


    //return id
    public static Long insertOrIgnoreLang(String desc, Map<String, String> descMap, DaoSession daoSession) {
        LangDao langDao = daoSession.getLangDao();
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
}
