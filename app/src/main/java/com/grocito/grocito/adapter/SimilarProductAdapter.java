package com.grocito.grocito.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.ProductDetail;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.BavergesItemBinding;
import com.grocito.grocito.databinding.SmilirItemBinding;
import com.grocito.grocito.model.ProductDetialModel;
import com.grocito.grocito.utils.Utils;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class SimilarProductAdapter extends RecyclerView.Adapter<SimilarProductAdapter.ViewHolder> {

    List<ProductDetialModel.RelatedProductList> arrayList;

    Context context;

    public SimilarProductAdapter(Context context, List<ProductDetialModel.RelatedProductList> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        SmilirItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.smilir_item,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ProductDetialModel.RelatedProductList homeCatProductModel = arrayList.get(i);
        Utils.strikeText(viewHolder.binding.priveDisTv);
        viewHolder.binding.productName.setText(homeCatProductModel.pName);
        Utils.setImage(context,viewHolder.binding.productImage, WebUrls.BASE_URL+WebUrls.IMAGE_PRODUCT+ homeCatProductModel.image);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SmilirItemBinding binding;
        public ViewHolder(@NonNull SmilirItemBinding itemView) {
            super(itemView.getRoot());

            this.binding = itemView;
            binding.getRoot().setOnClickListener(view -> context.startActivity(new Intent(context, ProductDetail.class)
                    .putExtra("product_slug",arrayList.get(getAdapterPosition()).slug)));
        }
    }
}
