package com.gh.ghtools.net;

import com.gh.ghtools.BuildConfig;
import com.gh.netlib.api.BaseApi;
import com.gh.netlib.listener.BaseHttpOnNextListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author: gh
 * @description: 无加载框,下拉刷新页面用
 * @date: 2019-07-19.
 * @from:
 */
public abstract class NetApiNoProgress<T>  extends BaseApi<T> {

    public NetApiNoProgress(BaseHttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
        setBaseUrl(BuildConfig.BASE_URL);
        setCancel(true);
        setShowProgress(false);
    }
}
