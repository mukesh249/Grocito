package com.grocito.grocito.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.SeeAllProduct;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.CategoryItemHomeBinding;
import com.grocito.grocito.model.HomeGsonModel;
import com.grocito.grocito.utils.Utils;

import id.zelory.compressor.Compressor;

public class CateHomeAdapter extends RecyclerView.Adapter<CateHomeAdapter.ViewHolder> {

    List<HomeGsonModel.CatDatum> arrayList;

    Context context;
    int size;

    public CateHomeAdapter(Context context, List<HomeGsonModel.CatDatum> arrayList, int size) {
        this.arrayList = arrayList;
        this.context = context;
        this.size = size;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CategoryItemHomeBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.category_item_home, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        HomeGsonModel.CatDatum bavergesModel = arrayList.get(i);
//        Utils.strikeText(viewHolder.binding.priveDisTv);
        viewHolder.binding.productName.setText(bavergesModel.name);
        String url_image = WebUrls.BASE_URL+WebUrls.Catgory_Image_URL + bavergesModel.image;
//        try {
//            URL url = new URL(url_image);
//            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            compressedImageBitmap = new Compressor(context).compressToBitmap(image);
            Utils.setImage(context, viewHolder.binding.productImage,url_image );
//        } catch(IOException e) {
//            System.out.println(e+"");
//        }


    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CategoryItemHomeBinding binding;

        public ViewHolder(@NonNull CategoryItemHomeBinding itemView) {
            super(itemView.getRoot());

            this.binding = itemView;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    context.startActivity(new Intent(context, SeeAllProduct.class)
                            .putExtra("cat_id", arrayList.get(pos).id+"")
                            .putExtra("subCatId","")
                    );

                }
            });
        }
    }
}
