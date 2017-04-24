package ru.sinjvf.testtranslate;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import ru.sinjvf.testtranslate.data.DaoSession;
import ru.sinjvf.testtranslate.data.SingleTranslation;
import ru.sinjvf.testtranslate.main.TranslateApplication;
import ru.sinjvf.testtranslate.retrofit.responses.TranslateResponse;
import ru.sinjvf.testtranslate.utils.TranslateUtils;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * тестирование методов TranslateUtils для вставки\получения\обновления элемента в бд, и для получения списка избаранного из бд
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class DbTests {
    public static final String TEST_WORD = "hi";
    public static final String TEST_TRANSLATE = "Привет";
    public static final String TEST_LANG = "en-ru";
    private boolean favorite = false;
    private long id;
    private DaoSession daoSession;

    @Before
    public void createLogHistory() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        TranslateApplication app = (TranslateApplication) appContext.getApplicationContext();
        daoSession = app.getDaoSession();
        TranslateResponse translResponse = new TranslateResponse();
        translResponse.setLang(TEST_LANG);
        List<String> list = new ArrayList<>();
        list.add(TEST_TRANSLATE);
        translResponse.setTextList(list);
        id = TranslateUtils.insert(translResponse, TEST_WORD, daoSession);

    }


    @Test
    public void checkGetById() {
        SingleTranslation translation = TranslateUtils.getById(id, daoSession);

        assertThat(translation.getId(), is(id));
        assertThat(translation.getMainTranslation(), is(TEST_TRANSLATE));
        assertThat(translation.getText(), is(TEST_WORD));
        assertThat(translation.getIsFavorite(), is(favorite));

    }

    @Test
    public void checkFavorite() {
        SingleTranslation translation = TranslateUtils.getById(id, daoSession);
        favorite = true;
        translation.setIsFavorite(favorite);
        TranslateUtils.update(translation, daoSession);
        SingleTranslation newTranslation = TranslateUtils.getById(id, daoSession);
        assertThat(newTranslation.getIsFavorite(), is(favorite));

        //проверяем, будет ли наш новый перевод в списке избранного
        List<SingleTranslation> favoritesList = TranslateUtils.getFavoritesFiltered(TEST_WORD, daoSession);
        boolean check = false;
        for (SingleTranslation tr : favoritesList) {
            if (tr.getId() == id) {
                check = true;
                break;
            }
        }
        assertThat(check, is(true));


    }


}
