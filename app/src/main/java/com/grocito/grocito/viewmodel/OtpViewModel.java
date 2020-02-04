package com.grocito.grocito.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.databinding.ObservableField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Observable;

import com.grocito.grocito.activities.HomeScreen;
import com.grocito.grocito.activities.OtpScreen;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.utils.Utils;

public class OtpViewModel extends Observable implements WebCompleteTask {

    private Context context;
    public final ObservableField<String> pincode = new ObservableField<>("");
//    public final ObservableField<String> pincodeString = new ObservableField<>("");


    public OtpViewModel(Context context) {
        this.context = context;
    }

    public void ResendOtp(String  mobile){
        HashMap objectNew = new HashMap();
        objectNew.put("mobile",mobile);
        Log.i("ResendOtp_obj",objectNew+"");
        new WebTask(context, WebUrls.BASE_URL+WebUrls.ReSendOtpApi,objectNew, OtpViewModel.this, RequestCode.CODE_ResendOtp,1);
    }
    public void OtpVerifiyRequest(String Mobile,String fname,String lname,String Email,String ReferralCode,String city){
        HashMap objectNew = new HashMap();
        objectNew.put("mobile",Mobile);
        objectNew.put("f_name",fname);
        objectNew.put("l_name",lname);
        objectNew.put("email",Email);
        objectNew.put("reff_code",ReferralCode);
        objectNew.put("device_token", SharedPrefManager.getDeviceToken(Constrants.Token));
        objectNew.put("city_name", city);
        Log.i("OtpVerifiy_obj",objectNew+"");
        new WebTask(context, WebUrls.BASE_URL+WebUrls.VerifiyOtpApi,objectNew,OtpViewModel.this, RequestCode.CODE_OtpVerifiy,1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_ResendOtp==taskcode) {
            Log.i("Resend_res", response);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status_code") == 1) {
                    OtpScreen.otp = jsonObject.optString("otp_code");
                }else {
                    Utils.Toast(context,jsonObject.optString("error_message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (RequestCode.CODE_OtpVerifiy==taskcode) {
            Log.i("OtpVerifiy_res", response);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
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

                    context.startActivity(new Intent(context, HomeScreen.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    ((Activity)context).finish();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
