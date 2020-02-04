package com.grocito.grocito.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.ProductDetail;
import com.grocito.grocito.activities.SeeAllProduct;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.ImageViewItemBinding;
import com.grocito.grocito.model.HomeGsonModel;
import com.grocito.grocito.model.ImageModel;
import com.grocito.grocito.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private Context context;
    List<HomeGsonModel.SliderList> imageArray;

    public SliderPagerAdapter(Context context, List<HomeGsonModel.SliderList> imageArray) {
        this.context = context;
        this.imageArray = imageArray;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageViewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.image_view_item, container, false);

        if (ProductDetail.product_detail){
            binding.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }else {
            binding.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        Utils.setImage(context, binding.imageView, WebUrls.BASE_URL+WebUrls.Slider_Image_URL + imageArray.get(position).images);


        binding.imageView.setOnClickListener(v -> {
            if (imageArray.get(position).linkType.equals("category")){
                if (imageArray.get(position).mainCategoryp!=null){
                    context.startActivity(new Intent(context, SeeAllProduct.class)
                            .putExtra("cat_id", imageArray.get(position).mainCategoryp.id+"")
                            .putExtra("subCatId","")
                    );
                }
            }else if (imageArray.get(position).linkType.equals("product")){
                context.startActivity(new Intent(context, ProductDetail.class)
                        .putExtra("product_slug",imageArray.get(position).product.slug+""));
            }

        });

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
