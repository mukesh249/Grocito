package com.grocito.grocito.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.grocito.grocito.R;
import com.grocito.grocito.adapter.GoogleSearchAddressAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.databinding.ActivitySearchAddressGoogleBinding;
import com.grocito.grocito.model.AddressModelGoogle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchAddressGoogle extends AppCompatActivity implements WebCompleteTask {

    List<AddressModelGoogle.Prediction> arrayList = new ArrayList<>();
    GoogleSearchAddressAdapter searchAdapter;
    private ActivitySearchAddressGoogleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_address_google);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new GoogleSearchAddressAdapter(this, arrayList);
        binding.recyclerView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();

        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.searchEditText.getText().toString().isEmpty()) {
                    binding.cancelBt.setVisibility(View.GONE);
                    binding.progress.setVisibility(View.GONE);
//                    binding.emptyListLL.setVisibility(View.GONE);
                    arrayList.clear();
                    searchAdapter.notifyDataSetChanged();
                } else {
                    binding.progress.setVisibility(View.VISIBLE);
                    binding.cancelBt.setVisibility(View.GONE);
                    searchAddress(binding.searchEditText.getText().toString());
                }
//                new searchProducts().execute();
            }
        });
    }

    public void searchAddress(String search) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://maps.googleapis.com/maps/api/place/queryautocomplete/json?key" +
                "=AIzaSyBTeuBxlcuFM8nC-OIkoxz4vqyGQA2OJ40&language=in&input=";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+search,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("SearchAddressGoogle_res", response);

                        AddressModelGoogle searchModel = JsonDeserializer.deserializeJson(response, AddressModelGoogle.class);
                        if (searchModel.status.equals("OK")) {
                            binding.recyclerView.setVisibility(View.VISIBLE);
//                    binding.emptyListLL.setVisibility(View.GONE);
                            arrayList.clear();
                            arrayList.addAll(searchModel.predictions);
                            searchAdapter.notifyDataSetChanged();
                        } else {
//                    binding.emptyList.titleEmpty.setText("Search item not Found");
//                    binding.emptyList.desEmpty.setText("Search item not found,we will let you know as soon as available");
//                    binding.emptyList.imageEmpty.setImageDrawable(getDrawable(R.drawable.product_not_found));
                            binding.recyclerView.setVisibility(View.GONE);
//                    binding.emptyListLL.setVisibility(View.VISIBLE);
                        }
                        binding.progress.setVisibility(View.GONE);
                        binding.cancelBt.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Sea_error", error.toString());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


//        HashMap objectNew = new HashMap();
//        new WebTask(SearchAddressGoogle.this,
//                url
//                        + search, objectNew, SearchAddressGoogle.this, RequestCode.CODE_SearchAddressGoogle, 3);
    }
//    public void searchMethod() {
//        String pincode = SharedPrefManager.getPinCode(Constrants.PinCode);
//        //----------------------------------Using Retrofit------------
////        final String[] str_response = new String[0];
//        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
//        Call<JsonObject> call = getResponse.searchProduct(pincode, binding.searchEditText.getText().toString());
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                Log.i("search_res", response.body() + "");
////                str_response[0] = response.body() + "";
//                SearchModel searchModel = JsonDeserializer.deserializeJson(response.body().toString(), SearchModel.class);
//                if (searchModel.statusCode == 1) {
//                    binding.recyclerView.setVisibility(View.VISIBLE);
////                    binding.emptyListLL.setVisibility(View.GONE);
//                    arrayList.clear();
//                    arrayList.addAll(searchModel.data);
//                    searchAdapter.notifyDataSetChanged();
//                }else {
//                    binding.emptyList.titleEmpty.setText("Search item not Found");
//                    binding.emptyList.desEmpty.setText("Search item not found,we will let you know as soon as available");
//                    binding.emptyList.imageEmpty.setImageDrawable(getDrawable(R.drawable.product_not_found));
//                    binding.recyclerView.setVisibility(View.GONE);
//                    binding.emptyListLL.setVisibility(View.VISIBLE);
//                }
//                binding.progress.setVisibility(View.GONE);
//                binding.cancelBt.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Log.i("search_error", t.getMessage());
//            }
//        });
//    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_SearchAddressGoogle == taskcode) {
            Log.i("SearchAddressGoogle_res", response);

            AddressModelGoogle searchModel = JsonDeserializer.deserializeJson(response, AddressModelGoogle.class);
            if (searchModel.status.equals("OK")) {
                binding.recyclerView.setVisibility(View.VISIBLE);
//                    binding.emptyListLL.setVisibility(View.GONE);
                arrayList.clear();
                arrayList.addAll(searchModel.predictions);
                searchAdapter.notifyDataSetChanged();
            } else {
//                    binding.emptyList.titleEmpty.setText("Search item not Found");
//                    binding.emptyList.desEmpty.setText("Search item not found,we will let you know as soon as available");
//                    binding.emptyList.imageEmpty.setImageDrawable(getDrawable(R.drawable.product_not_found));
                binding.recyclerView.setVisibility(View.GONE);
//                    binding.emptyListLL.setVisibility(View.VISIBLE);
            }
            binding.progress.setVisibility(View.GONE);
            binding.cancelBt.setVisibility(View.VISIBLE);
        }
    }
}
