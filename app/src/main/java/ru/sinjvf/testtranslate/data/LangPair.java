package ru.sinjvf.testtranslate.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sinjvf on 18.04.2017.
 */

public class LangPair implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.from);
        dest.writeString(this.to);
    }

    protected LangPair(Parcel in) {
        this.from = in.readString();
        this.to = in.readString();
    }

    public static final Parcelable.Creator<LangPair> CREATOR = new Parcelable.Creator<LangPair>() {
        @Override
        public LangPair createFromParcel(Parcel source) {
            return new LangPair(source);
        }

        @Override
        public LangPair[] newArray(int size) {
            return new LangPair[size];
        }
    };
}
