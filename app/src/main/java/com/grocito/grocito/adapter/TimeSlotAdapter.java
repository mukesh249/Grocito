package com.grocito.grocito.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.Payment;
import com.grocito.grocito.databinding.TimeslotItemBinding;
import com.grocito.grocito.model.CheckOutModel;
import com.grocito.grocito.utils.Utils;

import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ViewHolder> {

    List<CheckOutModel.DateTimeSlot> arrayList;

    Context context;
    private String order_id = "", shiped_date = "", current_mills = "";
    long current_date_millis, shiped_date_mills;

    public static RadioButton lastChecked = null;
    public static String seleted_Time = "";
    public static int checkedPosition = -1;
    private int poss=-1;

    public int getPoss() {
        return poss;
    }

    public TimeSlotAdapter(Context context, List<CheckOutModel.DateTimeSlot> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        current_date_millis = System.currentTimeMillis() / 1000;
//        current_mills = tsLong.toString();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TimeslotItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.timeslot_item, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CheckOutModel.DateTimeSlot timeSlot = arrayList.get(i);


        Log.i("current_date_mills", current_date_millis + "");
        Log.i("senconds_mills", timeSlot.second + "");

        if (Payment.crtdate) {
            if (current_date_millis >= timeSlot.second) {
                viewHolder.binding.viewTime.setVisibility(View.GONE);
            } else {
                viewHolder.binding.viewTime.setVisibility(View.VISIBLE);
            }
        }
        viewHolder.binding.viewTime.setOnClickListener(v -> {
            poss = i;
            seleted_Time = timeSlot.startTime + "-" + timeSlot.endTime;
            Utils.Toast(context, seleted_Time);
            notifyDataSetChanged();
        });


//        viewHolder.binding.imgChecked.setVisibility(poss == i ? View.VISIBLE : View.INVISIBLE);

        if (poss==i){
            viewHolder.binding.imgChecked.setImageDrawable(context.getResources().getDrawable(R.drawable.radio_f));
        }else {
            viewHolder.binding.imgChecked.setImageDrawable(context.getResources().getDrawable(R.drawable.radio_e));
        }


        viewHolder.binding.radioBtn1.setText(String.format("%s - %s", timeSlot.startTime, timeSlot.endTime));
//        viewHolder.binding.radioBtn1.setChecked(timeSlot.isChecked);
//        if (checkedPosition == i) {
//            lastChecked = viewHolder.binding.radioBtn1;
//            timeSlot.isChecked = true;
//            checkedPosition = i;
//            if (timeSlot.isChecked) {
//                viewHolder.binding.radioBtn1.setChecked(timeSlot.isChecked);
//            }
//            seleted_Time = timeSlot.startTime + "-" + timeSlot.endTime;
////            Utils.Toast(context, timeSlot.startTime + "-" + timeSlot.endTime);
//        }

//        viewHolder.binding.radioBtn1.setOnClickListener(v -> {
////            RadioButton cb = (RadioButton) v;
//
//            if (cb.isChecked()) {
//                if (lastChecked != null) {
//                    lastChecked.setChecked(false);
//                    timeSlot.isChecked = false;
//                }
//                seleted_Time = timeSlot.startTime + "-" + timeSlot.endTime;
//                lastChecked = cb;
//                checkedPosition = i;
//            } else {
//                lastChecked = null;
//                seleted_Time = "";
//            }
//
//            Utils.Toast(context, seleted_Time);
//            timeSlot.isChecked = cb.isChecked();
//
//        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TimeslotItemBinding binding;

        public ViewHolder(@NonNull TimeslotItemBinding
                                  itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

//    public void deleteAddress(int pos) {
////        rawpos = pos;
////        HashMap objectNew = new HashMap();
////        objectNew.put("address_id", arrayList.get(pos).id);
////        Log.i("DeleteUserAddress_obj", objectNew + "");
//      //  new WebTask(context, WebUrls.BASE_URL + WebUrls.DeleteUserAddress, objectNew, this, RequestCode.CODE_DeleteUserAddress, 5);
//    }
//
//    @Override
//    public void onComplete(String response, int taskcode) {
//        if (RequestCode.CODE_UserCheckout == taskcode) {
//            Log.i("DeleteUserAddress_res", response);
//
//            JSONObject jsonObject = null;
//            try {
//                jsonObject = new JSONObject(response);
//                if (jsonObject.optInt("status_code") == 1){
//                    Utils.Toast(context,jsonObject.optString("message"));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
