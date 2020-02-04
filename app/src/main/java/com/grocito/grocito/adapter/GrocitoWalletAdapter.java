package com.grocito.grocito.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.databinding.GrocitoWalletItemBinding;
import com.grocito.grocito.model.GrocitoWalletModel;
import com.grocito.grocito.utils.Utils;

import java.text.ParseException;
import java.util.List;

public class GrocitoWalletAdapter extends RecyclerView.Adapter<GrocitoWalletAdapter.ViewHolder> {

    List<GrocitoWalletModel.GrocitoWallet> arrayList;

    Context context;

    public GrocitoWalletAdapter(Context context, List<GrocitoWalletModel.GrocitoWallet> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        GrocitoWalletItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.grocito_wallet_item,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GrocitoWalletModel.GrocitoWallet homeCatProductModel = arrayList.get(i);

        try {
            viewHolder.binding.dateTimeTv.setText(Utils.formatDateFromDateString(
                    "yyyy-MM-dd HH:mm:ss",
                    "dd-MMM-yyyy", homeCatProductModel.createdAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.binding.reasonTv.setText(homeCatProductModel.reason);

//        if (homeCatProductModel.paymentType.matches("_")) {
            String string = homeCatProductModel.paymentType.replaceAll("_", " ");
            viewHolder.binding.productNameTv.setText(Utils.FirstLatterCap(string));
//        }

        viewHolder.binding.amountTv.setText(String.format("Amount : ₹%s",homeCatProductModel.amount));
        if (homeCatProductModel.type.equals("withdraw")){
            viewHolder.binding.statusTv.setText(String.format("Status : %s","Dr"));
        }else {
            viewHolder.binding.statusTv.setText(String.format("Status : %s","Cr"));
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        GrocitoWalletItemBinding binding;
        public ViewHolder(@NonNull GrocitoWalletItemBinding itemView) {
            super(itemView.getRoot());

            this.binding = itemView;


            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    context.startActivity(new Intent(context, ProductDetail.class)
//                            .putExtra("product_slug",arrayList.get(getAdapterPosition()).slug));
                }
            });
        }
    }
}
