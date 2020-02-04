package com.grocito.grocito.activities;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.grocito.grocito.R;
import com.grocito.grocito.adapter.GoogleSearchAddressAdapter;
import com.grocito.grocito.adapter.SelectionAddressAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivitySelectLocationBinding;
import com.grocito.grocito.model.AddressModelGoogle;
import com.grocito.grocito.model.SelectedAddressModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectLocation extends AppCompatActivity implements WebCompleteTask {

    ActivitySelectLocationBinding binding;
    List<SelectedAddressModel.Datum> arrayList = new ArrayList<>();
    SelectionAddressAdapter searchAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_select_location);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SelectionAddressAdapter(this, arrayList);
        binding.recyclerView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
        binding.searchHeader.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.searchHeader.searchTv.setText("Change Address");
        binding.searchHeader.searchIcon.setVisibility(View.GONE);
//        binding.searchHeader.searchTv.setTextColor(getResources().getColor(R.color.black));
        binding.currentLocatinLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectLocation.this,CurrentLocation.class));
            }
        });

        binding.addAddressLL.setOnClickListener(view -> startActivity(new Intent(SelectLocation.this,AddNewAddress.class)));
        selectionAddress();
    }
//    private class MyAsyncTask extends AsyncTask<String , String , String > {
//        protected String doInBackground(String... urls) {
//            // do some thing in background
//            return result;
//        }
//        @Override
//        protected void onPreExecute() {
//
//            // this will execute on main thread before Method doInBackground()
//
//            super.onPreExecute();
//        }
//
//        protected void onPostExecute(String result) {
//// this will execute on main thread after Method doInBackground()
//
//        }
//    }
    public void selectionAddress(){
        HashMap objectNew = new HashMap();
        objectNew.put("pincode", SharedPrefManager.getPinCode(Constrants.PinCode));
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        new WebTask(SelectLocation.this, WebUrls.BASE_URL+WebUrls.GetUserAddress,objectNew,SelectLocation.this, RequestCode.CODE_GetUserAddress,5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_GetUserAddress == taskcode) {
            Log.i("selectionAddress_res", response);

            SelectedAddressModel searchModel = JsonDeserializer.deserializeJson(response, SelectedAddressModel.class);
            if (searchModel.statusCode == 1) {
                binding.recyclerView.setVisibility(View.VISIBLE);
//                    binding.emptyListLL.setVisibility(View.GONE);
                arrayList.clear();
                if (searchModel.data.isEmpty()){
                    binding.youraddrTv.setVisibility(View.GONE);
                }else {
                    binding.youraddrTv.setVisibility(View.VISIBLE);
                }
                arrayList.addAll(searchModel.data);
                searchAdapter.notifyDataSetChanged();
            } else {
//                    binding.emptyList.titleEmpty.setText("Search item not Found");
//                    binding.emptyList.desEmpty.setText("Search item not found,we will let you know as soon as available");
//                    binding.emptyList.imageEmpty.setImageDrawable(getDrawable(R.drawable.product_not_found));
                binding.recyclerView.setVisibility(View.GONE);
//                    binding.emptyListLL.setVisibility(View.VISIBLE);
            }
//            binding.progress.setVisibility(View.GONE);
//            binding.cancelBt.setVisibility(View.VISIBLE);
        }
    }
}
