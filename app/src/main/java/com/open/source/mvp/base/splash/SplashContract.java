package com.open.source.mvp.base.splash;

import com.feimeng.fdroid.mvp.base.FDPresenter;
import com.feimeng.fdroid.mvp.base.FDView;
import com.open.source.data.Constants;

public interface SplashContract {
    interface View extends FDView {
        void startComplete(Constants.START_MODE mode, String extra);
    }

    abstract class Presenter extends FDPresenter<View> {
        public abstract void start();

        public abstract void upgrade();
    }
}
