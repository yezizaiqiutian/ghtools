package com.gh.ghtools.ui.loading

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.gh.ghtools.R
import com.gh.ghtools.base.BaseActivity
import kotlinx.android.synthetic.main.activity_loading.*

/**
 * @author: gh
 * @description:
 * @date: 2019-12-02.
 * @from:
 */
class LoadingActivity :  BaseActivity(){

    companion object {

        private val listData = arrayOf("权限请求", "网络请求")

        const val MESSAGE_TYPE_ONE = 1
        const val MESSAGE_TYPE_TWO = 2
        const val MESSAGE_NAME_THREE = "THREE"


        fun access(context: Context, messageType: Int, messageName: String) {
            var intent = Intent()
            intent.setClass(context, LoadingActivity::class.java)
            intent.putExtra("messageType", messageType)
            intent.putExtra("messageName", messageName)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
    }

    override fun initBundle() {
        super.initBundle()
    }

    override fun initView() {
        loadinglayout.showContent()

        loadinglayout.setRetryListener {
            loadinglayout.showContent()
        }

        btn_loading.setOnClickListener {
            loadinglayout.showLoading()
        }
        btn_error.setOnClickListener {
            loadinglayout.showError()
        }
        btn_empty.setOnClickListener {
            loadinglayout.showEmpty()
        }
    }

    override fun initData() {
        super.initData()
    }
}