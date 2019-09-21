package com.lgh.huanglib.retrofitlib.http.cookie;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * post請求緩存数据
 * Created by WZG on 2016/10/26.
 */
@Entity
public class CookieResulte {
    @Id
    private Long id;
    /*url*/
    private String url;
    /*返回结果*/
    private String resulte;
    /*时间*/
    private long time;
    /*code*/
    private int code;
    private String message;

    @Generated(hash = 380916526)
    public CookieResulte(Long id, String url, String resulte, long time, int code,
                         String message) {
        this.id = id;
        this.url = url;
        this.resulte = resulte;
        this.time = time;
        this.code = code;
        this.message = message;
    }

    public CookieResulte(String url, String resulte, long time, int code,
                         String message) {
        this.url = url;
        this.resulte = resulte;
        this.time = time;
        this.code = code;
        this.message = message;
    }

    @Generated(hash = 2104390000)
    public CookieResulte() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResulte() {
        return this.resulte;
    }

    public void setResulte(String resulte) {
        this.resulte = resulte;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CookieResulte{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", resulte='" + resulte + '\'' +
                ", time=" + time +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
