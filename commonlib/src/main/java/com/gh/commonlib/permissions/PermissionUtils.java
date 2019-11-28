package com.gh.commonlib.permissions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.gh.commonlib.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yanzhenjie.permission.runtime.PermissionRequest;

import java.util.List;

/**
 * @author: gh
 * @description:
 * @date: 2019/4/26.
 * @from:
 */
public class PermissionUtils {

    public static boolean isToCheckLocation;

    //启动页权限请求,所有权限
    public static final int FLAG_PERMISSION_ALL = 1;
    //APP清单文件注册的所有危险权限
    public static final String[] PERMISSIONS_ALL = {
            Permission.CAMERA,//相机
            Permission.ACCESS_FINE_LOCATION,//位置
            Permission.ACCESS_COARSE_LOCATION,//位置
            Permission.RECORD_AUDIO,//麦克风
            Permission.READ_PHONE_STATE,//手机
            Permission.READ_EXTERNAL_STORAGE,//存储卡
            Permission.WRITE_EXTERNAL_STORAGE,//存储卡
            Permission.CALL_PHONE,//拨打电话
    };

    //相机权限
    public static final int FLAG_PERMISSION_CAMERA = 2;
    public static final String[] PERMISSION_CAMERA = {
            Permission.CAMERA,
            Permission.READ_EXTERNAL_STORAGE,//存储卡
            Permission.WRITE_EXTERNAL_STORAGE,//存储卡
    };

    //存储卡
    //相机权限
    public static final int FLAG_PERMISSION_SD = 3;
    public static final String[] PERMISSION_SD = {
            Permission.READ_EXTERNAL_STORAGE,//存储卡
            Permission.WRITE_EXTERNAL_STORAGE,//存储卡
    };

    //麦克风
    public static final int FLAG_PERMISSION_AUDIO = 4;
    public static final String[] PERMISSION_AUDIO = {
            Permission.RECORD_AUDIO,//麦克风
    };

    //相机权限
    public static final int FLAG_PERMISSION_VIDEO = 5;
    public static final String[] PERMISSION_VIDEO = {
            Permission.CAMERA,
            Permission.READ_EXTERNAL_STORAGE,//存储卡
            Permission.WRITE_EXTERNAL_STORAGE,//存储卡
            Permission.RECORD_AUDIO,//麦克风
    };

    //位置
    public static final int FLAG_PERMISSION_LOCATION = 6;
    public static final String[] PERMISSION_LOCATION = {
            Permission.ACCESS_FINE_LOCATION,//位置
            Permission.ACCESS_COARSE_LOCATION,//位置
    };

    //手机
    public static final int FLAG_PERMISSION_STATE = 7;
    public static final String[] PERMISSION_STATE = {
            Permission.READ_PHONE_STATE,//手机
    };

    //手机直接拨打电话
    public static final int FLAG_PERMISSION_CALL=8;
    public static final String[] PERMISSION_CALL = {
            Permission.CALL_PHONE,//手机
    };


    public static void toCheckPermissions(Activity activity, boolean isShowD1, boolean isShowD2, int flag, OnPermissionListener listener, String... permissions) {
        PermissionRequest permission = AndPermission.with(activity)
                .runtime()
                .permission(permissions);
        if (isShowD1) {
            permission.rationale(new RuntimeRationale());
        }
        permission.onGranted(new Action<List<String>>() {
            @Override
            public void onAction(List<String> permissions) {
                if (listener != null) {
                    listener.onPermission(true, flag);
                }
            }
        })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (!isShowD2) {
                            listener.onPermission(false, flag);
                            return;
                        }
                        List<String> permissionNames = Permission.transformText(activity, permissions);
                        if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
                            showSettingDialog(activity, permissions, flag, listener);
                        } else {
                            Toast.makeText(activity,activity.getString(R.string.permission_failed, TextUtils.join(" ", permissionNames)),Toast.LENGTH_SHORT).show();
                            if (listener != null) {
                                listener.onPermission(false, flag);
                            }
                        }
                    }
                })
                .start();
    }

    public static void showSettingDialog(Context context, final List<String> permissions, int flag, OnPermissionListener listener) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.permission_always_failed,
                TextUtils.join(" ", permissionNames));

        AlertDialog dialog = new AlertDialog.Builder(context).setCancelable(false)
                .setTitle(R.string.permission_dialog_title)
                .setMessage(message)
                .setPositiveButton(R.string.permission_dialog_setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission(context, flag);
                    }
                })
                .setNegativeButton(R.string.permission_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onPermission(false, flag);
                        }
                    }
                })
                .show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.black));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.black));
    }

    /**
     * Set permissions.
     */
    private static void setPermission(Context context, int flag) {
        AndPermission.with(context).runtime().setting().start(flag);
    }

    public interface OnPermissionListener {
        void onPermission(boolean isSuccess, int flag);
    }

    public static void setIsToCheckLocation(boolean isToCheckLocation) {
        PermissionUtils.isToCheckLocation = isToCheckLocation;
    }

    public static boolean isToCheckLocation() {
        return isToCheckLocation;
    }
}
