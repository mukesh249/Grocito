package com.grocito.grocito.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.grocito.grocito.R;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.ImageViewItemBinding;
import com.grocito.grocito.databinding.ZoomImageViewBinding;
import com.grocito.grocito.model.ImageModel;
import com.grocito.grocito.utils.Utils;

import java.util.ArrayList;

public class ZoomViewPagerAdapter extends PagerAdapter {

    private Context context;
    ArrayList<ImageModel> imageArray;
    private AlertDialog retryCustomAlert;

    public ZoomViewPagerAdapter(Context context, ArrayList<ImageModel> imageArray) {
        this.context = context;
        this.imageArray = imageArray;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ZoomImageViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.zoom_image_view, container, false);

        binding.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

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
