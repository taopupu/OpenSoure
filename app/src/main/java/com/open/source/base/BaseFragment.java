package com.open.source.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feimeng.fdroid.base.FDFragmentReuse;
import com.feimeng.fdroid.mvp.base.FDPresenter;
import com.feimeng.fdroid.mvp.base.FDView;
import com.open.source.R;
import com.open.source.widget.dialog.LoadingDialog;

import butterknife.ButterKnife;

/**
 * Fragment基类
 */
public abstract class BaseFragment<V extends FDView, P extends FDPresenter<V>> extends FDFragmentReuse<V, P> {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected Dialog drawDialog(String message) {
        return new LoadingDialog(getContext(), R.style.CustomProgressDialog);
    }
}
