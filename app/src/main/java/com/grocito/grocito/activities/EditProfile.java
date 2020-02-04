package com.grocito.grocito.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.grocito.grocito.R;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityEditProfileBinding;
import com.grocito.grocito.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import dmax.dialog.SpotsDialog;

public class EditProfile extends AppCompatActivity implements WebCompleteTask {

    private ActivityEditProfileBinding binding;
    AlertDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_profile);

        binding.headlyaout.productCatName.setText(getResources().getString(R.string.edit_profile));
        binding.headlyaout.backBtn.setOnClickListener(v -> onBackPressed());
        binding.headlyaout.cartRL.setVisibility(View.GONE);
        binding.headlyaout.searchIcon.setVisibility(View.GONE);
        progressDialog = new SpotsDialog(this, R.style.Custom);
        binding.firstNameEt.setText(Utils.FirstLatterCap(SharedPrefManager.getUserFirstname(Constrants.UserFirstName)));
        binding.lastNameEt.setText(Utils.FirstLatterCap(SharedPrefManager.getUserLastName(Constrants.UserLastName)));
        binding.emailEt.setText(SharedPrefManager.getUserEmail(Constrants.UserEmail));

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proShow(true,"dasf");
                updateProfile();
            }
        });

    }
    public void proShow(Boolean value ,String message) {
        if (!progressDialog.isShowing())
            progressDialog.show();
        else{
            progressDialog.dismiss();
            Utils.Toast(this,message);
        }
    }
    public void updateProfile(){
        HashMap objentNew = new HashMap();
        objentNew.put("user_id",SharedPrefManager.getUserID(Constrants.UserId));
        objentNew.put("f_name",binding.firstNameEt.getText().toString());
        objentNew.put("l_name",binding.lastNameEt.getText().toString());
        objentNew.put("email",binding.emailEt.getText().toString());

        Log.i("updateProfile_obj",objentNew+"");
        new WebTask(EditProfile.this, WebUrls.BASE_URL+WebUrls.UserUpdateProfile,
                objentNew,EditProfile.this, RequestCode.CODE_UserUpdateProfile,5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_UserUpdateProfile == taskcode){
            Log.i("updateProfile_res",response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status_code")==1){
                    SharedPrefManager.setUserFirstName(Constrants.UserFirstName,binding.firstNameEt.getText().toString());
                    SharedPrefManager.setUserLastName(Constrants.UserLastName,binding.lastNameEt.getText().toString());
                    SharedPrefManager.setUserEmail(Constrants.UserEmail,binding.emailEt.getText().toString());
                    SharedPrefManager.setUserName(Constrants.UserName,
                            binding.firstNameEt.getText().toString()+" "+ binding.lastNameEt.getText().toString());
                    Utils.Toast(this,jsonObject.optString("message"));
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            proShow(false,"dasf");
        }
    }
}
