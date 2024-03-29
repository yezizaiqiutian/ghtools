package com.gh.netlib.api;


import com.gh.netlib.exception.HttpTimeException;
import com.gh.netlib.listener.BaseHttpOnNextListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

/**
 * @author: gh
 * @description:
 * @date: 2019-07-13.
 * @from:
 */
public abstract class BaseApi<T> implements Function<BaseResultEntity<T>, BaseResultEntity<T>> {
    //rx生命周期管理
    private SoftReference<RxAppCompatActivity> rxAppCompatActivity;
    /*回调*/
    private SoftReference<BaseHttpOnNextListener> listener;
    /*是否能取消加载框*/
    private boolean cancel;
    /*是否显示加载框*/
    private boolean showProgress;
    /*是否需要缓存处理*/
//    private boolean cache;
    /*基础url*/
    private String baseUrl = "https://www.izaodao.com/Api/";
    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
//    private String method="";
    /*超时时间-默认6秒*/
    private int connectionTime = 6;
    /*有网情况下的本地缓存时间默认60秒*/
//    private int cookieNetWorkTime = 60;
    /*无网络的情况下本地缓存时间默认30天*/
//    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;
    /* 失败后retry次数*/
    private int retryCount = 0;
    /*失败后retry延迟*/
    private long retryDelay = 100;
    /*失败后retry叠加延迟*/
    private long retryIncreaseDelay = 10;
    /*缓存url-可手动设置*/
//    private String cacheUrl;

    public BaseApi(BaseHttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        setListener(listener);
        setRxAppCompatActivity(rxAppCompatActivity);
        setShowProgress(true);
//        setCache(true);
    }

    /**
     * 设置参数
     *
     * @param retrofit
     * @return
     */
    public abstract Flowable getObservable(Retrofit retrofit);


//    public int getCookieNoNetWorkTime() {
//        return cookieNoNetWorkTime;
//    }

//    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
//        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
//    }

//    public int getCookieNetWorkTime() {
//        return cookieNetWorkTime;
//    }

//    public void setCookieNetWorkTime(int cookieNetWorkTime) {
//        this.cookieNetWorkTime = cookieNetWorkTime;
//    }


    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }


//    public String getMethod() {
//        return method;
//    }
//
//    public void setMethod(String method) {
//        this.method = method;
//    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

//    public String getUrl() {
//        /*在没有手动设置url情况下，简单拼接*/
//        if (null == getCacheUrl() || "".equals(getCacheUrl())) {
//            return getBaseUrl() + getMethod();
//        }
//        return getCacheUrl();
//    }

    public void setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
        this.rxAppCompatActivity = new SoftReference(rxAppCompatActivity);
    }

//    public boolean isCache() {
//        return cache;
//    }
//
//    public void setCache(boolean cache) {
//        this.cache = cache;
//    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public SoftReference<BaseHttpOnNextListener> getListener() {
        return listener;
    }

    public void setListener(BaseHttpOnNextListener listener) {
        this.listener = new SoftReference(listener);
    }


    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
    }

    public long getRetryIncreaseDelay() {
        return retryIncreaseDelay;
    }

    public void setRetryIncreaseDelay(long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
    }

    /*
     * 获取当前rx生命周期
     * @return
     */
    public RxAppCompatActivity getRxAppCompatActivity() {
        return rxAppCompatActivity.get();
    }

    @Override
    public BaseResultEntity<T> apply(BaseResultEntity<T> httpResult) {
        if (httpResult.getCode() != 0) {
            throw new HttpTimeException(httpResult.getMsg());
        }
        return httpResult;
    }


//    public String getCacheUrl() {
//        return cacheUrl;
//    }
//
//    public void setCacheUrl(String cacheUrl) {
//        this.cacheUrl = cacheUrl;
//    }
}
