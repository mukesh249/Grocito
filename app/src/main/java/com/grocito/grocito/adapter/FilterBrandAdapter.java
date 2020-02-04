package com.grocito.grocito.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.databinding.FilterCatItemBinding;
import com.grocito.grocito.model.FilterCatModel;
import com.grocito.grocito.utils.Utils;

import java.util.List;

public class FilterBrandAdapter extends RecyclerView.Adapter<FilterBrandAdapter.ViewHolder> {

    List<FilterCatModel.FilterBrand> arrayList;
    public static String brand_id = "";
    Context context;
    private int checkedPosition = -1;

    public FilterBrandAdapter(Context context, List<FilterCatModel.FilterBrand> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FilterCatItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                , R.layout.filter_cat_item, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FilterCatModel.FilterBrand datum = arrayList.get(i);
        if (datum.brand != null && datum.brand.name != null && !datum.brand.name.equalsIgnoreCase("none")) {
            viewHolder.binding.nameTv.setVisibility(View.VISIBLE);
            viewHolder.binding.llout.setVisibility(View.VISIBLE);
            viewHolder.binding.nameTv.setText(Utils.FirstLatterCap(String.format("%s", datum.brand.name)));
        } else {
            viewHolder.binding.llout.setVisibility(View.GONE);
        }
//        Glide.with(context).load()

        if (checkedPosition == i) {
            if (datum.brand != null)
                brand_id = datum.brand.id.toString();
            viewHolder.binding.llout.setBackground(context.getResources().getDrawable(R.drawable.round_conner_back_shad));
        } else {
            viewHolder.binding.llout.setBackground(null);
        }

        viewHolder.itemView.setOnClickListener(v -> {
            viewHolder.binding.llout.setBackground(context.getResources().getDrawable(R.drawable.round_conner_back_shad));
            if (checkedPosition != i) {
                notifyItemChanged(checkedPosition);
                if (datum.brand != null)
                    brand_id = datum.brand.id.toString();
                checkedPosition = i;
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FilterCatItemBinding binding;

        public ViewHolder(@NonNull FilterCatItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
