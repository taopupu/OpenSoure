package com.open.source.base;

import com.feimeng.fdroid.base.FDApp;
import com.feimeng.fdroid.config.FDConfig;
import com.open.source.BuildConfig;

import org.litepal.LitePal;


/**
 * Application基类
 */
public class BaseApp extends FDApp {
    public static BaseApp baseApp;

    @Override
    protected void config() {
        FDConfig.SHOW_HTTP_LOG = BuildConfig.DEBUG;
        FDConfig.SHOW_LOG = BuildConfig.DEBUG;
        FDConfig.SP_NAME = "openSource";
        FDConfig.SHOW_HTTP_EXCEPTION_INFO = BuildConfig.DEBUG;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApp = this;
        LitePal.initialize(this);
//        MultiDex.install(this);
    }
}
