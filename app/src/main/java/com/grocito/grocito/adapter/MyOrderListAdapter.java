package com.grocito.grocito.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.MyOrderDetails;
import com.grocito.grocito.activities.MyOrderList;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.CancelOrderItemBinding;
import com.grocito.grocito.databinding.MyorderListItemBinding;
import com.grocito.grocito.model.MyOrderListModel;
import com.grocito.grocito.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.ViewHolder> implements WebCompleteTask {

    ArrayList<MyOrderListModel.Datum> arrayList;
    Context context;
    private CancelOrderItemBinding bindingRx;
    private AlertDialog retryCustomAlert;
    private int raw_post;

    public MyOrderListAdapter(Context context, ArrayList<MyOrderListModel.Datum> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        MyorderListItemBinding notificationItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.myorder_list_item, viewGroup, false);
        return new ViewHolder(notificationItemBinding);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MyOrderListModel.Datum datum = arrayList.get(i);
        viewHolder.binding.orderDateTv.setText(String.format("Date/Time: %s", datum.createdAt));
        viewHolder.binding.orderIdTv.setText(String.format("Order Id: %s", datum.orderId));
        viewHolder.binding.orderItemNoTv.setText(String.format("No of Items: %s", datum.orderMetaData.size()));

        if (datum.status.equals("pending")){
            viewHolder.binding.cancelBt.setVisibility(View.VISIBLE);
        }else {
            viewHolder.binding.cancelBt.setVisibility(View.GONE);
        }
        String string = datum.status.replaceAll("_", " ");

        if (datum.status.equalsIgnoreCase("delivered")){
            viewHolder.binding.orderStatusTv.setVisibility(View.VISIBLE);
            viewHolder.binding.orderStatusTv.setTextColor(context.getResources().getColor(R.color.green));
        }else if (datum.status.equalsIgnoreCase("cancelled")){
            viewHolder.binding.orderStatusTv.setVisibility(View.VISIBLE);
            viewHolder.binding.orderStatusTv.setTextColor(context.getResources().getColor(R.color.red));
        }else {
            viewHolder.binding.orderStatusTv.setVisibility(View.GONE);
        }
        viewHolder.binding.orderStatusTv.setText(String.format("Order %s", Utils.FirstLatterCap(string)));
        viewHolder.binding.orderAmtTv.setText(String.format("Total Amount: ₹%.0f", Double.parseDouble(datum.totalAmount)+datum.shippingCharge));


        viewHolder.binding.cancelBt.setOnClickListener(v -> {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setCancelable(false);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            bindingRx = DataBindingUtil.inflate(inflater, R.layout.cancel_order_item, null, false);
            bindingRx.titleTv.setText("Cancel Order");
            dialogBuilder.setView(bindingRx.getRoot());
            retryCustomAlert = dialogBuilder.create();

            bindingRx.close.setOnClickListener(v1 -> retryCustomAlert.dismiss());

            bindingRx.submitBtn.setOnClickListener(v12 -> {
                if (bindingRx.reasonEt.getText().toString().isEmpty()){
                    bindingRx.reasonEt.setError("Please enter reason");
                    bindingRx.reasonEt.requestFocus();
                }else {
                    cancelOrder(i,bindingRx.reasonEt.getText().toString() ,datum.id);
                }

            });

            retryCustomAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            retryCustomAlert.show();
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MyorderListItemBinding binding;

        public ViewHolder(MyorderListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(view -> context.startActivity(new Intent(context, MyOrderDetails.class)
                    .putExtra("order_id", arrayList.get(getLayoutPosition()).id + "")
                    .putExtra("seller_id", arrayList.get(getLayoutPosition()).sellerId + "")
                    .putExtra("shiped_date", arrayList.get(getLayoutPosition()).shippedDate + "")
            ));

        }
    }

    public void cancelOrder(int pos,String msg, String order_id) {
        raw_post = pos;
        bindingRx.matrialProgress.setVisibility(View.VISIBLE);
        bindingRx.submitBtn.setVisibility(View.GONE);
        HashMap objectNew = new HashMap();
        objectNew.put("order_id", order_id + "");
        objectNew.put("reason", msg + "");

//        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        Log.i("cancelOrder_obj", objectNew + "");
        new WebTask(context, WebUrls.BASE_URL + WebUrls.UserOrderCancel,
                objectNew, this, RequestCode.CODE_UserOrderCancel, 5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_UserOrderCancel == taskcode) {
            Log.i("cancelOrder_res", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status_code") == 1) {
                    retryCustomAlert.dismiss();
                    MyOrderList.getInstance().myOrderListCancel();
                    Toast.makeText(context, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                    Utils.Toast(context, jsonObject.optString("message"));
                } else {
                    Utils.Toast(context, jsonObject.optString("message"));
                }
                bindingRx.matrialProgress.setVisibility(View.GONE);
                bindingRx.submitBtn.setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
