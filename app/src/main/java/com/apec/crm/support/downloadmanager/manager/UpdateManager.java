package com.apec.crm.support.downloadmanager.manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.apec.crm.support.downloadmanager.DownLoadService;

/**
 * Created by zs on 2016/7/7.
 */
public class UpdateManager {

    private Context mContext;

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate(String versionInfo) {
        // 显示提示对话
        showNoticeDialog(versionInfo);
    }

    /**
     * 显示更新对话框
     *
     * @param version_info
     */
    private void showNoticeDialog(String version_info) {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("更新提示");
        builder.setMessage(version_info);
        // 更新
        builder.setPositiveButton("立即更新", (dialog, which) -> {
            dialog.dismiss();
            mContext.startService(new Intent(mContext, DownLoadService.class));
        });
        // 稍后更新
        builder.setNegativeButton("以后更新", (dialog, which) -> dialog.dismiss());
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }
}
