package com.grocito.grocito.adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.grocito.grocito.activities.ProductDetail;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.R;
import com.grocito.grocito.model.HomeGsonModel;
import com.grocito.grocito.utils.Utils;
import com.grocito.grocito.databinding.BavergesItemBinding;

public class HomeCatProductAdapter extends RecyclerView.Adapter<HomeCatProductAdapter.ViewHolder> {

    List<HomeGsonModel.ProductListDatum> arrayList;

    Context context;

    public HomeCatProductAdapter(Context context, List<HomeGsonModel.ProductListDatum> arrayList) {
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
        HomeGsonModel.ProductListDatum homeCatProductModel = arrayList.get(i);
        Utils.strikeText(viewHolder.binding.priveDisTv);
        viewHolder.binding.productName.setText(homeCatProductModel.pName);
        Utils.setImage(context,viewHolder.binding.productImage, WebUrls.BASE_URL+WebUrls.IMAGE_PRODUCT+ homeCatProductModel.pImage);
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
            binding.offTv.setVisibility(View.GONE);

            binding.getRoot().setOnClickListener(view -> context.startActivity(new Intent(context, ProductDetail.class)
                    .putExtra("product_slug",arrayList.get(getAdapterPosition()).pSlug)
                    .putExtra("product_slug",arrayList.get(getAdapterPosition()).pSlug))
            );
        }
    }
}
