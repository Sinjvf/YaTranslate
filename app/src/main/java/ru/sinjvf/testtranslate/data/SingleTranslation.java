package ru.sinjvf.testtranslate.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Sinjvf on 17.04.2017.
 * DB Class for saving single translation.
 * Use greenDao ORM
 */

@Entity
public class SingleTranslation implements Parcelable {

    @Id(autoincrement = true)
    private Long id;
    private String lang;
    private String text;
    private boolean isFavorite;
    private  String mainTranslation;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.lang);
        dest.writeString(this.text);
        dest.writeByte(this.isFavorite ? (byte) 1 : (byte) 0);
        dest.writeString(this.mainTranslation);
    }

    protected SingleTranslation(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.lang = in.readString();
        this.text = in.readString();
        this.isFavorite = in.readByte() != 0;
        this.mainTranslation = in.readString();
    }

    public static final Parcelable.Creator<SingleTranslation> CREATOR = new Parcelable.Creator<SingleTranslation>() {
        @Override
        public SingleTranslation createFromParcel(Parcel source) {
            return new SingleTranslation(source);
        }

        @Override
        public SingleTranslation[] newArray(int size) {
            return new SingleTranslation[size];
        }
    };
}

