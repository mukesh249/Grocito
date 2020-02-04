package com.grocito.grocito.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.grocito.grocito.R;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityCancelAndReturnBinding;
import com.grocito.grocito.databinding.ActivityTermAndConditionBinding;
import com.grocito.grocito.model.UserPagesModel;

public class CancelAndReturn extends AppCompatActivity {

    private ActivityCancelAndReturnBinding binding;
    UserPagesModel userPagesModel = new UserPagesModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cancel_and_return);

        binding.headlyaout.productCatName.setText(getResources().getString(R.string.term_and_condition));
        binding.headlyaout.cartRL.setVisibility(View.GONE);
        binding.headlyaout.searchIcon.setVisibility(View.GONE);
        binding.headlyaout.backBtn.setOnClickListener(v -> finish());
        String des = SharedPrefManager.getCancelReturn(Constrants.CancelReturn);

        binding.webView.loadData(des, "text/html", null);
    }
}
