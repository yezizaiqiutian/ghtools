package com.gh.commonlib;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import com.bumptech.glide.Glide;

/**
 * @author: gh
 * @description:
 * @date: 2019-07-25.
 * @from:
 */
public class GlideUtils {

    public static void loadImage(Context context, @RawRes @DrawableRes @Nullable Integer resourceId, ImageView imageView) {
        Glide.with(context).load(resourceId).into(imageView);
    }

    public static void loadImage(Context context, @Nullable String resourceId, ImageView imageView) {
        Glide.with(context).load(resourceId).into(imageView);
    }

}
