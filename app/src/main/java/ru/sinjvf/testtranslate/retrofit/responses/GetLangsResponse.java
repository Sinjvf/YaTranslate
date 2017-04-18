package ru.sinjvf.testtranslate.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sinjvf on 18.04.2017.
 * response in getLang request
 */

public class GetLangsResponse extends SuperResponse {
    @SerializedName("dirs")
    @Expose
    private List<String> dirList = new ArrayList<>();
    @SerializedName("langs")
    @Expose
    private Map <String, String> langMap = new HashMap<>();

    public List<String> getDirList() {
        return dirList;
    }

    public void setDirList(List<String> dirList) {
        this.dirList = dirList;
    }

    public Map<String, String> getLangMap() {
        return langMap;
    }

    public void setLangMap(Map<String, String> langMap) {
        this.langMap = langMap;
    }
}
