package ru.sinjvf.testtranslate.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Sinjvf on 17.04.2017.
 */

@Entity
public class TranslationText {
    @Id
    private Long id;
    private Long translateId;
    private String text;
    @Generated(hash = 1874413750)
    public TranslationText(Long id, Long translateId, String text) {
        this.id = id;
        this.translateId = translateId;
        this.text = text;
    }
    @Generated(hash = 1568984268)
    public TranslationText() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTranslateId() {
        return this.translateId;
    }
    public void setTranslateId(Long translateId) {
        this.translateId = translateId;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }

}
