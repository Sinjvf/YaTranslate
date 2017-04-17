package ru.sinjvf.testtranslate.main.pages;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sinjvf on 17.04.2017.
 */

public class TranslatePresenter extends SuperPagePresenter<TranslateView> {
    static List<String> langList = new ArrayList<>();

    static {
        for (int i=0; i<9; i++){
            langList.add("lang "+i);
        }}


    public int getDefautlFromLang(){
        if(!isViewAttached()) return 0;
        return 0;
    }
    public int getDefautlToLang(){
        if(!isViewAttached()) return 0;
        return 1;
    }
    public List<String> getLangList(){
        return langList;
    }


    public void  fromLangChanged(String newLang){
        Log.d(TAG, "fromLangChanged: "+newLang);
    }

    public void  toLangChanged(String newLang){
        Log.d(TAG, "toLangChanged: "+newLang);

    }

    public void setNewText(String text){
        Log.d(TAG, "setNewText: "+text);

    }
}
