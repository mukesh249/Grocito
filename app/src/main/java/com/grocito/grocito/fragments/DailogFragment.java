package com.grocito.grocito.fragments;


import android.app.Dialog;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.SeeAllProduct;
import com.grocito.grocito.adapter.FilterBrandAdapter;
import com.grocito.grocito.adapter.FilterBrandTypeAdapter;
import com.grocito.grocito.adapter.FilterCatAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.BottomSheetFragment;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.FragmentDailogBinding;
import com.grocito.grocito.model.FilterBrandTypModel;
import com.grocito.grocito.model.FilterCatModel;
import com.grocito.grocito.utils.Utils;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailogFragment extends DialogFragment implements WebCompleteTask {


    public DailogFragment() {
        // Required empty public constructor
    }

//    private BottomSheetLayoutBinding bottomSheetLayoutBinding;
    public static String cat_id = "",subcat_id = "",min_price="",max_price="",min_ofr="",max_ofr="";
    private FilterCatAdapter filterCatAdapter;
    private FilterBrandAdapter filterBrandAdapter;
    private FilterBrandTypeAdapter filterBrandTypeAdapter;
    private FragmentDailogBinding bottomSheetLayoutBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bottomSheetLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_dailog, container, false);
        setCancelable(false);
        bottomSheetLayoutBinding.rangeSeekbar1.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            min_price = minValue.toString();
            max_price = maxValue.toString();
            bottomSheetLayoutBinding.minPrTv.setText(String.valueOf(minValue));
            bottomSheetLayoutBinding.maxPrTv.setText(String.valueOf(maxValue));
        });

// set final value listener
        bottomSheetLayoutBinding.rangeSeekbar1.setOnRangeSeekbarFinalValueListener((minValue, maxValue) ->
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue)));


        bottomSheetLayoutBinding.checkbox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                min_ofr = "0";
                max_ofr = "100";
                bottomSheetLayoutBinding.checkbox1.setChecked(true);
                bottomSheetLayoutBinding.checkbox2.setChecked(false);
                bottomSheetLayoutBinding.checkbox3.setChecked(false);
                bottomSheetLayoutBinding.checkbox4.setChecked(false);
            }
        });
        bottomSheetLayoutBinding.checkbox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                min_ofr = "101";
                max_ofr = "250";
                bottomSheetLayoutBinding.checkbox1.setChecked(false);
                bottomSheetLayoutBinding.checkbox2.setChecked(true);
                bottomSheetLayoutBinding.checkbox3.setChecked(false);
                bottomSheetLayoutBinding.checkbox4.setChecked(false);
            }
        });
        bottomSheetLayoutBinding.checkbox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                min_ofr = "251";
                max_ofr = "500";
                bottomSheetLayoutBinding.checkbox1.setChecked(false);
                bottomSheetLayoutBinding.checkbox2.setChecked(false);
                bottomSheetLayoutBinding.checkbox3.setChecked(true);
                bottomSheetLayoutBinding.checkbox4.setChecked(false);
            }
        });
        bottomSheetLayoutBinding.checkbox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                min_ofr = "500";
                max_ofr = "1000";
                bottomSheetLayoutBinding.checkbox1.setChecked(false);
                bottomSheetLayoutBinding.checkbox2.setChecked(false);
                bottomSheetLayoutBinding.checkbox3.setChecked(false);
                bottomSheetLayoutBinding.checkbox4.setChecked(true);
            }
        });



        bottomSheetLayoutBinding.recyclerViewSubCat.setLayoutManager(new LinearLayoutManager(getActivity()));
        bottomSheetLayoutBinding.recyclerViewBrand.setLayoutManager(new LinearLayoutManager(getActivity()));

        ((GradientDrawable)bottomSheetLayoutBinding.getRoot().getBackground()).setColor(getResources().getColor(R.color.white));


