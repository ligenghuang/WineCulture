package com.lgh.huanglib.model;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseDto<T> {
    private int result = -1;
    private String msg;
    private T data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return toJson(BaseDto.this);
    }

    private String toJson(Object obj) {
        // TODO Auto-generated method stub
        // FieldNamingPolicy.LOWER_CASE_WITH_DASHES    全部转换为小写，并用空格或者下划线分隔
        //FieldNamingPolicy.UPPER_CAMEL_CASE    所以单词首字母大写
        Gson gson2 = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();
        String obj2 = gson2.toJson(obj);
        return obj2;
    }
}
