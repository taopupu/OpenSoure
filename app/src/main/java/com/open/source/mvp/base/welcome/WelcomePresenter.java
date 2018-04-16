package com.open.source.mvp.base.welcome;

import com.feimeng.fdroid.utils.SP;
import com.open.source.data.Constants;

public class WelcomePresenter extends WelcomeContract.Presenter {

    @Override
    public void welcome() {
        SP.put(Constants.SP_IS_WELCOME, false);
        if (isActive()) mView.welcome(Constants.START_MODE.NORMAL);
    }
}
