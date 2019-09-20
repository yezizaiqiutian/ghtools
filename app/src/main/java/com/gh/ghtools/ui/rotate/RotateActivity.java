package com.gh.ghtools.ui.rotate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.gh.ghtools.R;
import com.gh.ghtools.base.BaseActivity;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: gh
 * @description:
 * @date: 2019-09-20.
 * @from:
 */
public class RotateActivity extends BaseActivity {

    @BindView(R.id.btn_rotate)
    Button btnRotate;
    @BindView(R.id.pie_view)
    PieView pieView;
    private boolean isRunning = false;

    public static void access(Context context) {

        Intent intent = new Intent();
        intent.setClass(context, RotateActivity.class);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        pieView.setItems(new String[]{"1","2","3"});

        pieView.setListener(new PieView.RotateListener() {
            @Override
            public void value(String s) {
                isRunning = false;
                new AlertDialog.Builder(RotateActivity.this)
                        .setTitle("鹿死谁手呢？")
                        .setMessage(s)
                        .setIcon(R.mipmap.ic_launcher)
                        .setNegativeButton("退出", null)
                        .show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @OnClick({R.id.btn_rotate, R.id.pie_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_rotate:
                if (!isRunning) {
                Random random = new Random();
                pieView.rotate(random.nextInt(pieView.getItems().length));
//                pieView.rotate(1);
            }
            isRunning = true;
                break;
            case R.id.pie_view:
                break;
        }
    }
}
