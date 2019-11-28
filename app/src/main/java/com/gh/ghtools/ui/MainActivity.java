package com.gh.ghtools.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gh.commonlib.permissions.PermissionUtils;
import com.gh.ghtools.R;
import com.gh.ghtools.base.BaseActivity;
import com.gh.ghtools.ui.net.NetTestActivity;
import com.gh.ghtools.ui.rotate.RotateActivity;
import com.gh.ghtools.ui.rxjava.RxJavaActivity;
import com.gh.ghtools.ui.xifu.XiFuDemoActivity;
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
    private String[] listData = {"网络加载","吸附效果","rxjava","转盘","权限申请"};

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
                    XiFuDemoActivity.access(context);
                    break;
                case 2:
                    RxJavaActivity.access(context);
                    break;
                case 3:
                    RotateActivity.access(context);
                    break;
                case 4:
                    PermissionUtils.toCheckPermissions(activity, true, true, PermissionUtils.FLAG_PERMISSION_SD, new PermissionUtils.OnPermissionListener() {
                        @Override
                        public void onPermission(boolean isSuccess, int flag) {
                            if (/*!isSuccess || */PermissionUtils.FLAG_PERMISSION_SD != flag) {
                                return;
                            }
                            Toast.makeText(context, "请求权限:"+isSuccess, Toast.LENGTH_SHORT).show();
                        }
                    }, PermissionUtils.PERMISSION_SD);
                    break;
            }
        });
    }
}
