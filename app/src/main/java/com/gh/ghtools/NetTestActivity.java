package com.gh.ghtools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gh.ghtools.base.BaseActivity;
import com.gh.ghtools.net.HttpOnNextListener;
import com.gh.ghtools.net.HttpPostService;
import com.gh.ghtools.net.NetUtils;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author: gh
 * @description:
 * @date: 2019-07-13.
 * @from:
 */
public class NetTestActivity extends BaseActivity implements View.OnClickListener  {

    private TextView tvMsg;
    private ImageView img;

    public static void access(Context context) {

        Intent intent = new Intent();
        intent.setClass(context, NetTestActivity.class);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_net);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        findViewById(R.id.btn_simple).setOnClickListener(this);
        findViewById(R.id.btn_rx).setOnClickListener(this);
        findViewById(R.id.btn_rx_mu_down).setOnClickListener(this);
        findViewById(R.id.btn_rx_uploade).setOnClickListener(this);
        img = (ImageView) findViewById(R.id.img);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rx:
                simpleDo();
                break;
        }
    }

    //    完美封装简化版
    private void simpleDo() {
//        SubjectPostApi postEntity = new SubjectPostApi(simpleOnNextListener, this);
//        postEntity.setAll(true);
//        HttpManager manager = HttpManager.getInstance();
//        manager.doHttpDeal(new BaseApi(simpleOnNextListener, this) {
//            @Override
//            public Flowable getObservable(Retrofit retrofit) {
//                HttpPostService service = retrofit.create(HttpPostService.class);
//                return service.getAllVedioBys(true);
//            }
//        });
        NetUtils.getNet(this,simpleOnNextListener);
    }

    //   回调一一对应
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
