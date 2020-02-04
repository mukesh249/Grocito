package com.grocito.grocito.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.grocito.grocito.R;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityAddMoneyBinding;
import com.grocito.grocito.utils.Utils;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddMoney extends AppCompatActivity implements PaymentResultListener, WebCompleteTask {

    int payment=0;
    private ActivityAddMoneyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_money);
        binding.headlyaout.searchIcon.setVisibility(View.GONE);
        binding.headlyaout.cartRL.setVisibility(View.GONE);
        binding.headlyaout.backBtn.setOnClickListener(v -> finish());
        binding.headlyaout.productCatName.setText(getString(R.string.addmoneytowallet));
        /**
         * Preload payment resources
         */
//        Checkout.preload(getApplicationContext());
        if (getIntent().getExtras() != null) {
            binding.walletAmoutTv.setText(getIntent().getExtras().getString("wallet_amount", ""));
        }

        binding.submitAddmoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.enterAmmountEt.getText().toString().trim())) {
                    binding.enterAmmountEt.setError("Please Enter Amount");
                    binding.enterAmmountEt.requestFocus();
                } else {
                    Paymethod();
                }
            }
        });
    }
    public void Paymethod() {
        payment = Integer.parseInt(binding.enterAmmountEt.getText().toString());
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.logo);
        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();      /**      * Merchant Name      * eg: ACME Corp || HasGeek etc.      */
            options.put("name", "GROCITO ONLINE PRIVATE LIMITED");      /**      * Description can be anything      * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.      *     Invoice Payment      *     etc.      */
            JSONObject prefill = new JSONObject();
            prefill.put("email", SharedPrefManager.getUserEmail(Constrants.UserEmail));
            prefill.put("name", SharedPrefManager.getUserName(Constrants.UserName));
            prefill.put("contact", SharedPrefManager.getUserMobile(Constrants.UserMobile));
            options.put("prefill",prefill);
//            options.put("description", "testing order place by online payment");
            options.put("currency", "INR");      /**      * Amount is always passed in currency subunits      * Eg: "500" = INR 5.00      */
            options.put("amount", payment * 100);

            Log.d("add_money_data", options + "");
            checkout.open(activity, options);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
//        spin_kit.setVisibility(View.GONE);
        Log.d("payment_success", s);
        addUserWalletAmount(payment,s);
    }

    @Override
    public void onPaymentError(int i, String s) {
//        spin_kit.setVisibility(View.GONE);
        Log.d("payment_fail", s);
        Toast.makeText(this, "Your payment failed", Toast.LENGTH_SHORT).show();
    }

    public void addUserWalletAmount(double amount,String transaction_id){
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        objectNew.put("amount",amount+"");
        objectNew.put("transaction_id", transaction_id);
        Log.i("addmoney_obj",objectNew+"");
        new WebTask(this, WebUrls.BASE_URL+WebUrls.AddUserWalletAmount,objectNew,
                AddMoney.this, RequestCode.CODE_AddUserWalletAmount,5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_AddUserWalletAmount==taskcode){
            Log.i("addmoney_res",response+"");

            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status_code")==1){
                    Utils.Toast(this,jsonObject.optString("message"));
                    finish();
                }else {
                    Utils.Toast(this,jsonObject.optString("message"));
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
