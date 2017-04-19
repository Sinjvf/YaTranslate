package ru.sinjvf.testtranslate.main;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import ru.sinjvf.testtranslate.data.DaoMaster;
import ru.sinjvf.testtranslate.data.DaoSession;

/**
 * Created by Sinjvf on 18.04.2017.
 * Application. Create dao database
 */

public class TranslateApplication  extends Application{

    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "translate_db");
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
