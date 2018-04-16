package com.open.source.mvp.base.welcome;

import com.feimeng.fdroid.mvp.base.FDPresenter;
import com.feimeng.fdroid.mvp.base.FDView;
import com.open.source.data.Constants;

public interface WelcomeContract {
    interface View extends FDView {
        void welcome(Constants.START_MODE start_mode);
    }

    abstract class Presenter extends FDPresenter<View> {
        public abstract void welcome();
    }
}
