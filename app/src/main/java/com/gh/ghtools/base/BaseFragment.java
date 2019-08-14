package com.gh.ghtools.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * @author: gh
 * @description:
 * @date: 2019-07-24.
 * @from:
 */
public class BaseFragment extends RxFragment {

    protected BaseActivity activity;
    protected Context context;

    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BaseActivity) context;
        this.context = context;
    }

    @CallSuper
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBundle();
        initView();
        initData();
    }

    /**
     * 初始化传参
     */
    protected void initBundle() {

    }

    /**
     * 初始化View
     */
    protected void initView() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    protected boolean isVisible;

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @CallSuper
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {//当可见的时候执行操作
            isVisible = true;
            onVisible();
        } else {//不可见时执行相应的操作
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {

    }

    protected void onInvisible() {
    }

}
