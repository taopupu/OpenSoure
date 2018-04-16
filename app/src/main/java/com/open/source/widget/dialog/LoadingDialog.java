package com.open.source.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.open.source.R;


/**
 * Created by 56890 turn_on 2017/8/4.
 */

public class LoadingDialog extends Dialog {
    private Animation rotate;

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadling);
        this.setCanceledOnTouchOutside(false);
        ImageView loading = (ImageView) findViewById(R.id.loading);
        rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim);
        loading.setAnimation(rotate);
        loading.startAnimation(rotate);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (rotate != null)
            rotate.cancel();
    }
}
