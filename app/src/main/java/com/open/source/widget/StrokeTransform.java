package com.open.source.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * 圆角描边图片
 * Created by Administrator turn_on 2017/2/17.
 */
public class StrokeTransform extends BitmapTransformation {
    public StrokeTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return getRoundBitmap(pool, toTransform);
    }

    private Bitmap getRoundBitmap(BitmapPool pool, Bitmap source) {
        int width = source.getWidth() + 1;
        int height = source.getHeight() + 1;

        Bitmap output = pool.get(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());
        RectF rectF = new RectF(rect);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#797979"));
        canvas.drawRoundRect(rectF, 6, 6, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, rect, rect, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0.5f);
        paint.setXfermode(null);
        canvas.drawRoundRect(rectF, 6, 6, paint);
        return output;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}
