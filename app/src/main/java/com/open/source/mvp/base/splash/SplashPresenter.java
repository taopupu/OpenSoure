package com.open.source.mvp.base.splash;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.feimeng.fdroid.upgrade.OnUpgradeListener;
import com.feimeng.fdroid.upgrade.VersionInfo;
import com.feimeng.fdroid.upgrade.VersionUpgradeManager;
import com.feimeng.fdroid.utils.L;
import com.feimeng.fdroid.utils.SP;
import com.feimeng.fdroid.utils.VersionUtils;
import com.open.source.data.Constants;
import com.open.source.data.bean.AppVersion;
import com.open.source.service.CheckVersionService;
import com.trello.rxlifecycle.android.ActivityEvent;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SplashPresenter extends SplashContract.Presenter {
    public static final long SHOW_TIME_MIN = 2000;//  等待3000毫秒
    private String extra = "";

    @Override
    public void start() {
        untilEvent(Observable.create(new Observable.OnSubscribe<Constants.START_MODE>() {
            @Override
            public void call(Subscriber<? super Constants.START_MODE> subscriber) {
                Constants.START_MODE mode;
                // 创建一些后台服务
                long startTime = System.currentTimeMillis();
                mode = doSomeThing(); // 做一些事情
                long loadingTime = System.currentTimeMillis() - startTime;
                if (loadingTime < SHOW_TIME_MIN) {
                    try {
                        Thread.sleep(SHOW_TIME_MIN - loadingTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onNext(mode);
            }
        }), ActivityEvent.DESTROY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Constants.START_MODE>() {
                    @Override
                    public void call(Constants.START_MODE startMode) {
                        if (isActive())
                            mView.startComplete(startMode, extra);
                    }
                });
    }

    private Constants.START_MODE doSomeThing() {
        initData();
        // 检查是否需要强制升级
        AppVersion appVersion = AppVersion.get();
        if (appVersion != null) {
            if (Integer.valueOf(appVersion.getCode()) > VersionUtils.getVerCode(getContext())) {
                if (appVersion.isForce()) {
                    return Constants.START_MODE.UPGRADE;
                }
            } else {
                AppVersion.clear();
            }
        }
        // 检测新版本
        getContext().startService(new Intent(getContext(), CheckVersionService.class));
        if ((boolean) SP.get(Constants.SP_IS_WELCOME, true)) {
            return Constants.START_MODE.WELCOME;
        } else {
            return Constants.START_MODE.NORMAL;
        }
    }

    private void initData() {

    }

    @Override
    public void upgrade() {
        AppVersion appVersion = AppVersion.get();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
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
                upgradeShow(dialog);
            }
        });
    }

    private void upgradeShow(final AlertDialog dialog) {
        AppVersion appVersion = AppVersion.get();
        VersionUpgradeManager manager = VersionUpgradeManager.getInstance();
        manager.showNotification(new VersionInfo("群仙·新版本", appVersion.getDescription(), appVersion.getName()));
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

}
