package ru.sinjvf.testtranslate.utils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import ru.sinjvf.testtranslate.data.DaoSession;
import ru.sinjvf.testtranslate.data.SingleTranslation;
import ru.sinjvf.testtranslate.data.SingleTranslationDao;
import ru.sinjvf.testtranslate.retrofit.responses.TranslateResponse;

/**
 * Created by Sinjvf on 18.04.2017.
 */

public class TranslateUtils {
    public static void update(SingleTranslation tr, DaoSession daoSession){
        SingleTranslationDao translationDao =  daoSession.getSingleTranslationDao();
        translationDao.update(tr);
    }

    public static void delete(SingleTranslation tr, DaoSession daoSession){
        SingleTranslationDao translationDao =  daoSession.getSingleTranslationDao();
        translationDao.delete(tr);
    }

    public static List<SingleTranslation> getHistory(DaoSession daoSession){
        SingleTranslationDao translationDao =  daoSession.getSingleTranslationDao();
        List<SingleTranslation> list = translationDao.queryBuilder()
                .orderDesc(SingleTranslationDao.Properties.Id)
                .list();
        return list;
    }

    public static List<SingleTranslation> getHistoryFiltred(String filter, DaoSession daoSession){
        SingleTranslationDao translationDao =  daoSession.getSingleTranslationDao();


        QueryBuilder<SingleTranslation> qb = translationDao.queryBuilder();
        qb.where(qb.or(SingleTranslationDao.Properties.MainTranslation.like("%"+filter+"%"),
                SingleTranslationDao.Properties.Text.like("%"+filter+"%")));
        qb.orderDesc(SingleTranslationDao.Properties.Id);
        List<SingleTranslation> list = qb.list();

        return list;
    }

    public static List<SingleTranslation> getFavorites(DaoSession daoSession){
        SingleTranslationDao translationDao =  daoSession.getSingleTranslationDao();
        List<SingleTranslation> list = translationDao.queryBuilder()
                .where(SingleTranslationDao.Properties.IsFavorite.eq(true))
                .orderDesc(SingleTranslationDao.Properties.Id)
                .list();
        return list;
    }
    public static List<SingleTranslation> getFavoritesFiltered(String filter, DaoSession daoSession){
        SingleTranslationDao translationDao =  daoSession.getSingleTranslationDao();
        QueryBuilder<SingleTranslation> qb = translationDao.queryBuilder();
        qb.where(qb.and(SingleTranslationDao.Properties.IsFavorite.eq(true),
                 qb.or(SingleTranslationDao.Properties.Text.like("%"+filter+"%"),
                        SingleTranslationDao.Properties.MainTranslation.like("%"+filter+"%") )));
        qb.orderDesc(SingleTranslationDao.Properties.Id);
        List<SingleTranslation> list = qb.list();
        return list;
    }
    public static Long insert(TranslateResponse response, String text, DaoSession daoSession){
        SingleTranslationDao translationDao =  daoSession.getSingleTranslationDao();
        SingleTranslation translation = new SingleTranslation();
        translation.setText(text);
        translation.setIsFavorite(false);
        translation.setLang(response.getLang());
        List<String> textList = response.getTextList();
        if (textList!=null && textList.size()!=0) {
            translation.setMainTranslation(textList.get(0));
        }
        translationDao.insert(translation);
        return translation.getId();
    }

    public static SingleTranslation getById(long id, DaoSession daoSession){
        SingleTranslationDao translationDao =  daoSession.getSingleTranslationDao();
        List<SingleTranslation> list = translationDao.queryBuilder()
                .where(SingleTranslationDao.Properties.Id.eq(id))
                .list();

        if (list != null && list.size() != 0) {
            return list.get(0);
        }
        return null;
    }

    public static SingleTranslation getByText(String text, String lang, DaoSession daoSession){
        SingleTranslationDao translationDao =  daoSession.getSingleTranslationDao();

        QueryBuilder<SingleTranslation> qb = translationDao.queryBuilder();
        qb.where(qb.and(SingleTranslationDao.Properties.Text.eq(text), SingleTranslationDao.Properties.Lang.eq(lang)));
        List<SingleTranslation> list = qb.list();

        if (list != null && list.size() != 0) {
            return list.get(0);
        }
        return null;
    }

}
