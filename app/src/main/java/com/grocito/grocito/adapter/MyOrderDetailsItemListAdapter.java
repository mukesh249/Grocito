package com.grocito.grocito.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.MyOrderDetails;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.MyorderDetailItemBinding;
import com.grocito.grocito.databinding.ReturnExLayoutBinding;
import com.grocito.grocito.databinding.ReviewRatingBinding;
import com.grocito.grocito.model.MyOrderDetailsModel;
import com.grocito.grocito.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MyOrderDetailsItemListAdapter extends RecyclerView.Adapter<MyOrderDetailsItemListAdapter.ViewHolder>
        implements WebCompleteTask {

    ArrayList<MyOrderDetailsModel.MetaDatum> arrayList;
    ArrayList<String> exchArray;
    ArrayList<String> returnArray;
    Context context;
    AlertDialog retryCustomAlert;
    ReviewRatingBinding bindingr;
    ReturnExLayoutBinding bindingRx;

    public MyOrderDetailsItemListAdapter(Context context,
                                         ArrayList<MyOrderDetailsModel.MetaDatum> arrayList,
                                         ArrayList<String> exchArray,
                                         ArrayList<String> returnArray) {
        this.exchArray = exchArray;
        this.returnArray = returnArray;
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        MyorderDetailItemBinding notificationItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.myorder_detail_item, viewGroup, false);
        return new ViewHolder(notificationItemBinding);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MyOrderDetailsModel.MetaDatum metaDatum = arrayList.get(i);
        Utils.setImage(context, viewHolder.binding.itemImage, WebUrls.BASE_URL + WebUrls.IMAGE_PRODUCT + metaDatum.productImage);
        viewHolder.binding.itemNameTv.setText(String.format("%s", metaDatum.productName));
        viewHolder.binding.itemPriceTv.setText(String.format("Price : ₹%.0f", Double.parseDouble(metaDatum.price)));
        viewHolder.binding.itemQtyTv.setText(String.format("Quantity : %s", metaDatum.qty + ""));
        viewHolder.binding.subAmtTv.setText(String.format("Total Amount: ₹%.0f", Double.parseDouble(metaDatum.price) * metaDatum.qty));
        viewHolder.binding.itemdisTv.setText(String.format("Item Weight : %s", metaDatum.weight));
//        viewHolder.binding.deliveryTypTv.setText(String.format("%s",metaDatum.));
        viewHolder.binding.deliveryDateTv.setText(String.format("%s", metaDatum.expectedDeliveryDate));
//        viewHolder.binding.deliveryTimeTv.setText(String.format("%s",metaDatum.));
        viewHolder.binding.orderStatusTv.setText(String.format("%s", metaDatum.status));


        if (MyOrderDetails.current_date_millis > MyOrderDetails.shiped_date_mills) {
            Log.i("curentlar", true + "");
        } else {
            Log.i("shiplar", false + "");
        }

        if (metaDatum.deliveryDate != null && !metaDatum.deliveryDate.isEmpty()) {
            Log.i("shipped_date", metaDatum.deliveryDate + "");
            final long millisToAdd = 3600000 * 16; //16 hours
            Date d = null;
            try {
                d = Utils.formatDateFromDateDate("yyyy-MM-dd", "yyyy-MM-dd", metaDatum.deliveryDate);
                d.setTime(d.getTime() + millisToAdd);
                metaDatum.shippedDateMills = d.getTime() + millisToAdd;
//                Log.i("shipped_date_aft", d + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (metaDatum.returnStatus == 1) {
            viewHolder.binding.statusTv.setVisibility(View.VISIBLE);
            viewHolder.binding.statusTv.setText("Item Retuned");
        }else if (metaDatum.exchangeStatus == 1) {
            viewHolder.binding.statusTv.setVisibility(View.VISIBLE);
            viewHolder.binding.statusTv.setText("Item Exchanged");
        } else {
            viewHolder.binding.statusTv.setVisibility(View.GONE);
        }

        System.out.println("date_mils" + " " + "\n new_value: " + metaDatum.shippedDateMills);
        if (MyOrderDetails.current_date_millis > metaDatum.shippedDateMills ) {
//            viewHolder.binding.retExLL.setVisibility(View.GONE);
        } else {
            if (metaDatum.status.equals("delivered")) {
//                viewHolder.binding.retExLL.setVisibility(View.VISIBLE);
                if (metaDatum.product.isExchange == 1 && metaDatum.exchangeStatus != 1 && !MyOrderDetails.cancel) {
                    viewHolder.binding.exchangeTv.setVisibility(View.VISIBLE);
//                viewHolder.binding.returnTv.setVisibility(View.GONE);
                } else {
                    viewHolder.binding.exchangeTv.setVisibility(View.GONE);
//                    viewHolder.binding.returnTv.setVisibility(View.GONE);
                }
            }
        }
//        if (MyOrderDetails.current_date_millis > MyOrderDetails.shiped_date_mills) {
        if (metaDatum.product != null && metaDatum.product.isReturn == 1
                && metaDatum.returnStatus != 1
                && !metaDatum.status.equals("exchange")
                && !MyOrderDetails.cancel && MyOrderDetails.orderotp
                && !metaDatum.status.equals("delivered")) {
                viewHolder.binding.returnTv.setVisibility(View.VISIBLE);
//                    viewHolder.binding.exchangeTv.setVisibility(View.GONE);
            } else {
//                viewHolder.binding.exchangeTv.setVisibility(View.GONE);
                viewHolder.binding.returnTv.setVisibility(View.GONE);
            }
//        }

        if (metaDatum.product!=null && metaDatum.product.productRating != null) {
            viewHolder.binding.ratingTv.setVisibility(View.VISIBLE);
            viewHolder.binding.reviewRatingTv.setVisibility(View.GONE);
            viewHolder.binding.ratingTv.setText(String.format("%.1f", (double) metaDatum.product.productRating.rating));
        } else {
            if (metaDatum.status.equalsIgnoreCase("delivered")) {
                viewHolder.binding.reviewRatingTv.setVisibility(View.VISIBLE);
//                viewHolder.binding.statusTv.setVisibility(View.GONE);
            } else {
                viewHolder.binding.reviewRatingTv.setVisibility(View.GONE);

                String string = metaDatum.status.replaceAll("_", " ");
//                viewHolder.binding.statusTv.setVisibility(View.VISIBLE);
            }
            viewHolder.binding.ratingTv.setVisibility(View.GONE);
        }


        viewHolder.binding.reviewRatingTv.setOnClickListener(v -> {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setCancelable(false);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            bindingr = DataBindingUtil.inflate(inflater, R.layout.review_rating, null, false);

            dialogBuilder.setView(bindingr.getRoot());
            retryCustomAlert = dialogBuilder.create();

            bindingr.close.setOnClickListener(v1 -> retryCustomAlert.dismiss());

            bindingr.submitBtn.setOnClickListener(v12 -> {
                if (bindingr.commentEt.getText().toString().isEmpty()) {
                    bindingr.commentEt.setError("Please enter your comment");
                    bindingr.commentEt.requestFocus();
                } else {
                    reviewRating(i, bindingr.ratingBar.getRating() + "", bindingr.commentEt.getText().toString(),
                            metaDatum.productId.toString(), metaDatum.orderId.toString());
                }
            });


            retryCustomAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            retryCustomAlert.show();
        });
        viewHolder.binding.returnTv.setOnClickListener(v -> {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setCancelable(false);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            bindingRx = DataBindingUtil.inflate(inflater, R.layout.return_ex_layout, null, false);

            bindingRx.titleTv.setText("Return Product");
            ArrayAdapter<String> returnAdpater = new ArrayAdapter<>(context,
                    android.R.layout.simple_spinner_item,
                    returnArray);
            returnAdpater.setDropDownViewResource(R.layout.spinnerlayout);
            bindingRx.reasonSpinner.setAdapter(returnAdpater);
            dialogBuilder.setView(bindingRx.getRoot());
            retryCustomAlert = dialogBuilder.create();

            bindingRx.close.setOnClickListener(v1 -> retryCustomAlert.dismiss());

            bindingRx.submitBtn.setOnClickListener(v12 -> {
                returnExchang(i,
                        bindingRx.reasonSpinner.getSelectedItem().toString(),
                        metaDatum.id.toString(),
                        metaDatum.orderId.toString(),
                        WebUrls.UserReturnOrder
                );
            });

            retryCustomAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            retryCustomAlert.show();
        });
        viewHolder.binding.exchangeTv.setOnClickListener(v -> {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setCancelable(false);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            bindingRx = DataBindingUtil.inflate(inflater, R.layout.return_ex_layout, null, false);
            bindingRx.titleTv.setText("Exchange Product");
            dialogBuilder.setView(bindingRx.getRoot());
            retryCustomAlert = dialogBuilder.create();
            ArrayAdapter<String> exch_adapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_spinner_item,
                    exchArray);
            exch_adapter.setDropDownViewResource(R.layout.spinnerlayout);
            bindingRx.reasonSpinner.setAdapter(exch_adapter);

//            bindingRx.reasonSpinner
            bindingRx.close.setOnClickListener(v1 -> retryCustomAlert.dismiss());

            bindingRx.submitBtn.setOnClickListener(v12 -> {
                returnExchang(i,
                        bindingRx.reasonSpinner.getSelectedItem().toString(),
                        metaDatum.id.toString(),
                        metaDatum.orderId.toString(),
                        WebUrls.UserExchangeOrder
                );
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

        MyorderDetailItemBinding binding;

        public ViewHolder(MyorderDetailItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    int pos_ex;

    public void returnExchang(int pos, String msg, String meta_id, String order_id, String url) {
        pos_ex = pos;
        bindingRx.matrialProgress.setVisibility(View.VISIBLE);
        bindingRx.submitBtn.setVisibility(View.GONE);
        HashMap objectNew = new HashMap();
        objectNew.put("meta_id", meta_id + "");
        objectNew.put("order_id", order_id + "");
        objectNew.put("reason", msg + "");

//        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        Log.i("returnExchang_obj", objectNew + "");
        new WebTask(context, WebUrls.BASE_URL + url,
                objectNew, this, RequestCode.CODE_UserReturnOrder, 5);
    }

    public void reviewRating(int pos, String rating, String msg, String pro_id, String order_id) {
        bindingr.matrialProgress.setVisibility(View.VISIBLE);
        bindingr.submitBtn.setVisibility(View.GONE);
        HashMap objectNew = new HashMap();
        objectNew.put("rating", rating + "");
        objectNew.put("review_msg", msg + "");
        objectNew.put("product_id", pro_id + "");
        objectNew.put("order_id", order_id + "");
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        Log.i("reviewRating_obj", objectNew + "");
        new WebTask(context, WebUrls.BASE_URL + WebUrls.SubmitReviewRating,
                objectNew, this, RequestCode.CODE_SubmitReviewRating, 5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_SubmitReviewRating == taskcode) {
            Log.i("reviewRating_res", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status_code") == 1) {
                    Utils.Toast(context, jsonObject.optString("message"));
                    MyOrderDetails.getInstance().myOrderList(5);
                    retryCustomAlert.dismiss();
                } else {
                    Utils.Toast(context, jsonObject.optString("message"));
                    bindingr.matrialProgress.setVisibility(View.GONE);
                    bindingr.submitBtn.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (RequestCode.CODE_UserReturnOrder == taskcode) {
            Log.i("userReturnOrder_res", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status_code") == 1) {
                    Utils.Toast(context, jsonObject.optString("message"));
                    MyOrderDetails.getInstance().myOrderList(5);
                    retryCustomAlert.dismiss();
                } else {
                    Utils.Toast(context, jsonObject.optString("message"));
                    bindingRx.matrialProgress.setVisibility(View.GONE);
                    bindingRx.submitBtn.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
