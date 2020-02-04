package com.grocito.grocito.adapter;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.model.NotificationModel;
import com.grocito.grocito.R;
import com.grocito.grocito.databinding.NotificationItemBinding;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<NotificationModel.Notifydatum> arrayList = new ArrayList<>();
    NotificationModel notificationModel;
    Context context;

    public NotificationAdapter(Context context, List<NotificationModel.Notifydatum> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        NotificationItemBinding notificationItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.notification_item,viewGroup,false);
        return new ViewHolder(notificationItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        NotificationModel.Notifydatum model = this.arrayList.get(i);
        viewHolder.binding.dateTimeTv.setText(model.createdAt);
        viewHolder.binding.offerName.setText(model.title);
        viewHolder.binding.offerName.setTextColor(context.getResources().getColor(R.color.black));
        viewHolder.binding.productName.setText(Html.fromHtml(model.description));
        Glide.with(context).load(WebUrls.BASE_URL+WebUrls.Notification_Image_URL+model.image).placeholder(R.drawable.logo).into(viewHolder.binding.notiImage);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        NotificationItemBinding binding;
        int item = 1;

        public ViewHolder(NotificationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    context.startActivity(new Intent(context, ProductDetail.class));
//                }
//            });

        }
    }
}
