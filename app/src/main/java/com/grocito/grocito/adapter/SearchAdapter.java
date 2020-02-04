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
import com.grocito.grocito.activities.SeeAllProduct;
import com.grocito.grocito.databinding.FaqItemBinding;
import com.grocito.grocito.databinding.SearchItemBinding;
import com.grocito.grocito.model.SearchModel;
import com.grocito.grocito.utils.Utils;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

   List<SearchModel.Datum> arrayList;

    Context context;

    public SearchAdapter(Context context, List<SearchModel.Datum> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        SearchItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                ,R.layout.search_item,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SearchModel.Datum datum = arrayList.get(i);
        viewHolder.binding.searchNameTv.setText(Utils.FirstLatterCap(String.format("%s",datum.value)));
//        Glide.with(context).load()

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datum.searchType.equals("category")){
                    context.startActivity(new Intent(context, SeeAllProduct.class)
                            .putExtra("cat_id", datum.id)
                            .putExtra("subCatId","")
                    );
                }else if (datum.searchType.equals("product")){
                    context.startActivity(new Intent(context, ProductDetail.class)
                            .putExtra("product_slug",datum.id));
                }else {
                    Utils.Toast(context,"this product not available");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SearchItemBinding binding;
        public ViewHolder(@NonNull SearchItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
