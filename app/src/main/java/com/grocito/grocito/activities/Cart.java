package com.grocito.grocito.activities;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.grocito.grocito.adapter.CartAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.BottomSheetFragment;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.MyApplication;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.model.CartDataModel;
import com.grocito.grocito.R;
import com.grocito.grocito.databinding.ActivityCartBinding;
import com.grocito.grocito.utils.Utils;

@SuppressLint("DefaultLocale")
public class Cart extends AppCompatActivity implements WebCompleteTask {

    private ActivityCartBinding cartBinding;
    CartAdapter cartAdapter;
    private List<CartDataModel.Datum> arrayList = new ArrayList<>();
    private static Cart mInstance;
    double amt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart);

        mInstance = this;
        cartBinding.headlyaout.productCatName.setText("My Cart");
        cartBinding.headlyaout.cartRL.setVisibility(View.GONE);
        cartBinding.headlyaout.searchIcon.setVisibility(View.GONE);
        cartBinding.headlyaout.backBtn.setOnClickListener(view -> finish());

        cartBinding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyApplication.RecyclerView(cartBinding.cartRecyclerView);

        cartAdapter = new CartAdapter(this, arrayList);
        cartBinding.cartRecyclerView.setAdapter(cartAdapter);
        cartList();

        ((GradientDrawable) cartBinding.bottomLL.getBackground()).setColor(getResources().getColor(R.color.colorPrimary));

        BottomSheetFragment fragment = new BottomSheetFragment();

        cartBinding.ProccedCheckout.setOnClickListener(view ->
                        startActivity(new Intent(Cart.this, Payment.class).putExtra("amt",amt))
//                fragment.show(getSupportFragmentManager(), "TAG")
        );
        cartBinding.continueshopBtn.setOnClickListener(view -> {
            startActivity(new Intent(Cart.this,HomeScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        });

    }

    public void cartList() {
        Utils.ProgressShow(this, cartBinding.matrialProgress, cartBinding.cartRecyclerView);
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));

        Log.i("ProductListing_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.GetCartData,
                objectNew, Cart.this, RequestCode.CODE_GetCartData, 5);
    }

    double sum = 0;

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_GetCartData == taskcode) {
            Log.i("GetCartData_res", response);
            CartDataModel cartDataModel = JsonDeserializer.deserializeJson(response, CartDataModel.class);
            if (cartDataModel.status == 1) {
                arrayList.clear();
                if (cartDataModel.data.size()>0) {
                    cartBinding.emptyLL.setVisibility(View.GONE);
                    cartBinding.cartRecyclerView.setVisibility(View.VISIBLE);
                    cartBinding.bottomLL.setVisibility(View.VISIBLE);

                    this.arrayList.addAll(cartDataModel.data);
                    cartAdapter.notifyDataSetChanged();
                    cartBinding.totalTv.setText(String.format("₹%.0f", totalSum()));
                }else {
                    cartBinding.emptyLL.setVisibility(View.VISIBLE);
                    cartBinding.cartRecyclerView.setVisibility(View.GONE);
                    cartBinding.bottomLL.setVisibility(View.GONE);
                }
            }
            Utils.ProgressHide(this, cartBinding.matrialProgress, cartBinding.cartRecyclerView);
        }
    }

    public double totalSum() {
        amt = 0;
        double total = 0;
        for (CartDataModel.Datum datum : arrayList) {
            double qtyAmount = Double.parseDouble(datum.qty) * Double.parseDouble(datum.sprice);
            total = total + qtyAmount;
        }
        amt = total;
        return total;
    }

    public static Cart getInstance(){
        return mInstance;
    }

    public void setTotalSum(){
        cartBinding.totalTv.setText(String.format("₹%.0f",totalSum()));
    }
    public void showProgress(){
        cartBinding.matrialProgress.setVisibility(View.VISIBLE);
    }

    public void hideProgress(){
        cartBinding.matrialProgress.setVisibility(View.GONE);
    }


}
