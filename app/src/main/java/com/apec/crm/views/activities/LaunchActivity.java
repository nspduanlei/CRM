package com.apec.crm.views.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v13.app.ActivityCompat;
import android.view.View;

import com.apec.crm.app.MyApplication;
import com.apec.crm.utils.DeviceUtils;
import com.apec.crm.utils.SPUtils;
import com.apec.crm.utils.StringUtils;
import com.apec.crm.views.activities.core.BaseActivity;

/**
 * Created by duanlei on 16/9/27.
 * 启动页
 */
public class LaunchActivity extends BaseActivity {
    @Override
    protected void setUpContentView() {
        setContentView(-1, -1, MODE_NONE);
    }

    private static final int REQUEST_PERMISSIONS = 1;
    private static final String FRAGMENT_DIALOG = "dialog";

    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initUi(Bundle savedInstanceState) {
        //隐藏状态栏 和 导航栏
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);


        //请求权限
        if (DeviceUtils.hasM() && !hasPermissionsGranted(PERMISSIONS)) {
            requestVideoPermissions();
            return;
        }

        pageIntent();
    }

    private void pageIntent() {
        new Handler().postDelayed(() -> {
//            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
//            startActivity(intent);
            if (StringUtils.isNullOrEmpty((String) SPUtils.get(this, SPUtils.TOKEN, ""))) {
                Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
            }

            this.finish();
        }, 2000);
    }


    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    private boolean hasPermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestVideoPermissions() {
        if (shouldShowRequestPermissionRationale(PERMISSIONS)) {
            new ConfirmationDialog().show(getFragmentManager(), FRAGMENT_DIALOG);
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS);
        }
    }

    public static class ConfirmationDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage("应用程序要获取用户的设备id")
                    .setPositiveButton(android.R.string.ok,
                            (dialog, which) ->
                                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS,
                                            REQUEST_PERMISSIONS))
                    .setNegativeButton(android.R.string.cancel,
                            (dialog, which) -> getActivity().finish())
                    .create();
        }
    }

    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length == PERMISSIONS.length) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        ErrorDialog.newInstance("应用需要获取设备信息提升用户体验")
                                .show(getFragmentManager(), FRAGMENT_DIALOG);
                        break;
                    }
                    pageIntent();
                }
            } else {
                ErrorDialog.newInstance("应用需要获取设备信息提升用户体验")
                        .show(getFragmentManager(), FRAGMENT_DIALOG);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public static class ErrorDialog extends DialogFragment {

        private static final String ARG_MESSAGE = "message";

        public static ErrorDialog newInstance(String message) {
            ErrorDialog dialog = new ErrorDialog();
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            dialog.setArguments(args);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Activity activity = getActivity();
            return new AlertDialog.Builder(activity)
                    .setMessage(getArguments().getString(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> activity.finish())
                    .create();
        }

    }

}
