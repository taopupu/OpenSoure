package com.open.source.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * 判断权限工具类
 */

public class PermissionUtil {
    public static void permission(final Intent intent, final String content, final Context context, final String... permissions) {
        new RxPermissions((Activity) context).request(permissions)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            context.startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                    .setMessage("需要" + content + "权限，您可在系统设置中开启。")
                                    .setPositiveButton("去设置", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                    intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                                    context.startActivity(intent);
                                }
                            });
                        }
                    }
                });
    }

    public static void permissionForResult(final Intent intent, final int requestCode, final String content, final Context context, final String... permissions) {
        new RxPermissions((Activity) context).request(permissions)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            ((Activity) context).startActivityForResult(intent, requestCode);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                    .setMessage("需要" + content + "权限，您可在系统设置中开启。")
                                    .setPositiveButton("去设置", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                    intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                                    context.startActivity(intent);
                                }
                            });
                        }
                    }
                });
    }
}
