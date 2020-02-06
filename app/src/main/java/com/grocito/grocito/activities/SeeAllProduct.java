package com.grocito.grocito.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;

import com.grocito.grocito.R;
import com.grocito.grocito.adapter.FilterBrandAdapter;
import com.grocito.grocito.adapter.FilterBrandTypeAdapter;
import com.grocito.grocito.adapter.FilterCatAdapter;
import com.grocito.grocito.adapter.SeeAllProductAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.BottomSheetFragment;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.MyApplication;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivitySeeAllProductBinding;
import com.grocito.grocito.fragments.DailogFragment;
import com.grocito.grocito.model.SeeAllProductsModel;
import com.grocito.grocito.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class SeeAllProduct extends AppCompatActivity implements WebCompleteTask {

    private ActivitySeeAllProductBinding binding;
    private SeeAllProductAdapter allProductAdapter;
    private List<SeeAllProductsModel.ProductList> arrayList = new ArrayList<>();
//    private List<SeeAllProductsModel.ProductList> arrayListDemo = new ArrayList<>();
    private String catid = "", subcatid = "", catName = "";
    private static SeeAllProduct mInstance;
    AlertDialog progressDialog;
    private int countitem = 0;
    public static String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_see_all_product);
        progressDialog = new SpotsDialog(this, R.style.Custom);
        mInstance = this;
        if (getIntent().getExtras() != null)
            binding.headlyaout.productCatName.setText(getIntent().getExtras().getString("name", ""));

        binding.productRecyView.setLayoutManager(new GridLayoutManager(this,2));
        MyApplication.RecyclerView(binding.productRecyView);

        binding.headlyaout.searchIcon.setOnClickListener(view -> startActivity(new Intent(SeeAllProduct.this, SearchItem.class)));
        FilterCatAdapter.cate_id = "";
        FilterBrandAdapter.brand_id = "";
        BottomSheetFragment.min_price = "";
        BottomSheetFragment.max_price = "";
        BottomSheetFragment.min_ofr = "";
        BottomSheetFragment.max_ofr = "";
        binding.headlyaout.backBtn.setOnClickListener(view ->
        {
            FilterCatAdapter.cate_id = "";
            FilterBrandAdapter.brand_id = "";
            BottomSheetFragment.min_price = "";
            BottomSheetFragment.max_price = "";
            BottomSheetFragment.min_ofr = "";
            BottomSheetFragment.max_ofr = "";
            finish();
        });

        binding.headlyaout.cartIcon.setOnClickListener(view -> startActivity(new Intent(SeeAllProduct.this, Cart.class)));

        if (getIntent().getExtras() != null) {
            type = getIntent().getExtras().getString("type", "");
            catid = getIntent().getExtras().getString("cat_id", "");
            subcatid = getIntent().getExtras().getString("subCatId", "");
        }
