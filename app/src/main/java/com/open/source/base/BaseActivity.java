package com.open.source.base;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.feimeng.fdroid.base.FDActivity;
import com.feimeng.fdroid.mvp.base.FDPresenter;
import com.feimeng.fdroid.mvp.base.FDView;
import com.feimeng.fdroid.utils.ActivityPageManager;
import com.open.source.R;
import com.open.source.widget.SystemBarTintManager;
import com.open.source.widget.dialog.LoadingDialog;

import java.util.Stack;

import butterknife.ButterKnife;

/**
 * Activity基类
 */
public abstract class BaseActivity<V extends FDView, P extends FDPresenter<V>> extends FDActivity<V, P> {
    public static int screenWidth;
    public static int screenHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        // 进行View绑定
        ButterKnife.bind(this);
        getWindowWH();
        setSystemBarColor(getResources().getColor(R.color.colorAccent));
    }

    public void getWindowWH() {
        Display mDisplay = getWindowManager().getDefaultDisplay();
        DisplayMetrics metric = new DisplayMetrics();
        mDisplay.getMetrics(metric);
        if (screenWidth == 0)
            screenWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        if (screenHeight == 0)
            screenHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight();
    }

    @Override
    protected Dialog drawDialog(String message) {
        return new LoadingDialog(this, R.style.CustomProgressDialog);
    }

    public static double getStatusBarHeight(Context context) {
        return Math.ceil(25 * context.getResources().getDisplayMetrics().density);
    }

    /**
     * 设置状态栏颜色
     */
    public void setSystemBarColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager mTintManager;
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(false);
            mTintManager.setTintColor(color);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            } catch (IllegalArgumentException ignore) {
                Log.e("BaseActivity", "Invalid hexString argument, use f.i. '#999999'a");
            } catch (Exception ignore) {
                Log.w("BaseActivity", "Method window.setStatusBarColor not found for SDK level " + Build.VERSION.SDK_INT);
            }
        }
    }

    //关闭软件盘
    public void closeInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public static void toHomeActivity() {
        Stack<FDActivity> all = ActivityPageManager.getInstance().all();
        FDActivity fdActivity;
        while ((fdActivity = all.peek()) != null) {
//            if (fdActivity instanceof HomeActivity) {
//                return;
//            }
            all.pop();
            fdActivity.finish();
        }
    }
}
