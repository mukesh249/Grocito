package com.grocito.grocito.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.grocito.grocito.R;
import com.grocito.grocito.adapter.CheckoutAdapter;
import com.grocito.grocito.adapter.TimeSlotAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityPaymentBinding;
import com.grocito.grocito.model.CheckOutModel;
import com.grocito.grocito.model.PlaceOrderCodModel;
import com.grocito.grocito.utils.Utils;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class Payment extends AppCompatActivity implements WebCompleteTask, DatePickerListener, PaymentResultListener {

    ActivityPaymentBinding paymentBinding;
    List<CheckOutModel.AddressList> addressLists = new ArrayList<>();
    List<CheckOutModel.DateTimeSlot> timeSlotList = new ArrayList<>();
    private CheckoutAdapter checkoutAdapter;
    private TimeSlotAdapter timeSlotAdapter;
    HorizontalPicker picker;
    double wallet_amt = 0;
    double pretotal = 0;
    double final_deli_ch = 0;
    public static String delivery_type = "";
    private static Payment mInstance;
    private String payment_mode = "";
    private String delivery_date = "",current_date="";
    private String expressTime = "";
    private String orderId, orderNo, method = "";
    double payAmt;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment);

        mInstance = this;

        /**
         * Preload payment resources
         */
        Checkout.preload(getApplicationContext());

        paymentBinding.headlyaout.searchIcon.setVisibility(View.GONE);
        paymentBinding.headlyaout.cartRL.setVisibility(View.GONE);
        paymentBinding.headlyaout.productCatName.setText(getResources().getString(R.string.payment));

        paymentBinding.headlyaout.backBtn.setOnClickListener(view -> finish());

        paymentBinding.userNameTv.setText(SharedPrefManager.getUserName(Constrants.UserName));
        paymentBinding.addRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentBinding.addRecyclerView.setNestedScrollingEnabled(false);

        paymentBinding.timeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentBinding.timeRecyclerView.setNestedScrollingEnabled(false);
        checkoutAdapter = new CheckoutAdapter(this, addressLists);
        paymentBinding.addRecyclerView.setAdapter(checkoutAdapter);
        checkoutAdapter.notifyDataSetChanged();


        paymentBinding.timeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentBinding.timeRecyclerView.setNestedScrollingEnabled(false);
        timeSlotAdapter = new TimeSlotAdapter(this, timeSlotList);
        paymentBinding.timeRecyclerView.setAdapter(timeSlotAdapter);
        timeSlotAdapter.notifyDataSetChanged();

        paymentBinding.razorpayRadioBtn.setOnClickListener(view -> {
            paymentBinding.razorpayRadioBtn.setChecked(true);
            paymentBinding.codRadioBtn.setChecked(false);
            payment_mode = "online";
            paymentBinding.paynowBtn.setText(getResources().getString(R.string.pay_now));
        });

        paymentBinding.codRadioBtn.setOnClickListener(view -> {
            paymentBinding.codRadioBtn.setChecked(true);
            paymentBinding.razorpayRadioBtn.setChecked(false);
            payment_mode = "cod";
            paymentBinding.paynowBtn.setText(getResources().getString(R.string.place_order));
        });

        paymentBinding.paynowBtn.setOnClickListener(view -> {
            Log.i("sdfsafsaf", orderId + "");
            if (!Utils.checkEmptyNull(payment_mode)) {
                Utils.Toast(this, "Please Select Payment Method.");
            } else if (!Utils.checkEmptyNull(delivery_date)) {
                Utils.Toast(this, "Please Select Date.");
            } else if (delivery_type.equalsIgnoreCase("Standard")&&!Utils.checkEmptyNull(TimeSlotAdapter.seleted_Time)) {
                Utils.Toast(this, "Please Select Time Slot.");
            } else if (!Utils.checkEmptyNull(CheckoutAdapter.seleted_address_id)) {
                Utils.Toast(this, "Please Select Address or Add address");
            } else {
                if (paymentBinding.razorpayRadioBtn.isChecked())
                    startPayment();
                if (paymentBinding.codRadioBtn.isChecked())
                    orderPlace();
            }
        });

        paymentBinding.changeAddressTv.setOnClickListener(view -> {
            startActivity(new Intent(Payment.this, AddNewAddress.class));
        });

        // find the picker
        picker = (HorizontalPicker) findViewById(R.id.datePicker);

        // initialize it and attach a listener
        picker.setListener(this)
                .setDays(6)
                .setOffset(0)
                .showTodayButton(false)
                .setTodayDateBackgroundColor(getResources().getColor(R.color.md_grey_100))
                .init();
