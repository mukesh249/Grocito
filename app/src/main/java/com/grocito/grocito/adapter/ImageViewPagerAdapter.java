package com.grocito.grocito.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.ProductDetail;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.ImageViewItemBinding;
import com.grocito.grocito.databinding.ImageViewLayoutBinding;
import com.grocito.grocito.model.ImageModel;
import com.grocito.grocito.utils.Utils;

import java.util.ArrayList;

public class ImageViewPagerAdapter extends PagerAdapter {

    private Context context;
    ArrayList<ImageModel> imageArray;
    private AlertDialog retryCustomAlert;

    public ImageViewPagerAdapter(Context context, ArrayList<ImageModel> imageArray) {
        this.context = context;
        this.imageArray = imageArray;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageViewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.image_view_item, container, false);

        if (ProductDetail.product_detail) {
            binding.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            binding.imageView.setOnClickListener(v -> {
                if (ProductDetail.product_detail) {
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    dialogBuilder.setCancelable(true);
                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    ImageViewLayoutBinding bindingRx = DataBindingUtil.inflate(inflater,
                            R.layout.image_view_layout,
                            null, false);

//                    Utils.setImage(context, bindingRx.imageView, WebUrls.BASE_URL + imageArray.get(position).getImage());

                    ZoomViewPagerAdapter imageViewPagerAdapter = new ZoomViewPagerAdapter(context, imageArray);
                    bindingRx.viewPager.setAdapter(imageViewPagerAdapter);
                    bindingRx.dotsIndicator.setViewPager(bindingRx.viewPager);
                    bindingRx.viewPager.setCurrentItem(position);
                    imageViewPagerAdapter.notifyDataSetChanged();

                    dialogBuilder.setView(bindingRx.getRoot());
                    retryCustomAlert = dialogBuilder.create();

                    bindingRx.close.setOnClickListener(v1 -> retryCustomAlert.dismiss());

                    retryCustomAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    retryCustomAlert.show();
                }
            });
        } else {
            binding.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        Utils.setImage(context, binding.imageView, WebUrls.BASE_URL + imageArray.get(position).getImage());
        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageArray.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
