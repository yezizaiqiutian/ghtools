package com.gh.netlib.api;

import java.io.Serializable;

/**
 * @author: gh
 * @description:请求接口返回基类
 * @date: 2019-07-13.
 * @from:
 */
public class BaseResultEntity<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
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

}
