package com.grocito.grocito.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.JsonObject;
import com.grocito.grocito.R;
import com.grocito.grocito.adapter.SearchAdapter;
import com.grocito.grocito.api.ApiConfig;
import com.grocito.grocito.api.AppConfig;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivitySearchItemBinding;
import com.grocito.grocito.model.SearchModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchItem extends AppCompatActivity {

    ArrayList<SearchModel.Datum> arrayList = new ArrayList<>();
    ActivitySearchItemBinding binding;
    SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_item);

        binding.headlyaout.productCatName.setText(getResources().getString(R.string.app_name));
        binding.headlyaout.cartRL.setVisibility(View.GONE);
        binding.headlyaout.searchIcon.setVisibility(View.GONE);
        binding.headlyaout.headViewLine.setVisibility(View.GONE);

        binding.headlyaout.backBtn.setOnClickListener(view -> onBackPressed());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(SearchItem.this, arrayList);
        binding.recyclerView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();

        binding.searchEditText.setFocusable(true);
        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.searchEditText.getText().toString().isEmpty()){
                    binding.cancelBt.setVisibility(View.GONE);
                    binding.progress.setVisibility(View.GONE);
                    binding.emptyListLL.setVisibility(View.GONE);
                    arrayList.clear();
                    searchAdapter.notifyDataSetChanged();
                }else {
                    binding.progress.setVisibility(View.VISIBLE);
                    binding.cancelBt.setVisibility(View.GONE);
                    searchMethod();
                }
//                new searchProducts().execute();
            }
        });
        binding.cancelBt.setOnClickListener(v -> {
            binding.cancelBt.setVisibility(View.GONE);
            binding.progress.setVisibility(View.GONE);
            binding.emptyListLL.setVisibility(View.GONE);
            binding.searchEditText.setText("");
            arrayList.clear();
            searchAdapter.notifyDataSetChanged();
        });
    }

//    private class searchProducts extends AsyncTask<String, String, String> {
//        //        public ProgressDialog pd;
//
//        String pincode = SharedPrefManager.getPinCode(Constrants.PinCode);
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            pd=new ProgressDialog(ProductUpload.this);
////            pd.setMessage("Loading...");
////            pd.setCancelable(false);
////            pd.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {

    public void searchMethod() {
        String pincode = SharedPrefManager.getPinCode(Constrants.PinCode);
        //----------------------------------Using Retrofit------------
//        final String[] str_response = new String[0];
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        Call<JsonObject> call = getResponse.searchProduct(pincode, binding.searchEditText.getText().toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("search_res", response.body() + "");
//                str_response[0] = response.body() + "";
                SearchModel searchModel = JsonDeserializer.deserializeJson(response.body().toString(), SearchModel.class);
                if (searchModel.statusCode == 1) {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.emptyListLL.setVisibility(View.GONE);
                    arrayList.clear();
                    arrayList.addAll(searchModel.data);
                    searchAdapter.notifyDataSetChanged();
                }else {
                    binding.emptyList.titleEmpty.setText("Search item not Found");
                    binding.emptyList.desEmpty.setText("Search item not found,we will let you know as soon as available");
                    binding.emptyList.imageEmpty.setImageDrawable(getDrawable(R.drawable.product_not_found));
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.emptyListLL.setVisibility(View.VISIBLE);
                }
                binding.progress.setVisibility(View.GONE);
                binding.cancelBt.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("search_error", t.getMessage());
            }
        });
    }
//            return_cancel str_response[0];
//        }
//
//        @Override
//        protected void onPostExecute(String response) {
//            super.onPostExecute(response);
//            System.out.println("GetSearchItem_res: " + response);
////            SearchModel searchModel = JsonDeserializer.deserializeJson(response,SearchModel.class);
////            if (searchModel.statusCode==1){
//////                arrayList.addAll(searchModel.data);
//////                searchAdapter.notifyDataSetChanged();
////            }
//
//        }
//    }
}
