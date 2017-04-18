package ru.sinjvf.testtranslate.data;

/**
 * Created by Sinjvf on 18.04.2017.
 */

public class LangPair {
    private String from = "";
    private String to = "";

    public LangPair(String str) {
        if (str == null) return;
        String[] elements = str.split("-");
        if (elements.length == 2) {
            from = elements[0];
            to = elements[1];
        }
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStr() {
        return from + "-" + to;
    }
}
