package com.grocito.grocito.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.grocito.grocito.R;
import com.grocito.grocito.adapter.MyOrderListAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityMyOrderListBinding;
import com.grocito.grocito.model.MyOrderListModel;

import java.util.ArrayList;
import java.util.HashMap;

public class MyOrderList extends AppCompatActivity implements WebCompleteTask {

    private ActivityMyOrderListBinding binding;
    private ArrayList<MyOrderListModel.Datum> arrayList = new ArrayList<>();
    private MyOrderListAdapter adapter;
    public static MyOrderList mInstance;
    private boolean isRefreshing = false;
    private boolean forceRefresh = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_order_list);

        mInstance = this;
        binding.headlyaout.cartRL.setVisibility(View.GONE);
        binding.headlyaout.searchIcon.setVisibility(View.GONE);
        binding.headlyaout.productCatName.setText(getResources().getString(R.string.my_order));
        binding.headlyaout.backBtn.setOnClickListener(view -> finish());

        binding.continueshopBtn.setOnClickListener(view -> {
            startActivity(new Intent(MyOrderList.this,HomeScreen.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyOrderListAdapter(this,arrayList);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.refresh.setOnRefreshListener(() -> {
            if (!isRefreshing) {
                forceRefresh = true;
                binding.refresh.setRefreshing(true);
                myOrderList();
            }
        });

        myOrderList();

    }

    public void myOrderList(){
        binding.matrialProgress.setVisibility(View.VISIBLE);
        binding.emptyLL.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.GONE);
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        new WebTask(this, WebUrls.BASE_URL+WebUrls.UserOrderList,objectNew,
                MyOrderList.this, RequestCode.CODE_UserOrderList,5);
    }
    public void myOrderListCancel(){
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        new WebTask(this, WebUrls.BASE_URL+WebUrls.UserOrderList,objectNew,
                MyOrderList.this, RequestCode.CODE_UserOrderList,5);
    }

    public static MyOrderList getInstance(){
        return mInstance;
    }

    @Override
    public void onComplete(String response, int taskcode) {

        if (RequestCode.CODE_UserOrderList == taskcode){
            Log.i("UserOrderList_res",response);
            MyOrderListModel orderListModel = JsonDeserializer.deserializeJson(response, MyOrderListModel.class);
            if (orderListModel.statusCode==1) {

                forceRefresh = false;
                isRefreshing = false;
                binding.refresh.setRefreshing(false);

                binding.matrialProgress.setVisibility(View.GONE);
                if (orderListModel.data.isEmpty()){
                    binding.emptyLL.setVisibility(View.VISIBLE);
                    binding.recyclerView.setVisibility(View.GONE);
                }else {
                    arrayList.clear();
                    binding.emptyLL.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    arrayList.addAll(orderListModel.data);
                    adapter.notifyDataSetChanged();
                }
            }else {
                binding.matrialProgress.setVisibility(View.GONE);
                binding.emptyLL.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            }
        }

    }
}
