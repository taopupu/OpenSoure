package com.open.source.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.open.source.widget.StrokeTransform;

/**
 * Created by 56890 on 2017/12/22.
 */

public class GlideUtil {

    public static void loadDefault(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView, RequestOptions options) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static RequestOptions optionsError(int pic) {
        return new RequestOptions().error(pic);
    }

    public static RequestOptions optionsPlaceholder(int pic) {
        return new RequestOptions().placeholder(pic);
    }

    public static RequestOptions optionsTransform(Context context) {
        return new RequestOptions().transform(new StrokeTransform(context));
    }

    public static void loadTransform(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().transform(new StrokeTransform(context)))
                .into(imageView);
    }

    public static RequestOptions options(Context context, int errorResId, int pholderResId) {
        RequestOptions requestOptions = new RequestOptions();
        if (context != null) requestOptions.transform(new StrokeTransform(context));
        if (errorResId != 0) requestOptions.error(errorResId);
        if (pholderResId != 0) requestOptions.placeholder(pholderResId);
        return requestOptions;
    }
}
