package com.grocito.grocito.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.AvailablePlaceBinding;
import com.grocito.grocito.model.AvailablePlaceModel;
import com.grocito.grocito.utils.Utils;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    List<AvailablePlaceModel.Datum> arrayList;
    Context context;


    public PlaceAdapter(Context context, List<AvailablePlaceModel.Datum> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AvailablePlaceBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.available_place, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AvailablePlaceModel.Datum datum = this.arrayList.get(position);
        Utils.setImage(context,holder.binding.placeImage, WebUrls.BASE_URL+WebUrls.CityIcon+datum.icon);
        holder.binding.cityTv.setText(datum.name);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
//        return_cancel arrayList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AvailablePlaceBinding binding;

        public ViewHolder(@NonNull AvailablePlaceBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

    }
}
