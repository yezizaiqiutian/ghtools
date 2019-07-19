package com.gh.ghtools.net;

import com.gh.netlib.api.BaseApi;
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

}