//        picker.setDate(new DateTime().plusDays(0));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        delivery_date = dateFormat.format(calendar.getTime());
        current_date = delivery_date;
        Log.i("first_date",delivery_date);
        delivery_type = getResources().getString(R.string.standard);

//        init();

//        deliveryAmt(CheckoutAdapter.seleted_address_id);
        paymentBinding.express.setOnClickListener(view -> {
            delivery_type = getResources().getString(R.string.express);

            paymentBinding.express.setBackground(getResources().getDrawable(R.drawable.greyborder));
            paymentBinding.express.setTextColor(getResources().getColor(R.color.colorPrimary));
            paymentBinding.standardTv.setTextColor(getResources().getColor(R.color.black));
            paymentBinding.standardTv.setBackground(null);
            paymentBinding.timeRecyclerView.setVisibility(View.GONE);
            paymentBinding.expressViewTv.setVisibility(View.VISIBLE);
            paymentBinding.datePicker.setVisibility(View.GONE);
            deliveryAmt(CheckoutAdapter.seleted_address_id);
        });

        paymentBinding.standardTv.setOnClickListener(view -> {
            delivery_type = getResources().getString(R.string.standard);
            paymentBinding.standardTv.setBackground(getResources().getDrawable(R.drawable.greyborder));
            paymentBinding.standardTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            paymentBinding.express.setTextColor(getResources().getColor(R.color.black));
            paymentBinding.express.setBackground(null);
            paymentBinding.timeRecyclerView.setVisibility(View.VISIBLE);
            paymentBinding.expressViewTv.setVisibility(View.GONE);
            paymentBinding.datePicker.setVisibility(View.VISIBLE);
            deliveryAmt(CheckoutAdapter.seleted_address_id);
        });

        paymentBinding.checkboxWallet.setOnCheckedChangeListener((buttonView, isChecked) -> {
            PayableAmount(isChecked);
        });
        init();
