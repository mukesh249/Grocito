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

public class FilterCatAdapter extends RecyclerView.Adapter<FilterCatAdapter.ViewHolder> {

   List<FilterCatModel.SubCatDatum> arrayList;

    private int checkedPosition = -1;
    public static String cate_id="";

    Context context;

    public FilterCatAdapter(Context context, List<FilterCatModel.SubCatDatum> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FilterCatItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                ,R.layout.filter_cat_item,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FilterCatModel.SubCatDatum datum = arrayList.get(i);
        viewHolder.binding.nameTv.setText(Utils.FirstLatterCap(String.format("%s",datum.name)));
//        Glide.with(context).load()

        if (checkedPosition == i) {
            if (datum.id != null)
                cate_id = datum.id.toString();
            viewHolder.binding.llout.setBackground(context.getResources().getDrawable(R.drawable.round_conner_back_shad));
        } else {
            viewHolder.binding.llout.setBackground(null);
        }

        viewHolder.itemView.setOnClickListener(v -> {
            viewHolder.binding.llout.setBackground(context.getResources().getDrawable(R.drawable.round_conner_back_shad));
            if (checkedPosition != i) {
                notifyItemChanged(checkedPosition);
                if (datum.id != null)
                    cate_id = datum.id.toString();
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
