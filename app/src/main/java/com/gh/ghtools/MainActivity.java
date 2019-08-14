package com.gh.ghtools;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gh.ghtools.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Arrays;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.id_rv_list)
    RecyclerView id_rv_list;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srl_refresh;
    private BaseQuickAdapter<String, BaseViewHolder> adapter;
    private String[] listData = {"网络加载"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_textview) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.id_tv_text, item);
            }
        };
        id_rv_list.setLayoutManager(new LinearLayoutManager(context));
        id_rv_list.setAdapter(adapter);
        id_rv_list.setNestedScrollingEnabled(false);
        id_rv_list.setHasFixedSize(true);

        srl_refresh.setEnableLoadMore(true);
        srl_refresh.setEnableRefresh(true);
        srl_refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srl_refresh.finishLoadMore(1000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srl_refresh.finishRefresh(1000);
            }
        });

    }

    @Override
    protected void initData() {
        adapter.setNewData(Arrays.asList(listData));
        adapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position) {
                case 0:
                    NetTestActivity.access(context);
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        });
    }
}
