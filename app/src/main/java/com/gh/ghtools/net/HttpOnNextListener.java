package com.gh.ghtools.net;

import com.gh.netlib.listener.BaseHttpOnNextListener;

import io.reactivex.Flowable;

/**
 * @author: gh
 * @description:
 * @date: 2019-07-18.
 * @from:
 */
public abstract class HttpOnNextListener<T>  extends BaseHttpOnNextListener<T>  {

    public abstract Flowable onConnect(HttpPostService service);

}
