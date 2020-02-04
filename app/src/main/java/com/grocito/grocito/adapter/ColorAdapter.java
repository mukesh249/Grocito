package com.grocito.grocito.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.databinding.ColorItemBinding;
import com.grocito.grocito.model.SeeAllProductsModel;
import com.grocito.grocito.utils.Utils;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

//    List<SeeAllProductsModel.ProductList> arrayList;
    Context context;
    String[] arrayList;
    int selected_pos;

    public ColorAdapter(Context context, String[] arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ColorItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.color_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (arrayList.length > 0 && Utils.checkEmptyNull(arrayList[position])) {
            if (arrayList[position].contains("#")) {
                holder.binding.colorView.setBackgroundColor(Color.parseColor(arrayList[position]));
            } else {
                holder.binding.colorView.setBackgroundColor(Color.parseColor("#" + arrayList[position]));
            }

            holder.binding.colorLL.setOnClickListener(v -> {
//                selected_pos = position;
//                arrayList.get(position).
//                if (selectedpos.contains(position)) {
//                    selectedpos.remove(position);
//                    selectedcolorAry.remove(position);
//                    notifyDataSetChanged();
//                } else {
//                    selectedpos.add(selected_pos);
//                    selectedcolorAry.add(arrayList[position]);
//                    notifyDataSetChanged();
//                }

//                for (int i = 0;i<arrayList.length;i++) {
//                    if (i == selected_pos) {
//                        Log.i("color_code", arrayList[i] + "");
//                        GradientDrawable shape = (GradientDrawable) (context.getResources().getDrawable(R.drawable.grey_rounded_border));
//                        shape.setStroke(4, context.getResources().getColor(R.color.colorPrimaryDark));
//                        holder.binding.colorLL.setBackgroundDrawable(shape);
//                    } else {
//                        GradientDrawable shape = (GradientDrawable) (context.getResources().getDrawable(R.drawable.grey_rounded_border));
//                        shape.setStroke(4, context.getResources().getColor(android.R.color.darker_gray));
//                        holder.binding.colorLL.setBackgroundDrawable(shape);
//                    }
//                }
                SeeAllProductsModel seeAllProduct = new SeeAllProductsModel();
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ColorItemBinding binding;

        public ViewHolder(@NonNull ColorItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

    }
}
