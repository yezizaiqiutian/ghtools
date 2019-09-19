package com.gh.ghtools.ui.net;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gh.ghtools.R;
import com.gh.ghtools.base.BaseActivity;
import com.gh.ghtools.net.HttpOnNextListener;
import com.gh.ghtools.net.HttpPostService;
import com.gh.ghtools.net.NetUtils;
import com.gh.ghtools.net.SubjectResulte;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;

/**
 * @author: gh
 * @description:
 * @date: 2019-07-13.
 * @from:
 */
public class NetTestActivity extends BaseActivity {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public static void access(Context context) {

        Intent intent = new Intent();
        intent.setClass(context, NetTestActivity.class);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_net);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_rx, R.id.tv_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_rx:
                simpleDo();
                break;
            case R.id.tv_msg:
                break;
        }
    }

    //完美封装简化版
    private void simpleDo() {
        NetUtils.getNet(this, simpleOnNextListener);
    }

    //回调一一对应
    HttpOnNextListener simpleOnNextListener = new HttpOnNextListener<List<SubjectResulte>>() {
        @Override
        public Flowable onConnect(HttpPostService service) {
            return service.getAllVedioBys(true);
        }

        @Override
        public void onNext(List<SubjectResulte> subjects) {
            tvMsg.setText("网络返回：\n" + subjects.toString());
        }

        /*用户主动调用，默认是不需要覆写该方法*/
        @Override
        public void onError(Throwable e) {
            super.onError(e);
            tvMsg.setText("失败：\n" + e.toString());
        }

        /*用户主动调用，默认是不需要覆写该方法*/
        @Override
        public void onCancel() {
            super.onCancel();
            tvMsg.setText("取消請求");
        }
    };

}
