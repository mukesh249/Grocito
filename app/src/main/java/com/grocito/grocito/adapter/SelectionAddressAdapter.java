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
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.GoogleAddressItemBinding;
import com.grocito.grocito.databinding.SearchItemBinding;
import com.grocito.grocito.model.SearchModel;
import com.grocito.grocito.model.SelectedAddressModel;
import com.grocito.grocito.utils.Utils;

import java.util.List;

public class SelectionAddressAdapter extends RecyclerView.Adapter<SelectionAddressAdapter.ViewHolder> {

   List<SelectedAddressModel.Datum> arrayList;

    Context context;

    public SelectionAddressAdapter(Context context, List<SelectedAddressModel.Datum> arrayList) {
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
        SelectedAddressModel.Datum datum = arrayList.get(i);
        viewHolder.binding.firstAddress.setText(Utils.FirstLatterCap(datum.type));
        String address = String.format("%s,%s %s %s %s(%s)",
                datum.name ,
                datum.house ,
                datum.street,
                datum.city ,
                datum.state ,
                datum.pincode);
        viewHolder.binding.secondAddress.setText(address);
//        Glide.with(context).load()

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPrefManager.setAddress(Constrants.DeliveryAddress,address);
//                Utils.Toast(context,"this Address set as your default address");
////                if (datum.searchType.equals("category")){
////                    context.startActivity(new Intent(context, SeeAllProduct.class)
////                            .putExtra("cat_id", datum.id)
////                            .putExtra("subCatId","")
////                    );
////                }else if (datum.searchType.equals("product")){
////                    context.startActivity(new Intent(context, ProductDetail.class)
////                            .putExtra("product_slug",datum.id));
////                }else {
////                    Utils.Toast(context,"this product not available");
////                }
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
