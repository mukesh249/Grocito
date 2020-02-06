package com.grocito.grocito.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.SeeAllProduct;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.OtherItemBinding;
import com.grocito.grocito.model.HomeGsonModel;
import com.grocito.grocito.utils.Utils;

import java.util.List;

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.ViewHolder> {

    List<HomeGsonModel.MainCategory> arrayList ;

    int size=0;
    Context context;

    public OtherAdapter(Context context, List<HomeGsonModel.MainCategory> arrayList,int size) {
        this.arrayList = arrayList;
        this.context = context;
        this.size = size;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        OtherItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.other_item,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        HomeGsonModel.MainCategory isSpecialModel = arrayList.get(i);
        Utils.setImage(context,viewHolder.binding.productImage, WebUrls.BASE_URL+WebUrls.Catgory_Image_URL+isSpecialModel.image);
        viewHolder.binding.productImage.setOnClickListener(v ->
                context.startActivity(new Intent(context, SeeAllProduct.class)
                        .putExtra("cat_id", isSpecialModel.categoryId + "")
                        .putExtra("subCatId", isSpecialModel.id + "")
                        .putExtra("name", isSpecialModel.name)
        ));
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OtherItemBinding binding;
        public ViewHolder(@NonNull OtherItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
