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
import com.grocito.grocito.databinding.GoogleAddressItemBinding;
import com.grocito.grocito.databinding.SearchItemBinding;
import com.grocito.grocito.model.AddressModelGoogle;
import com.grocito.grocito.model.SearchModel;
import com.grocito.grocito.utils.Utils;

import java.util.List;

public class GoogleSearchAddressAdapter extends RecyclerView.Adapter<GoogleSearchAddressAdapter.ViewHolder> {

   List<AddressModelGoogle.Prediction> arrayList;

    Context context;

    public GoogleSearchAddressAdapter(Context context, List<AddressModelGoogle.Prediction> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        GoogleAddressItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                ,R.layout.google_address_item,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AddressModelGoogle.Prediction datum = arrayList.get(i);
        viewHolder.binding.firstAddress.setText(Utils.FirstLatterCap(String.format("%s",datum.structuredFormatting.secondaryText)));
        viewHolder.binding.secondAddress.setText(Utils.FirstLatterCap(String.format("%s",datum.description)));
//        Glide.with(context).load()

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (datum.searchType.equals("category")){
//                    context.startActivity(new Intent(context, SeeAllProduct.class)
//                            .putExtra("cat_id", datum.id)
//                            .putExtra("subCatId","")
//                    );
//                }else if (datum.searchType.equals("product")){
//                    context.startActivity(new Intent(context, ProductDetail.class)
//                            .putExtra("product_slug",datum.id));
//                }else {
//                    Utils.Toast(context,"this product not available");
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        GoogleAddressItemBinding binding;
        public ViewHolder(@NonNull GoogleAddressItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
