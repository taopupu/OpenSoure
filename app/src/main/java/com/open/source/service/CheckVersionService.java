package com.open.source.service;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.feimeng.fdroid.base.FDActivity;
import com.feimeng.fdroid.mvp.model.api.bean.ApiError;
import com.feimeng.fdroid.mvp.model.api.bean.ApiFinish;
import com.feimeng.fdroid.upgrade.OnUpgradeListener;
import com.feimeng.fdroid.upgrade.VersionInfo;
import com.feimeng.fdroid.upgrade.VersionUpgradeManager;
import com.feimeng.fdroid.utils.L;
import com.feimeng.fdroid.utils.VersionUtils;
import com.open.source.api.ApiWrapper;
import com.open.source.base.BaseActivity;
import com.open.source.data.bean.AppVersion;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.open.source.mvp.base.splash.SplashPresenter.SHOW_TIME_MIN;

public class CheckVersionService extends Service {
    private CompositeSubscription compositeSubscription;// 控制Subscription订阅

    @Override
    public void onCreate() {
        compositeSubscription = new CompositeSubscription();
        versionCheck();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    private void versionCheck() {
        Subscription subscribe = ApiWrapper.getInstance().version()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<AppVersion>() {
                    @Override
                    public void call(AppVersion appVersion) {
                        try {
                            Thread.sleep(SHOW_TIME_MIN + 100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(ApiWrapper.subscriber(new ApiFinish<AppVersion>() {
                    @Override
                    public void start() {

                    }

                    @Override
                    public void success(final AppVersion appVersion) {
                        if (Integer.valueOf(appVersion.getCode()) > VersionUtils.getVerCode(getApplicationContext())) {
                            AppVersion.clear();
                            appVersion.save();
                            showNewVersion(appVersion);
                        }
                    }

                    @Override
                    public void fail(ApiError apiError, String s) {

                    }

                    @Override
                    public void stop() {
                        stopSelf();// 结束当前服务
                    }
                }));
        compositeSubscription.add(subscribe);
    }

    /**
     * 显示新版本
     */
    private void showNewVersion(AppVersion appVersion) {
        FDActivity activity = BaseActivity.getLatestActivity();
        if (activity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                    .setTitle("发现新版本")
                    .setMessage(appVersion.getDescription().replace("nn", "\n"))
                    .setPositiveButton("立即更新", null);
            if (!appVersion.isForce()) {
                builder.setNegativeButton("稍后再说", null);
            }
            builder.setCancelable(false);
            final AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setClickable(false);
                    Button button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    if (button != null) button.setVisibility(View.GONE);
                    upgrade(dialog);
                }
            });
        }
    }

    private void upgrade(final AlertDialog dialog) {
        AppVersion appVersion = AppVersion.get();
        VersionUpgradeManager manager = VersionUpgradeManager.getInstance();
        manager.setProvider("com.open.source.provider");
        manager.showNotification(new VersionInfo("群仙·新版本", appVersion.getName(), appVersion.getName()));
        manager.setOnDownloadListener(new OnUpgradeListener() {
            @Override
            public void onStart() {
                L.d("开始升级");
                if (dialog != null)
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText("准备升级...");
            }

            @Override
            public void onProgress(long totalBytes, long downloadedBytes) {
                L.d("升级进度:" + downloadedBytes + "/" + totalBytes);
                if (dialog != null)
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText("已下载" + (int) ((downloadedBytes / (float) totalBytes) * 100) + "%");
            }

            @Override
            public void onComplete() {
                L.d("升级完成");
                if (dialog != null) {
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText("准备安装");
                    Button button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    if (button != null && !button.getText().toString().isEmpty()) {
                        button.setText("关闭");
                        button.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFail() {
                L.d("升级失败");
                if (dialog != null) {
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText("升级失败");
                    Button button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    if (button != null && !button.getText().toString().isEmpty()) {
                        button.setText("关闭");
                        button.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        try {
            manager.upgrade(dialog.getContext(), "qunxian", appVersion.getUrl());
        } catch (IllegalArgumentException e) {
            AppVersion.clear();
            if (dialog != null)
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText("安装包下载失败");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
        L.i("GeneralService", "常规服务：结束");
    }

}