//        userCheckOut();
    }

    public static boolean crtdate = true;
    @Override
    public void onDateSelected(DateTime dateSelected) {
        // log it for demo
        Log.i("HorizontalPicker", "Selected date is " + dateSelected.toString());
        try {
            delivery_date = Utils.formatDateFromDateString(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS",
                    "dd-MM-yyyy",
                    dateSelected.toString());
            if (current_date.equals(delivery_date)){
                crtdate = true;
            }else {
                crtdate = false;
            }
//            TimeSlotAdapter.checkedPosition = -1;
//            TimeSlotAdapter.lastChecked = null;
//
//            for (int i =0;i<timeSlotList.size();i++){
//                timeSlotList.get(i).isChecked=false;
//            }
            TimeSlotAdapter.seleted_Time = "";
            timeSlotAdapter = new TimeSlotAdapter(this, timeSlotList);
            paymentBinding.timeRecyclerView.setAdapter(timeSlotAdapter);
//            timeSlotAdapter.notifyDataSetChanged();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("select_date", "Selected date is " + delivery_date);
    }

    @SuppressLint("DefaultLocale")
    public void init() {
        paymentBinding.express.setOnClickListener(view -> {
            delivery_type = getResources().getString(R.string.express).toLowerCase();

            paymentBinding.express.setBackground(getResources().getDrawable(R.drawable.greyborder));
            paymentBinding.express.setTextColor(getResources().getColor(R.color.colorPrimary));
            paymentBinding.standardTv.setTextColor(getResources().getColor(R.color.black));
            paymentBinding.standardTv.setBackground(null);
            paymentBinding.timeRecyclerView.setVisibility(View.GONE);
            paymentBinding.expressViewTv.setVisibility(View.VISIBLE);
            paymentBinding.datePicker.setVisibility(View.GONE);
            deliveryAmt(CheckoutAdapter.seleted_address_id);
        });

        paymentBinding.standardTv.setOnClickListener(view -> {
            delivery_type = getResources().getString(R.string.standard).toLowerCase();
            paymentBinding.standardTv.setBackground(getResources().getDrawable(R.drawable.greyborder));
            paymentBinding.standardTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            paymentBinding.express.setTextColor(getResources().getColor(R.color.black));
            paymentBinding.express.setBackground(null);
            paymentBinding.timeRecyclerView.setVisibility(View.VISIBLE);
            paymentBinding.expressViewTv.setVisibility(View.GONE);
            paymentBinding.datePicker.setVisibility(View.VISIBLE);
            deliveryAmt(CheckoutAdapter.seleted_address_id);
        });

        paymentBinding.checkboxWallet.setOnCheckedChangeListener((buttonView, isChecked) -> {
            PayableAmount(isChecked);
        });

    }

    public static Payment getInstance() {
        return mInstance;
    }

    public void userCheckOut() {
//        paymentBinding.scrollView.setVisibility(View.GONE);
        HashMap objectNew = new HashMap();
        objectNew.put("pincode", SharedPrefManager.getPinCode(Constrants.PinCode));
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        Log.i("UserCheckout_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.UserCheckout, objectNew, Payment.this, RequestCode.CODE_UserCheckout, 1);
    }

    public void deliveryAmt(String adr_id) {
        HashMap objectNew = new HashMap();
        objectNew.put("address_id", adr_id);
        objectNew.put("delivery_type", delivery_type.toLowerCase());
        objectNew.put("amount", pretotal+"");

        Log.i("GetDeliveryAmount_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.GetDeliveryAmount, objectNew, Payment.this, RequestCode.CODE_GetDeliveryAmount, 5);
    }

    @Override
    protected void onStart() {
        super.onStart();
        paymentBinding.matrialProgress.setVisibility(View.GONE);
        paymentBinding.paynowBtn.setVisibility(View.VISIBLE);
        paymentBinding.scrollView.setEnabled(true);
        userCheckOut();
    }

    public void deliveryChargeRefresh(){
        final_deli_ch = 0;
        paymentBinding.deliveryChargeTv.setText(String.format("₹%.0f", final_deli_ch));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_UserCheckout == taskcode) {
            Log.i("UserCheckout_res", response);
            addressLists.clear();
            timeSlotList.clear();
            CheckOutModel checkOutModel = JsonDeserializer.deserializeJson(response, CheckOutModel.class);
            if (checkOutModel.expressStatus == 1){
                paymentBinding.express.setVisibility(View.VISIBLE);
            }else {
                paymentBinding.express.setVisibility(View.GONE);
            }
            addressLists.addAll(checkOutModel.data.addressList);
            timeSlotList.addAll(checkOutModel.data.dateTimeSlot);
            checkoutAdapter.notifyDataSetChanged();
            timeSlotAdapter = new TimeSlotAdapter(this, timeSlotList);
            paymentBinding.timeRecyclerView.setAdapter(timeSlotAdapter);
            timeSlotAdapter.notifyDataSetChanged();
            paymentBinding.expressViewTv.setText(String.format("%s", checkOutModel.data.expressTimeString));
            expressTime = checkOutModel.data.expressTime;
            wallet_amt = checkOutModel.data.walletAmount;
            paymentBinding.checkboxWallet.setText(String.format("Use Grocito Wallet(₹%.0f)", wallet_amt));
            if (getIntent().getExtras() != null) {
                paymentBinding.walletLL.setVisibility(View.GONE);
                paymentBinding.walletV.setVisibility(View.GONE);
                pretotal = getIntent().getExtras().getDouble("amt", 0);
                paymentBinding.totalPayableTv.setText(String.format("₹%.0f", pretotal));
                PayableAmount(paymentBinding.checkboxWallet.isChecked());
            }
//            paymentBinding.scrollView.setVisibility(View.VISIBLE);
        }
        if (RequestCode.CODE_GetDeliveryAmount == taskcode) {
            Log.i("GetDeliveryAmount_res", response);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status_code") == 1) {
                    final_deli_ch = jsonObject.optInt("delivery_charge");
                    paymentBinding.deliveryChargeTv.setText(String.format("₹%.0f", final_deli_ch));

                    if (getIntent().getExtras() != null) {
                        paymentBinding.walletLL.setVisibility(View.GONE);
                        paymentBinding.walletV.setVisibility(View.GONE);
                        pretotal = getIntent().getExtras().getDouble("amt", 0);
                        paymentBinding.totalPayableTv.setText(String.format("₹%.0f", pretotal));
                        PayableAmount(paymentBinding.checkboxWallet.isChecked());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (RequestCode.CODE_UserPlaceOrder == taskcode) {
            Log.i("UserPlaceOrder_res", response);

            PlaceOrderCodModel placeOrderCodModel = JsonDeserializer.deserializeJson(response, PlaceOrderCodModel.class);
            if (placeOrderCodModel.statusCode == 1) {
                paymentBinding.matrialProgress.setVisibility(View.GONE);
                paymentBinding.paynowBtn.setVisibility(View.GONE);
                paymentBinding.scrollView.setEnabled(false);

                orderId = placeOrderCodModel.data.id + "";
                orderNo = placeOrderCodModel.data.orderId + "";
                Intent intent = new Intent(Payment.this, Success.class);
                intent.putExtra("order_id", orderId);
                intent.putExtra("order_no", orderNo);
                if (paymentBinding.codRadioBtn.isChecked()) {
                    intent.putExtra("method", "cod");
                    startActivity(intent);
                }
                if (paymentBinding.razorpayRadioBtn.isChecked()) {
                    if (method.equals("razorpaySuccess")) {
                        intent.putExtra("method", method);
                        startActivity(intent);
                    }
                    if (method.equals("razorpayFailed")) {
                        intent.putExtra("method", method);
                        startActivity(intent);
                    }
                }
            } else {
                Utils.Toast(this, placeOrderCodModel.message);
            }
        }
    }

    double netAmt = 0, wltDrwAmt = 0;

    @SuppressLint("DefaultLocale")
    public void PayableAmount(boolean isChecked) {
        double totAmt = 0;
        double wltSubAmt = 0;
        if (isChecked) {
            paymentBinding.walletLL.setVisibility(View.VISIBLE);
            paymentBinding.walletV.setVisibility(View.VISIBLE);
            if ((pretotal + final_deli_ch) > wallet_amt) {
                wltSubAmt = wallet_amt;
            } else {
                wltSubAmt = (pretotal + final_deli_ch);
            }
            totAmt = (pretotal + final_deli_ch) - wltSubAmt;
            paymentBinding.walletAmountTv.setText(String.format("-₹%.0f", wltSubAmt));
//                paymentBinding.checkboxWallet.setText(String.format("Use Grocito Wallet(Rs. %.2f)", totAmt));

        } else {
            paymentBinding.walletLL.setVisibility(View.GONE);
            paymentBinding.walletV.setVisibility(View.GONE);
            totAmt = (pretotal + final_deli_ch);
        }
        netAmt = totAmt;
        wltDrwAmt = wltSubAmt;
        if (totAmt == 0) {
            paymentBinding.razorpayRadioBtn.setVisibility(View.GONE);
        } else {
            paymentBinding.razorpayRadioBtn.setVisibility(View.VISIBLE);
        }
        payAmt = totAmt;
        paymentBinding.amountTv.setText(String.format("₹%.0f", totAmt));
    }

    public void orderPlace() {
        paymentBinding.matrialProgress.setVisibility(View.VISIBLE);
        paymentBinding.paynowBtn.setVisibility(View.GONE);
        paymentBinding.scrollView.setEnabled(false);
        paymentBinding.scrollView.setAlpha(0.5f);
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        objectNew.put("address_id", CheckoutAdapter.seleted_address_id);
        objectNew.put("pincode", SharedPrefManager.getPinCode(Constrants.PinCode));
        objectNew.put("net_amount", "100");
        objectNew.put("sgst_amount", "10");
        objectNew.put("payment_mode", payment_mode);
        objectNew.put("delivery_date", delivery_date);
        objectNew.put("delivery_time", TimeSlotAdapter.seleted_Time);
        objectNew.put("delivery_type", delivery_type.toLowerCase());
        objectNew.put("express_time", expressTime);
        objectNew.put("withdraw_wallet_amount", wltDrwAmt + "");
        objectNew.put("delivery_charge", final_deli_ch + "");
        Log.i("orderPlace_obj", objectNew + "");

        new WebTask(this, WebUrls.BASE_URL + WebUrls.UserPlaceOrder, objectNew, Payment.this, RequestCode.CODE_UserPlaceOrder, 5);

    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("payment_success", s);
        method = "razorpaySuccess";
        orderPlace();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("payment_failed", s);
        method = "razorpayFailed";
        orderPlace();
    }


    public void startPayment() {
//        double pay = Double.parseDouble(paymentBinding.amountTv.getText().toString());
        double pay = payAmt;
        Checkout checkout = new Checkout();  /**   * Set your logo here   */
        checkout.setImage(R.drawable.logo);  /**   * Reference to current activity   */
        final Activity activity = this;  /**   * Pass your payment options to the Razorpay Checkout as a JSONObject   */
        try {

            JSONObject options = new JSONObject();      /**      * Merchant Name      * eg: ACME Corp || HasGeek etc.      */
            options.put("name", "GROCITO ONLINE PRIVATE LIMITED");      /**      * Description can be anything      * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.      *     Invoice Payment      *     etc.      */
            options.put("description", "Order place by online payment your order Id : " + orderNo);
//            options.put("order_id", orderNo);

            JSONObject prefill = new JSONObject();
            prefill.put("email", SharedPrefManager.getUserEmail(Constrants.UserEmail));
            prefill.put("name", SharedPrefManager.getUserName(Constrants.UserName));
            prefill.put("contact", SharedPrefManager.getUserMobile(Constrants.UserMobile));
            options.put("prefill",prefill);

            options.put("currency", "INR");      /**      * Amount is always passed in currency subunits      * Eg: "500" = INR 5.00      */
            options.put("amount", pay * 100);
            Log.i("startpayment_obj:", options.toString());
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("Payment", "Error in starting Razorpay Checkout", e);
        }
    }

}
