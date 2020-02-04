package com.grocito.grocito.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.SeeAllProduct;
import com.grocito.grocito.common.MyApplication;
import com.grocito.grocito.databinding.HomeItemBinding;
import com.grocito.grocito.model.HomeGsonModel;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

  List<HomeGsonModel.CatWithProduct> arrayList;

    Context context;

    public HomeAdapter(Context context, List<HomeGsonModel.CatWithProduct> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        HomeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.home_item, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        HomeGsonModel.CatWithProduct homeModel = arrayList.get(i);
        viewHolder.binding.catName.setText(homeModel.catName);

        viewHolder.binding.catRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        MyApplication.RecyclerView(viewHolder.binding.catRecyclerView);
        HomeCatProductAdapter homeCatProductAdapter = new HomeCatProductAdapter(context, homeModel.productListData);
        viewHolder.binding.catRecyclerView.setAdapter(homeCatProductAdapter);
        homeCatProductAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        HomeItemBinding binding;

        public ViewHolder(@NonNull HomeItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

            binding.seeAll.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                context.startActivity(new Intent(context, SeeAllProduct.class)
                        .putExtra("cat_id", arrayList.get(pos).catId+"")
                );

            });
        }
    }
}
