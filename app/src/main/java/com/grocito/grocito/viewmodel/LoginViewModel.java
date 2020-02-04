package com.grocito.grocito.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.databinding.ObservableField;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Observable;

import com.grocito.grocito.activities.OtpScreen;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.utils.Utils;

public class LoginViewModel extends Observable implements WebCompleteTask {

    private Context context;
    public final ObservableField<String> mobileno = new ObservableField<>("");

    public LoginViewModel(Context context) {
        this.context = context;
    }

    public void LoginRequest(){
        HashMap objectNew = new HashMap();
        objectNew.put("mobile",mobileno.get());
        objectNew.put("device_token", SharedPrefManager.getDeviceToken(Constrants.Token));
        Log.i("Login_obj",objectNew+"");
        new WebTask(context, WebUrls.BASE_URL+WebUrls.LoginApi,objectNew,LoginViewModel.this, RequestCode.CODE_Login,1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_Login == taskcode) {
            Log.i("Login_res", response);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status_code") == 1) {
                    context.startActivity(new Intent(context, OtpScreen.class)
                            .putExtra("activity","login")
                            .putExtra("mobile",mobileno.get())
                            .putExtra("otp",jsonObject.optString("otp_code"))
                            .putExtra("response",response)
                    );
                } else {
                    Utils.Toast(context, jsonObject.optString("error_message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
