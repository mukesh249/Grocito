package com.grocito.grocito.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.grocito.grocito.R;
import com.grocito.grocito.adapter.GrocitoWalletAdapter;
import com.grocito.grocito.adapter.ReferralWalletAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityMyWalletBinding;
import com.grocito.grocito.model.GrocitoWalletModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyWallet extends AppCompatActivity implements WebCompleteTask {

    private ActivityMyWalletBinding binding;
    private GrocitoWalletAdapter grocitoWalletAdapter;
    private ReferralWalletAdapter referralWalletAdapter;
    private List<GrocitoWalletModel.GrocitoWallet> grocitoWalletModelList = new ArrayList<>();
    private List<GrocitoWalletModel.ReferWallet> referralModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_wallet);

        binding.headlyaout.searchIcon.setVisibility(View.GONE);
        binding.headlyaout.cartRL.setVisibility(View.GONE);
        binding.headlyaout.backBtn.setOnClickListener(v -> finish());
        binding.headlyaout.productCatName.setText(getString(R.string.my_wallet));

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        grocitoWalletAdapter = new GrocitoWalletAdapter(this,grocitoWalletModelList);
        binding.recyclerView.setAdapter(grocitoWalletAdapter);

        binding.grocitoWalletTv.setOnClickListener(v -> {
            binding.grocitoWalletTv.setBackground(getResources().getDrawable(R.drawable.round_conner_primary));
            binding.grocitoWalletTv.setTextColor(getResources().getColor(R.color.white));
            binding.referralWalletTv.setBackground(getResources().getDrawable(R.drawable.border_light_radius_pri));
            binding.referralWalletTv.setTextColor(getResources().getColor(R.color.md_blue_grey_800));

            grocitoWalletAdapter = new GrocitoWalletAdapter(this,grocitoWalletModelList);
            binding.recyclerView.setAdapter(grocitoWalletAdapter);
        });

        binding.referralWalletTv.setOnClickListener(v -> {
            binding.referralWalletTv.setBackground(getResources().getDrawable(R.drawable.round_conner_primary));
            binding.referralWalletTv.setTextColor(getResources().getColor(R.color.white));
            binding.grocitoWalletTv.setBackground(getResources().getDrawable(R.drawable.border_light_radius_pri));
            binding.grocitoWalletTv.setTextColor(getResources().getColor(R.color.md_blue_grey_800));

            referralWalletAdapter = new ReferralWalletAdapter(this,referralModelList);
            binding.recyclerView.setAdapter(referralWalletAdapter);
        });

        binding.addMoneyTv.setOnClickListener(v ->
                startActivity(new Intent(MyWallet.this,AddMoney.class)
                        .putExtra("wallet_amount",binding.totalAmountTv.getText().toString()))
        );

//        myWallet();

    }

    @Override
    protected void onStart() {
        super.onStart();
        myWallet();
    }

    public void myWallet(){
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        Log.i("myWallet_obj",objectNew+"");
        new WebTask(this, WebUrls.BASE_URL+WebUrls.GetUserWallet,objectNew,
                MyWallet.this, RequestCode.CODE_MyWallet,5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_MyWallet==taskcode){
            Log.i("myWallet_res",response+"");

            GrocitoWalletModel grocitoWalletModel = JsonDeserializer.deserializeJson(response, GrocitoWalletModel.class);

//            if (grocitoWalletModel.statusCode==1){
            binding.totalAmountTv.setText(grocitoWalletModel.totalAmount);
            grocitoWalletModelList.clear();
            referralModelList.clear();
            grocitoWalletModelList.addAll(grocitoWalletModel.grocitoWallet);
            referralModelList.addAll(grocitoWalletModel.referWallet);
            grocitoWalletAdapter.notifyDataSetChanged();

        }
    }
}
