package com.example.photodemo.adapters;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;

public class ImageAdapters {
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(android.R.drawable.ic_menu_gallery) // 设置占位图
                .error(android.R.drawable.ic_menu_report_image); // 设置加载失败时显示的图片

        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(requestOptions)
                .override(imageView.getWidth(), imageView.getHeight())
                .into(imageView);
    }
}
