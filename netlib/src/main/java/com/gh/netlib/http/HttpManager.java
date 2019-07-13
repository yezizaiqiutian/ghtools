package com.gh.netlib.http;

import android.util.Log;

import com.gh.netlib.RxRetrofitApp;
import com.gh.netlib.api.BaseApi;
import com.gh.netlib.exception.RetryWhenNetworkException;
import com.gh.netlib.listener.HttpOnNextListener;
import com.gh.netlib.subscribers.ProgressSubscriber;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: gh
 * @description:
 * @date: 2019-07-13.
 * @from:
 */
public class HttpManager {

    private volatile static HttpManager INSTANCE;

    private HttpManager(){}

    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 处理http请求
     *
     * @param baseApi
     */
    public void doHttpDeal(BaseApi baseApi) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(baseApi.getConnectionTime(), TimeUnit.SECONDS);
        if(RxRetrofitApp.isDebug()){
            builder.addInterceptor(getHttpLoggingInterceptor());
        }

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseApi.getBaseUrl())
                .build();

        ProgressSubscriber subscriber = new ProgressSubscriber(baseApi);
        Flowable observable = baseApi.getObservable(retrofit)
                /*失败后的retry配置*/
//                .retryWhen( new RetryWhenNetworkException(baseApi.getRetryCount(),
//                        baseApi.getRetryDelay(), baseApi.getRetryIncreaseDelay()))
                /*生命周期管理*/
                .compose(baseApi.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.PAUSE))
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*结果判断*/
                .map(baseApi);

        SoftReference<HttpOnNextListener> httpOnNextListener = baseApi.getListener();
        if (httpOnNextListener != null && httpOnNextListener.get() != null) {
            httpOnNextListener.get().onNext(observable);
        }

        observable.subscribe(subscriber);

    }

    private HttpLoggingInterceptor getHttpLoggingInterceptor(){
        //日志显示级别
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RxRetrofit","Retrofit====Message:"+message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

}
