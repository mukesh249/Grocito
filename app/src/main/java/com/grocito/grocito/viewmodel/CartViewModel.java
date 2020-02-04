package com.grocito.grocito.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.model.CartDataModel;

public class CartViewModel extends ViewModel implements WebCompleteTask {
   //this is the data that we will fetch asynchronously
    private MutableLiveData<List<CartDataModel.Datum>> heroList;
    private List<CartDataModel.Datum> arrayList = new ArrayList<>();
    private Context context;

    //we will call this method to get the data
    public LiveData<List<CartDataModel.Datum>> getHeroes() {
        //if the list is null
        if (heroList == null) {
            heroList = new MutableLiveData<List<CartDataModel.Datum>>();
            //we will load it asynchronously from server in this method
            cartList();
        }

        //finally we will return_cancel the list
        return heroList;
    }


    //This method is using Retrofit to get the JSON data from URL
    public void cartList(){
//        Utils.ProgressShow(this, cartBinding.matrialProgress, cartBinding.cartRecyclerView);
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));

        Log.i("ProductListing_obj", objectNew+"");
        new WebTask(context, WebUrls.BASE_URL+WebUrls.GetCartData,
                objectNew, CartViewModel.this, RequestCode.CODE_GetCartData,5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_GetCartData == taskcode){
            Log.i("GetCartData_res", response);
            CartDataModel cartDataModel = JsonDeserializer.deserializeJson(response,CartDataModel.class);
            if (cartDataModel.status == 1){
                this.arrayList.addAll(cartDataModel.data);
                heroList.setValue(arrayList);
            }
//            Utils.ProgressHide(this, cartBinding.matrialProgress, cartBinding.cartRecyclerView);

        }
    }
//    private void loadHeroes() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(WebUrls.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        Api api = retrofit.create(Api.class);
//        Call<List<CartDataModel>> call = api.getHeroes();
//
//
//        call.enqueue(new Callback<List<CartDataModel>>() {
//            @Override
//            public void onResponse(Call<List<CartDataModel>> call, Response<List<CartDataModel>> response) {
//
//                //finally we are setting the list to our MutableLiveData
//                heroList.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<CartDataModel>> call, Throwable t) {
//
//            }
//        });
//    }
}
