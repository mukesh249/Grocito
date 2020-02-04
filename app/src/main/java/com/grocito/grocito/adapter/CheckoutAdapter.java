package com.grocito.grocito.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.AddNewAddress;
import com.grocito.grocito.activities.Payment;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.AddressItemBinding;
import com.grocito.grocito.databinding.OtherItemBinding;
import com.grocito.grocito.model.CheckOutModel;
import com.grocito.grocito.utils.Utils;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> implements WebCompleteTask {

    List<CheckOutModel.AddressList> arrayList;

    Context context;
    int rawpos;

    private int checkedPosition = -1;
    public static String seleted_address_id = "";
    public static double deliveryAmt;

    public CheckoutAdapter(Context context, List<CheckOutModel.AddressList> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AddressItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.address_item, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CheckOutModel.AddressList addressList = arrayList.get(i);

        viewHolder.binding.addrtype.setText(Utils.FirstLatterCap(addressList.name)+"("+addressList.type+")");
        viewHolder.binding.addressTv.setText(String.format("%s %s %s %s(%s)",
                addressList.house ,
                addressList.street,
                addressList.city ,
                addressList.state ,
                addressList.pincode)
        );
//        if (checkedPosition == -1) {
//            viewHolder.binding.SelectAddrIv.setVisibility(View.GONE);
//        } else {
            if (checkedPosition == i) {
                viewHolder.binding.SelectAddrIv.setVisibility(View.VISIBLE);
                seleted_address_id = addressList.id.toString();
                Payment.getInstance().deliveryAmt(seleted_address_id);
//                Utils.Toast(context,seleted_address_id);
            } else {
                viewHolder.binding.SelectAddrIv.setVisibility(View.GONE);
            }
//        }

        viewHolder.itemView.setOnClickListener(view -> {
            viewHolder.binding.SelectAddrIv.setVisibility(View.VISIBLE);
            if (checkedPosition != i) {
                notifyItemChanged(checkedPosition);
                seleted_address_id = addressList.id.toString();
//                Utils.Toast(context,seleted_address_id);
                Payment.getInstance().deliveryAmt(seleted_address_id);
                checkedPosition = i;
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AddressItemBinding binding;

        public ViewHolder(@NonNull AddressItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.deleteIv.setOnClickListener(v -> deleteAddress(getAdapterPosition()));
            binding.editIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddNewAddress.class);
                    intent.putExtra("id",arrayList.get(getLayoutPosition()).id+"");
                    intent.putExtra("type",arrayList.get(getLayoutPosition()).type+"");
                    intent.putExtra("name",arrayList.get(getLayoutPosition()).name+"");
                    intent.putExtra("mobile",arrayList.get(getLayoutPosition()).mobile+"");
                    intent.putExtra("address",arrayList.get(getLayoutPosition()).address+"");
                    intent.putExtra("house",arrayList.get(getLayoutPosition()).house+"");
                    intent.putExtra("street",arrayList.get(getLayoutPosition()).street+"");
                    intent.putExtra("city",arrayList.get(getLayoutPosition()).city+"");
                    intent.putExtra("landmark",arrayList.get(getLayoutPosition()).landmark+"");
                    intent.putExtra("state",arrayList.get(getLayoutPosition()).state+"");
                    intent.putExtra("pincode",arrayList.get(getLayoutPosition()).pincode+"");
                    intent.putExtra("lattitude",arrayList.get(getLayoutPosition()).lattitude+"");
                    intent.putExtra("longitude",arrayList.get(getLayoutPosition()).longitude+"");
                    intent.putExtra("is_default",arrayList.get(getLayoutPosition()).isDefault+"");
                    context.startActivity(intent);
                }
            });
        }
    }

    public void deleteAddress(int pos) {
        rawpos = pos;
        HashMap objectNew = new HashMap();
        objectNew.put("address_id", arrayList.get(pos).id+"");
        Log.i("DeleteUserAddress_obj", objectNew + "");
        new WebTask(context, WebUrls.BASE_URL + WebUrls.DeleteUserAddress, objectNew, this, RequestCode.CODE_DeleteUserAddress, 5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_DeleteUserAddress == taskcode) {
            Log.i("DeleteUserAddress_res", response);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status_code") == 1){
                    Utils.Toast(context,jsonObject.optString("message"));
                    arrayList.remove(rawpos);
                    notifyItemRemoved(rawpos);
                    seleted_address_id = "";
                    Payment.getInstance().deliveryChargeRefresh();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
