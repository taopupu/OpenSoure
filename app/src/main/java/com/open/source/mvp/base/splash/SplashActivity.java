package com.open.source.mvp.base.splash;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;

import com.feimeng.fdroid.utils.T;
import com.open.source.MainActivity;
import com.open.source.R;
import com.open.source.base.BaseActivity;
import com.open.source.data.Constants;
import com.open.source.mvp.base.welcome.WelcomeActivity;
import com.open.source.mvp.home.HomeActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity<SplashContract.View, SplashContract.Presenter> implements SplashContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).
                subscribeOn(Schedulers.io()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    mPresenter.start();// 启动
                } else {
                    showNeedPermission();
                }
            }
        });
    }

    @Override
    protected SplashContract.Presenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    public void startComplete(Constants.START_MODE mode, String extra) {
        switch (mode) {
            case WELCOME:
                startActivity(new Intent(this, WelcomeActivity.class));
                break;
            case NORMAL:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case LOGIN:
//                startActivity(new Intent(this, LoginActivity.class));
                break;
            case UPGRADE:
                mPresenter.upgrade();
                return;
            case FAIL:
                if (extra != null)
                    T.showL(getApplicationContext(), extra);
                break;
        }
        finish();
    }

    private void showNeedPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("需要存储权限，您可在系统设置中开启。")
                .setPositiveButton("去设置", null);
        AlertDialog dialog = builder.setCancelable(false).create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            }
        });
    }
}
