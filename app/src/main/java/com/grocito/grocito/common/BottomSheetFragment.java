package com.grocito.grocito.common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.google.android.libraries.places.widget.internal.ui.zzo;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import com.grocito.grocito.activities.Payment;
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
import com.grocito.grocito.databinding.BottomSheetLayoutBinding;
import com.grocito.grocito.model.FilterBrandTypModel;
import com.grocito.grocito.model.FilterCatModel;
import com.grocito.grocito.model.SeeAllProductsModel;
import com.grocito.grocito.utils.Utils;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;

public class BottomSheetFragment extends BottomSheetDialogFragment implements WebCompleteTask {


    private BottomSheetLayoutBinding bottomSheetLayoutBinding;
    public static String cat_id = "",subcat_id = "",min_price="",max_price="",min_ofr="",max_ofr="";
    private FilterCatAdapter filterCatAdapter;
    private FilterBrandAdapter filterBrandAdapter;
    private FilterBrandTypeAdapter filterBrandTypeAdapter;

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

//        dialog = dialog.dialogSettings();

        bottomSheetLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(dialog.getContext())
                ,R.layout.bottom_sheet_layout,null,false);
//        bottomSheetLayoutBinding.setMain(this);
        dialog.setContentView(bottomSheetLayoutBinding .getRoot());

//        //Set the custom view
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_layout, null);
//        dialog.setContentView(view);
// set listener
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
                max_ofr = "5";
                bottomSheetLayoutBinding.checkbox1.setChecked(true);
                bottomSheetLayoutBinding.checkbox2.setChecked(false);
                bottomSheetLayoutBinding.checkbox3.setChecked(false);
                bottomSheetLayoutBinding.checkbox4.setChecked(false);
            }
        });
        bottomSheetLayoutBinding.checkbox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                min_ofr = "5";
                max_ofr = "10";
                bottomSheetLayoutBinding.checkbox1.setChecked(false);
                bottomSheetLayoutBinding.checkbox2.setChecked(true);
                bottomSheetLayoutBinding.checkbox3.setChecked(false);
                bottomSheetLayoutBinding.checkbox4.setChecked(false);
            }
        });
        bottomSheetLayoutBinding.checkbox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                min_ofr = "10";
                max_ofr = "15";
                bottomSheetLayoutBinding.checkbox1.setChecked(false);
                bottomSheetLayoutBinding.checkbox2.setChecked(false);
                bottomSheetLayoutBinding.checkbox3.setChecked(true);
                bottomSheetLayoutBinding.checkbox4.setChecked(false);
            }
        });
        bottomSheetLayoutBinding.checkbox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                min_ofr = "15";
                max_ofr = "25";
                bottomSheetLayoutBinding.checkbox1.setChecked(false);
                bottomSheetLayoutBinding.checkbox2.setChecked(false);
                bottomSheetLayoutBinding.checkbox3.setChecked(false);
                bottomSheetLayoutBinding.checkbox4.setChecked(true);
            }
        });



        bottomSheetLayoutBinding.recyclerViewSubCat.setLayoutManager(new LinearLayoutManager(getActivity()));
        bottomSheetLayoutBinding.recyclerViewBrand.setLayoutManager(new LinearLayoutManager(getActivity()));

        ((GradientDrawable)bottomSheetLayoutBinding.getRoot().getBackground()).setColor(getResources().getColor(R.color.white));


        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) bottomSheetLayoutBinding.getRoot().getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View)bottomSheetLayoutBinding.getRoot().getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        bottomSheetLayoutBinding.crossIv.setOnClickListener(v -> dismiss());
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

//                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }

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

    }

    public void seeAllOffer(String type) {
        HashMap objectNew = new HashMap();
        objectNew.put("product_type", type);
        objectNew.put("pincode", SharedPrefManager.getPinCode(Constrants.PinCode));
        Log.i("seeAllOffer_obj", objectNew + "");
        new WebTask(getActivity(), WebUrls.BASE_URL + WebUrls.FilterDataProductType, objectNew,
                BottomSheetFragment.this, RequestCode.CODE_FilterDataProductType, 5);
    }
    public void productList() {
//        Utils.ProgressShow(this, binding.matrialProgress, binding.productRecyView);
        HashMap objectNew = new HashMap();
        objectNew.put("cat_id", cat_id);
        objectNew.put("sub_cat_id", subcat_id);

        Log.i("Filter_obj", objectNew + "");
        new WebTask(getActivity(), WebUrls.BASE_URL + WebUrls.FilterData, objectNew,
                BottomSheetFragment.this, RequestCode.CODE_FilterData, 5);
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
