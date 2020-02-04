package com.grocito.grocito.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.grocito.grocito.R;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityOtpScreenBinding;
import com.grocito.grocito.presenter.OtpPresenter;
import com.grocito.grocito.readotp.SmsListener;
import com.grocito.grocito.readotp.SmsReceiver;
import com.grocito.grocito.utils.Utils;
import com.grocito.grocito.viewmodel.OtpViewModel;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OtpScreen extends AppCompatActivity {

    ActivityOtpScreenBinding binding;
    OtpViewModel otpViewModel;
    String fname, lname, mobile, referral, email, response, activity,city_name;
    public static String otp;

    private SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp_screen);
        databaseList();

        otpViewModel = new OtpViewModel(this);
        binding.setOtpViewModel(otpViewModel);

//        SmsReceiver.bindListener(new SmsListener() {
//            @Override
//            public void messageReceived(String messageText) {
//                binding.firstPinView.setText(messageText);
//            }
//        });
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
//                String code = parseCode(message);//Parse verification code
                smsVerifyCatcher.setPhoneNumberFilter("VDGRCITO");
                String abcd = message.replaceAll("[^0-9]", "");
                binding.firstPinView.setText(abcd);
            }
        });

        if (getIntent().getExtras() != null) {
            activity = getIntent().getExtras().getString("activity", "");
            if (activity.equals("login")) {
                response = getIntent().getExtras().getString("response", "");
            } else if (activity.equals("signup")) {
                fname = getIntent().getExtras().getString("fname", "");
                lname = getIntent().getExtras().getString("lname", "");
                email = getIntent().getExtras().getString("email", "");
                referral = getIntent().getExtras().getString("referral", "");
                referral = getIntent().getExtras().getString("referral", "");
                city_name = getIntent().getExtras().getString("city", "");

            }
            mobile = getIntent().getExtras().getString("mobile", "");
            otp = getIntent().getExtras().getString("otp", "");
            Log.i("sdfasf", otp + "  " + mobile);
//            binding.otpString.setText(otp);

        }
        binding.setViewPresenter(new OtpPresenter() {
            @Override
            public void OtpResend() {
                otpViewModel.ResendOtp(mobile);
            }

            @Override
            public void OptVerifiy() {
                Log.i("otp_code", otp + "=" + binding.firstPinView.getText());
                if (otp.compareTo(binding.firstPinView.getText().toString()) == 0) {
                    if (activity.equals("login")) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("status_code") == 1) {

                                JSONArray dataArray = jsonObject.optJSONArray("data");
                                SharedPrefManager.setLogin(Constrants.IsLogin, true);
                                SharedPrefManager.setUserName(Constrants.UserName, dataArray.getJSONObject(0).optString("username"));
                                SharedPrefManager.setUserID(Constrants.UserId, dataArray.getJSONObject(0).optString("id"));
                                SharedPrefManager.setUserEmail(Constrants.UserEmail, dataArray.getJSONObject(0).optString("email"));
//                                SharedPrefManager.setUserEmail(Constrants.UserEmail,dataArray.getJSONObject(0).optString("email" ));
                                SharedPrefManager.setUserMobile(Constrants.UserMobile, dataArray.getJSONObject(0).optString("mobile"));
                                SharedPrefManager.setUserPic(Constrants.UserPic, dataArray.getJSONObject(0).optJSONObject("user_kyc").optString("profile_image"));
                                SharedPrefManager.setUserFirstName(Constrants.UserFirstName, dataArray.getJSONObject(0).optJSONObject("user_kyc").optString("f_name"));
                                SharedPrefManager.setUserLastName(Constrants.UserLastName, dataArray.getJSONObject(0).optJSONObject("user_kyc").optString("l_name"));

                                startActivity(new Intent(OtpScreen.this, HomeScreen.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (activity.equals("signup")) {
                        otpViewModel.OtpVerifiyRequest(mobile, fname, lname, email, referral,city_name);
                    }
                } else {
                    Utils.Toast(OtpScreen.this, "OTP not Match");
                }
            }
        });

        binding.backBtn.setOnClickListener(v -> finish());

    }
    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
