package com.grocito.grocito.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.ProductDetail;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.BavergesItemBinding;
import com.grocito.grocito.model.HomeGsonModel;
import com.grocito.grocito.utils.Utils;

import java.util.List;

public class SunProductAdapter extends RecyclerView.Adapter<SunProductAdapter.ViewHolder> {

    List<HomeGsonModel.SundayProduct> arrayList;

    Context context;

    public SunProductAdapter(Context context, List<HomeGsonModel.SundayProduct> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BavergesItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.baverges_item,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        HomeGsonModel.SundayProduct homeCatProductModel = arrayList.get(i);
        Utils.strikeText(viewHolder.binding.priveDisTv);
        viewHolder.binding.productName.setText(homeCatProductModel.name);
        Utils.setImage(context,viewHolder.binding.productImage, WebUrls.BASE_URL+WebUrls.IMAGE_PRODUCT+ homeCatProductModel.image);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        BavergesItemBinding binding;
        public ViewHolder(@NonNull BavergesItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            binding.offTv.setVisibility(View.VISIBLE);

            binding.getRoot().setOnClickListener(view -> context.startActivity(new Intent(context, ProductDetail.class)
                    .putExtra("product_slug",arrayList.get(getAdapterPosition()).slug)));
        }
    }
}
