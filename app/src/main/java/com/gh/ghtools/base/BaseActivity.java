package com.gh.ghtools.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.CallSuper;

import com.jaeger.library.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

public class BaseActivity extends RxAppCompatActivity {

    protected Context context;
    protected BaseActivity activity;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        activity = this;
    }

    @CallSuper
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
        ButterKnife.bind(this);
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

    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }

}
