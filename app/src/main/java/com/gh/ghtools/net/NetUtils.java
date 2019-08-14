package com.gh.ghtools.net;

import com.gh.netlib.http.HttpManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Flowable;
import retrofit2.Retrofit;

/**
 * @author: gh
 * @description:
 * @date: 2019-07-18.
 * @from:
 */
public class NetUtils {

    /**
     * 请求网咯,有加载框
     * @param activity
     * @param simpleOnNextListener
     */
    public static void getNet(RxAppCompatActivity activity, HttpOnNextListener simpleOnNextListener) {
        HttpManager manager = HttpManager.getInstance();

        manager.doHttpDeal(new NetApi(simpleOnNextListener, activity) {
            @Override
            public Flowable getObservable(Retrofit retrofit) {
                HttpPostService service = retrofit.create(HttpPostService.class);
                return simpleOnNextListener.onConnect(service);
            }
        });
    }

    /**
     * 请求网络,无加载框
     * @param activity
     * @param simpleOnNextListener
     */
    public static void getNetNoProgress(RxAppCompatActivity activity, HttpOnNextListener simpleOnNextListener) {
        HttpManager manager = HttpManager.getInstance();

        manager.doHttpDeal(new NetApiNoProgress(simpleOnNextListener, activity) {
            @Override
            public Flowable getObservable(Retrofit retrofit) {
                HttpPostService service = retrofit.create(HttpPostService.class);
                return simpleOnNextListener.onConnect(service);
            }
        });
    }

}
