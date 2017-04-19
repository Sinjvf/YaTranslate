package ru.sinjvf.testtranslate.data;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * Created by Sinjvf on 17.04.2017.
 * DB Class for saving single translation.
 * Use greenDao ORM
 */

@Entity
public class SingleTranslation {

    @Id(autoincrement = true)
    private Long id;
    private String lang;
    private String text;
    private boolean isFavorite;
    private  String mainTranslation;

    @ToMany(referencedJoinProperty = "translateId")
    private List<TranslationText> translationList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1264460250)
    private transient SingleTranslationDao myDao;

    @Generated(hash = 553402247)
    public SingleTranslation(Long id, String lang, String text, boolean isFavorite,
            String mainTranslation) {
        this.id = id;
        this.lang = lang;
        this.text = text;
        this.isFavorite = isFavorite;
        this.mainTranslation = mainTranslation;
    }

    @Generated(hash = 377811225)
    public SingleTranslation() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getIsFavorite() {
        return this.isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getMainTranslation() {
        return this.mainTranslation;
    }

    public void setMainTranslation(String mainTranslation) {
        this.mainTranslation = mainTranslation;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 82780351)
    public List<TranslationText> getTranslationList() {
        if (translationList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TranslationTextDao targetDao = daoSession.getTranslationTextDao();
            List<TranslationText> translationListNew = targetDao
                    ._querySingleTranslation_TranslationList(id);
            synchronized (this) {
                if (translationList == null) {
                    translationList = translationListNew;
                }
            }
        }
        return translationList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1937075857)
    public synchronized void resetTranslationList() {
        translationList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1889450203)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSingleTranslationDao() : null;
    }

}

