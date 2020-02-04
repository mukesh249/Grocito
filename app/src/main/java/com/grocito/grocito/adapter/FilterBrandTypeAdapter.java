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
import com.grocito.grocito.model.FilterBrandTypModel;
import com.grocito.grocito.model.FilterCatModel;
import com.grocito.grocito.utils.Utils;

import java.util.List;

public class FilterBrandTypeAdapter extends RecyclerView.Adapter<FilterBrandTypeAdapter.ViewHolder> {

    List<FilterBrandTypModel.FilterBrand> arrayList;
    public static String brand_id = "";
    Context context;
    private int checkedPosition = -1;

    public FilterBrandTypeAdapter(Context context, List<FilterBrandTypModel.FilterBrand> arrayList) {
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
        FilterBrandTypModel.FilterBrand datum = arrayList.get(i);
        if (datum != null && datum.name!=null) {
            viewHolder.binding.nameTv.setVisibility(View.VISIBLE);
            viewHolder.binding.nameTv.setText(Utils.FirstLatterCap(String.format("%s", datum.name)));
        } else {
            viewHolder.binding.nameTv.setVisibility(View.GONE);
        }
//        Glide.with(context).load()

        if (checkedPosition == i) {
            if (datum != null)
                brand_id = datum.id.toString();
            viewHolder.binding.llout.setBackground(context.getResources().getDrawable(R.drawable.round_conner_back_shad));
        } else {
            viewHolder.binding.llout.setBackground(null);
        }

        viewHolder.itemView.setOnClickListener(v -> {
            viewHolder.binding.llout.setBackground(context.getResources().getDrawable(R.drawable.round_conner_back_shad));
            if (checkedPosition != i) {
                notifyItemChanged(checkedPosition);
                if (datum != null && datum.id!=null)
                    brand_id = datum.id.toString();
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
