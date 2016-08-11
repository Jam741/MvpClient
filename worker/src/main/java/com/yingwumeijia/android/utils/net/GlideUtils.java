package com.yingwumeijia.android.utils.net;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rx.android.jamspeedlibrary.utils.GlideCircleTransform;
import com.rx.android.jamspeedlibrary.utils.GlideRoundTransform;
import com.yingwumeijia.android.R;

/**
 * Created by Jam on 16/6/30 上午11:05.
 * Describe:Glide 图片加载工具
 */
public class GlideUtils {


    public static void loadSimpleImage(Context context, ImageView imageView, String url) {
        Glide
                .with(context)
                .load(url)
                .crossFade()
                .placeholder(R.mipmap.case_moren_pic)
                .into(imageView);
    }


    public static void loadCircleImage(Context context, ImageView imageView, String url) {
        Glide
                .with(context)
                .load(url)
                .crossFade()
                .placeholder(R.mipmap.case_moren_pic)
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }


    public static void loadRoundImage(Context context, ImageView imageView, String url, int roundDp) {
        Glide
                .with(context)
                .load(url)
                .crossFade()
                .placeholder(R.mipmap.case_moren_pic)
                .transform(new GlideRoundTransform(context, roundDp))
                .into(imageView);
    }

    public static void loadPortraitImage(Context context, ImageView imageView, String url) {
        Glide
                .with(context)
                .load(url)
                .crossFade()
                .placeholder(R.mipmap.mine_user_ywmj_circle)
                .into(imageView);
    }

    public static void loadCriclePortraitImage(Context context, ImageView imageView, String url) {
        Glide
                .with(context)
                .load(url)
                .crossFade()
                .placeholder(R.mipmap.mine_user_ywmj_circle)
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }

    public static void loadCriclePortraitImage(Context context, ImageView imageView, @DrawableRes int resId) {
        Glide
                .with(context)
                .load(resId)
                .crossFade()
                .placeholder(R.mipmap.mine_user_ywmj_circle)
                .transform(new GlideCircleTransform(context))
                .into(imageView);

        imageView.setImageResource(resId);
    }
}
