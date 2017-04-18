package ru.sinjvf.testtranslate.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Sinjvf on 18.04.2017.
 */
@Entity
public class JoinLangs {
    @Id(autoincrement = true)
    private Long id;
    private Long fromId;
    private Long toId;
    @Generated(hash = 1196747325)
    public JoinLangs(Long id, Long fromId, Long toId) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
    }
    @Generated(hash = 686129447)
    public JoinLangs() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getFromId() {
        return this.fromId;
    }
    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }
    public Long getToId() {
        return this.toId;
    }
    public void setToId(Long toId) {
        this.toId = toId;
    }

}
