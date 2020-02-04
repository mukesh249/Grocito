package com.grocito.grocito.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.ProductDetail;
import com.grocito.grocito.activities.SeeAllProduct;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.OfferItemHomeBinding;
import com.grocito.grocito.model.HomeGsonModel;
import com.grocito.grocito.utils.Utils;

import java.util.List;

public class OfferHomeAdapter extends RecyclerView.Adapter<OfferHomeAdapter.ViewHolder> {

    List<HomeGsonModel.FirstOfferBanner> arrayList;

    Context context;
    int size;

    public OfferHomeAdapter(Context context, List<HomeGsonModel.FirstOfferBanner> arrayList, int size) {
        this.arrayList = arrayList;
        this.context = context;
        this.size = size;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        OfferItemHomeBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.offer_item_home, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        HomeGsonModel.FirstOfferBanner bavergesModel = arrayList.get(i);
//        Utils.strikeText(viewHolder.binding.priveDisTv);
        viewHolder.binding.productName.setText(bavergesModel.title);
        Utils.setImage(context, viewHolder.binding.productImage, WebUrls.BASE_URL+WebUrls.Banner_Image_URL + bavergesModel.images);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        OfferItemHomeBinding binding;

        public ViewHolder(@NonNull OfferItemHomeBinding itemView) {
            super(itemView.getRoot());

            this.binding = itemView;
            binding.getRoot().setOnClickListener(view -> {
                int pos = getAdapterPosition();
                if (arrayList.get(pos).linkType.equals("category")){
                    if (arrayList.get(pos).mainCategory!=null){
                        context.startActivity(new Intent(context, SeeAllProduct.class)
                                .putExtra("cat_id", arrayList.get(pos).mainCategory.id+"")
                                .putExtra("subCatId","")
                        );
                    }
                }
//                else if (arrayList.get(pos).linkType.equals("product")){
////                    context.startActivity(new Intent(context, ProductDetail.class)
////                            .putExtra("product_slug",arrayList.get(pos).product.slug+""));
////                }
//                context.startActivity(new Intent(context, SeeAllProduct.class)
//                        .putExtra("cat_id", arrayList.get(pos).mainCategory.id+"")
//                        .putExtra("subCatId","")
//                );

            });
        }
    }
}