//        if (SharedPrefManager.getCartItemCount(Constrants.CartItemCount)>0){
//            binding.headlyaout.cartItemNo.setVisibility(View.VISIBLE);
//            binding.headlyaout.cartItemNo.setText(SharedPrefManager.getCartItemCount(Constrants.CartItemCount) + "");
//        }else {
//            binding.headlyaout.cartItemNo.setVisibility(View.GONE);
//        }

        if (type.equals("is_sunday_bazar")) {
            seeAllOffer(type);
        } else if (type.equals("is_grocito_exclusive")) {
            seeAllOffer(type);
        } else if (type.equals("new")) {
            seeAllOffer(type);
        } else {
            if (!subcatid.isEmpty()) {
                FilterCatAdapter.cate_id = subcatid;
            }
            productList(subcatid);
        }

        binding.filterBtn.setOnClickListener(v -> {
//            DailogFragment newFragment = new DailogFragment();

//            if (arrayList != null && arrayList.size() > 0) {
//                BottomSheetFragment fragment = new BottomSheetFragment();
                DailogFragment fragment = new DailogFragment();
                Bundle args = new Bundle();
                args.putString("cat_id", catid);
                args.putString("subCatId", subcatid);
                args.putString("catName", catName);
                fragment.setArguments(args);
//                fragment.show(getSupportFragmentManager(), "TAG");
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(android.R.id.content, fragment).addToBackStack(null).commit();

//            }
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().add(android.R.id.content, newFragment).addToBackStack(null).commit();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        countitem = SharedPrefManager.getCartItemCount(Constrants.CartItemCount);
        addCart(countitem);
    }

    public void seeAllOffer(String type) {
        Utils.ProgressShow(this, binding.matrialProgress, binding.productRecyView);
        HashMap objectNew = new HashMap();
        objectNew.put("type", type);
        objectNew.put("pincode", SharedPrefManager.getPinCode(Constrants.PinCode));
        objectNew.put("brand_id", FilterBrandTypeAdapter.brand_id + "");
        objectNew.put("min_price", BottomSheetFragment.min_price + "");
        objectNew.put("max_price", BottomSheetFragment.max_price + "");
        objectNew.put("min_offer", BottomSheetFragment.min_ofr + "");
        objectNew.put("max_offer", BottomSheetFragment.max_ofr + "");
        Log.i("seeAllOffer_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.ProductTypeListing, objectNew,
                SeeAllProduct.this, RequestCode.CODE_ProductTypeListing, 5);
    }

    public void productList(String subcat_id) {
        Utils.ProgressShow(this, binding.matrialProgress, binding.productRecyView);
        HashMap objectNew = new HashMap();
        objectNew.put("cat_id", catid);
        objectNew.put("sub_cat_id", subcat_id);
        objectNew.put("brand_id", FilterBrandAdapter.brand_id + "");
        objectNew.put("min_price", BottomSheetFragment.min_price + "");
        objectNew.put("max_price", BottomSheetFragment.max_price + "");
        objectNew.put("min_offer", BottomSheetFragment.min_ofr + "");
        objectNew.put("max_offer", BottomSheetFragment.max_ofr + "");
        objectNew.put("pincode", SharedPrefManager.getPinCode(Constrants.PinCode));
        Log.i("ProductListing_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.ProductListing, objectNew,
                SeeAllProduct.this, RequestCode.CODE_ProductListing, 5);
    }

    @Override
    public void onComplete(String response, int taskcode) {

        if (RequestCode.CODE_ProductListing == taskcode) {
            Log.i("ProductListing_res", response);
            SeeAllProductsModel seeAllProductsModel = JsonDeserializer.deserializeJson(response, SeeAllProductsModel.class);
            if (seeAllProductsModel.statusCode == 1) {
                this.arrayList.clear();
                this.arrayList.addAll(seeAllProductsModel.data.productList);
                binding.productRecyView.setVisibility(View.VISIBLE);
                binding.emptyListLL.setVisibility(View.GONE);
                allProductAdapter = new SeeAllProductAdapter(this, seeAllProductsModel.data.productList);
                binding.productRecyView.setAdapter(allProductAdapter);
//                allProductAdapter.notifyDataSetChanged();
//                if (!seeAllProductsModel.data.productList.isEmpty()) {
//                    binding.headlyaout.productCatName.setText(seeAllProductsModel.data.catdata.name);
//                    catName = seeAllProductsModel.data.catdata.name;
//                    allProductAdapter.notifyDataSetChanged();
//                } else {
//                    binding.productRecyView.setVisibility(View.GONE);
//                    binding.emptyListLL.setVisibility(View.VISIBLE);
//                }
            }
            Utils.ProgressHide(this, binding.matrialProgress, binding.productRecyView);
        }
        if (RequestCode.CODE_ProductTypeListing == taskcode) {
            Log.i("seeAllOffer_res", response);
            SeeAllProductsModel seeAllProductsModel = JsonDeserializer.deserializeJson(response, SeeAllProductsModel.class);
            if (seeAllProductsModel.statusCode == 1) {
                this.arrayList.clear();
                this.arrayList.addAll(seeAllProductsModel.data.productList);
                binding.productRecyView.setVisibility(View.VISIBLE);
                binding.emptyListLL.setVisibility(View.GONE);
                if (!seeAllProductsModel.data.productList.isEmpty()) {
                    if (type.equals("is_sunday_bazar")) {
                        binding.headlyaout.productCatName.setText(getString(R.string.sunday_bazar));
                    } else if (type.equals("is_grocito_exclusive")) {
                        binding.headlyaout.productCatName.setText(getString(R.string.grocito_exclusive));
                    } else if (type.equals("new")) {
                        binding.headlyaout.productCatName.setText(getString(R.string.new_arrival));
                    }
//                    else {
//                        binding.headlyaout.productCatName.setText(seeAllProductsModel.data.catdata.name);
//                    }
                    allProductAdapter = new SeeAllProductAdapter(this, seeAllProductsModel.data.productList);
                    binding.productRecyView.setAdapter(allProductAdapter);
//                    allProductAdapter.notifyDataSetChanged();

                } else {
                    binding.productRecyView.setVisibility(View.GONE);
                    binding.emptyListLL.setVisibility(View.VISIBLE);
                }
            }
            Utils.ProgressHide(this, binding.matrialProgress, binding.productRecyView);
        }

    }

    public static SeeAllProduct getInstance() {
        return mInstance;
    }

    public void addCart(Integer count) {
//        Utils.CartCount(this, binding.headlyaout.cartItemNo, count);
        if (binding.headlyaout.cartItemNo != null) {
            if (count > 0) {
                binding.headlyaout.cartItemNo.setVisibility(View.VISIBLE);
            } else {
                binding.headlyaout.cartItemNo.setVisibility(View.GONE);
            }
            binding.headlyaout.cartItemNo.setText(String.valueOf(count));
        }
    }

    public void proShow(Boolean value, String message) {
        if (!progressDialog.isShowing())
            progressDialog.show();
        else {
            progressDialog.dismiss();
            Utils.Toast(this, message);
        }
    }


//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        if (parent.getId() == R.id.capcity_spinner) {
//            int poss = (int) parent.getTag();
//            arrayList.get(poss).selcted=position;
//          // allProductAdapter.notifyItemChanged(poss);
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
