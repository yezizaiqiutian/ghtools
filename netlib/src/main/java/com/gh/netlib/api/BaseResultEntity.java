package com.gh.netlib.api;

/**
 * @author: gh
 * @description:请求接口返回基类
 * @date: 2019-07-13.
 * @from:
 */
public class BaseResultEntity<T> {

    private int ret;
    private String msg;
    private T data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
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