//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) bottomSheetLayoutBinding.getRoot().getParent()).getLayoutParams();
//        CoordinatorLayout.Behavior behavior = params.getBehavior();
//        ((View)bottomSheetLayoutBinding.getRoot().getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        bottomSheetLayoutBinding.crossIv.setOnClickListener(v -> dismiss());
//        if (behavior != null && behavior instanceof BottomSheetBehavior) {
//            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//                @Override
//                public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                    String state = "";
//
//                    switch (newState) {
//                        case BottomSheetBehavior.STATE_DRAGGING: {
//                            state = "DRAGGING";
//                            break;
//                        }
//                        case BottomSheetBehavior.STATE_SETTLING: {
//                            state = "SETTLING";
//                            break;
//                        }
//                        case BottomSheetBehavior.STATE_EXPANDED: {
//                            state = "EXPANDED";
//                            break;
//                        }
//                        case STATE_COLLAPSED: {
//                            state = "COLLAPSED";
//                            break;
//                        }
//                        case BottomSheetBehavior.STATE_HIDDEN: {
//                            dismiss();
//                            state = "HIDDEN";
//                            break;
//                        }
//                    }
//
////                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                }
//            });
//        }

        if (getActivity().getIntent().getExtras() != null) {
            cat_id = getActivity().getIntent().getExtras().getString("cat_id", "");
            subcat_id = getActivity().getIntent().getExtras().getString("subCatId", "");
            bottomSheetLayoutBinding.catName.setText(getActivity().getIntent().getExtras().getString("catName", ""));
            if (SeeAllProduct.type.equals("is_sunday_bazar")) {
                bottomSheetLayoutBinding.recyclerViewSubCat.setVisibility(View.GONE);
                seeAllOffer(SeeAllProduct.type);
            } else if (SeeAllProduct.type.equals("is_grocito_exclusive")) {
                bottomSheetLayoutBinding.recyclerViewSubCat.setVisibility(View.GONE);
                seeAllOffer(SeeAllProduct.type);
            } else if (SeeAllProduct.type.equals("new")) {
                bottomSheetLayoutBinding.recyclerViewSubCat.setVisibility(View.GONE);
                seeAllOffer(SeeAllProduct.type);
            }else {
                productList();
            }
        }
        bottomSheetLayoutBinding.applyTv.setOnClickListener(v -> {
            if (SeeAllProduct.type.equals("is_sunday_bazar")) {
                bottomSheetLayoutBinding.recyclerViewSubCat.setVisibility(View.GONE);
                SeeAllProduct.getInstance().seeAllOffer(SeeAllProduct.type);
            } else if (SeeAllProduct.type.equals("is_grocito_exclusive")) {
                bottomSheetLayoutBinding.recyclerViewSubCat.setVisibility(View.GONE);
                SeeAllProduct.getInstance().seeAllOffer(SeeAllProduct.type);
            } else if (SeeAllProduct.type.equals("new")) {
                bottomSheetLayoutBinding.recyclerViewSubCat.setVisibility(View.GONE);
                SeeAllProduct.getInstance().seeAllOffer(SeeAllProduct.type);
            }else {
                SeeAllProduct.getInstance().productList(FilterCatAdapter.cate_id);
            }
            dismiss();
        });
        bottomSheetLayoutBinding.removeTv.setOnClickListener(v -> {
            FilterBrandAdapter.brand_id = "";
            BottomSheetFragment.min_price = "";
            BottomSheetFragment.max_price = "";
            BottomSheetFragment.min_ofr = "";
            BottomSheetFragment.max_ofr = "";

            if (SeeAllProduct.type.equals("is_sunday_bazar")) {
                FilterBrandTypeAdapter.brand_id="";
                bottomSheetLayoutBinding.recyclerViewSubCat.setVisibility(View.GONE);
                SeeAllProduct.getInstance().seeAllOffer(SeeAllProduct.type);
            } else if (SeeAllProduct.type.equals("is_grocito_exclusive")) {
                FilterBrandTypeAdapter.brand_id="";
                bottomSheetLayoutBinding.recyclerViewSubCat.setVisibility(View.GONE);
                SeeAllProduct.getInstance().seeAllOffer(SeeAllProduct.type);
            } else if (SeeAllProduct.type.equals("new")) {
                FilterBrandTypeAdapter.brand_id="";
                bottomSheetLayoutBinding.recyclerViewSubCat.setVisibility(View.GONE);
                SeeAllProduct.getInstance().seeAllOffer(SeeAllProduct.type);
            }else if (SeeAllProduct.type.equals("cate")){
                SeeAllProduct.getInstance().productList(FilterCatAdapter.cate_id);
            }else {
                SeeAllProduct.getInstance().productList("");
            }
            dismiss();
        });

        return bottomSheetLayoutBinding.getRoot();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void seeAllOffer(String type) {
        HashMap objectNew = new HashMap();
        objectNew.put("product_type", type);
        objectNew.put("pincode", SharedPrefManager.getPinCode(Constrants.PinCode));
        Log.i("seeAllOffer_obj", objectNew + "");
        new WebTask(getActivity(), WebUrls.BASE_URL + WebUrls.FilterDataProductType, objectNew,
                this, RequestCode.CODE_FilterDataProductType, 5);
    }
    public void productList() {
        Utils.ProgressShow(requireContext(), bottomSheetLayoutBinding.matrialProgress, bottomSheetLayoutBinding.nestScrollview);
        HashMap objectNew = new HashMap();
        objectNew.put("cat_id", cat_id);
        objectNew.put("sub_cat_id", subcat_id);

        Log.i("Filter_obj", objectNew + "");
        new WebTask(getActivity(), WebUrls.BASE_URL + WebUrls.FilterData, objectNew,
                this, RequestCode.CODE_FilterData, 5);
    }
    @Override
    public void onComplete(String response, int taskcode) {

        if (RequestCode.CODE_FilterData == taskcode) {
            Log.i("Filter_res", response);
            FilterCatModel filterCatModel = JsonDeserializer.deserializeJson(response, FilterCatModel.class);
            if (filterCatModel.statusCode == 1) {
                if (!filterCatModel.data.subCatData.isEmpty()) {
                    filterCatAdapter = new FilterCatAdapter(getActivity(),filterCatModel.data.subCatData);
                    bottomSheetLayoutBinding.recyclerViewSubCat.setAdapter(filterCatAdapter);
                    filterCatAdapter.notifyDataSetChanged();
                }
                if (!filterCatModel.data.filterBrand.isEmpty()) {
                    filterBrandAdapter = new FilterBrandAdapter(getActivity(),filterCatModel.data.filterBrand);
                    bottomSheetLayoutBinding.recyclerViewBrand.setAdapter(filterBrandAdapter);
                    filterBrandAdapter.notifyDataSetChanged();
                } else {
                }
            }
            Utils.ProgressHide(requireContext(), bottomSheetLayoutBinding.matrialProgress, bottomSheetLayoutBinding.nestScrollview);
        }
        if (RequestCode.CODE_FilterDataProductType == taskcode) {
            Log.i("FilterDataType_res", response);
            FilterBrandTypModel filterCatModel = JsonDeserializer.deserializeJson(response, FilterBrandTypModel.class);
            if (filterCatModel.statusCode == 1) {
                if (!filterCatModel.data.filterBrand.isEmpty()) {
                    filterBrandTypeAdapter = new FilterBrandTypeAdapter(getActivity(),filterCatModel.data.filterBrand);
                    bottomSheetLayoutBinding.recyclerViewBrand.setAdapter(filterBrandTypeAdapter);
                    filterBrandTypeAdapter.notifyDataSetChanged();
                }
            }
        }
    }

}
