package com.grocito.grocito.fragments;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.grocito.grocito.activities.SeeAllProduct;
import com.grocito.grocito.adapter.ExpandableListCategoryAdapter;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.model.CategoryModelE;
import com.grocito.grocito.model.SubCategoryModelE;
import com.grocito.grocito.R;
import com.grocito.grocito.databinding.FragmentCategoryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Category extends Fragment implements WebCompleteTask {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public Category() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Category newInstance(String param1, String param2) {
        Category fragment = new Category();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    List<CategoryModelE> headerList = new ArrayList<>();
    HashMap<CategoryModelE, List<SubCategoryModelE>> childList = new HashMap<>();
    ExpandableListCategoryAdapter expandableListAdapter;
    FragmentCategoryBinding binding;

    public static int[] icon = {R.drawable.home, R.drawable.account,
            R.drawable.sell, R.drawable.new_image,
            R.drawable.shop, R.drawable.services,
            R.drawable.notifaction};
    public String[] name = {
            "Fruits & Vegetables",
            "Foodgrains",
            "Oil & Masala",
            "Bakery, Cakes & Dairy",
            "Beverages",
            "Snacks & Branded Foods",
            "Beauty & Hygiene"
    };
    public String[] subname = {
            "Bakery",
            "Cakes",
            "Dairy"
    };
    public boolean[] haschild = {false, true, false, false, false, true, false, false, false};
    public static int[] myAcIcon = {R.drawable.home, R.drawable.account,
            R.drawable.sell};
    DisplayMetrics metrics = new DisplayMetrics();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        binding.expandableList.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
//        prepareMenuData();
        CatList();
        populateExpandableList();
        return binding.getRoot();

    }

    public void CatList() {
//        binding.matrialProgress.setVisibility(View.VISIBLE);
        binding.expandableList.setVisibility(View.GONE);
        HashMap objectNew = new HashMap();
        objectNew.put("pincode", SharedPrefManager.getPinCode(Constrants.PinCode));
        new WebTask(getActivity(), WebUrls.BASE_URL + WebUrls.UserCatList, objectNew, Category.this, RequestCode.CODE_UserCatList, 1);
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    private void prepareMenuData() {

        for (int i = 0; i < icon.length; i++) {
            CategoryModelE categoryModelE = new CategoryModelE();
            categoryModelE.setMenuName(name[i]);
            categoryModelE.setImage(icon[i]);
            categoryModelE.setGroup(true);
            categoryModelE.setHasChildren(haschild[i]);
            categoryModelE.setUrl("");

            if (categoryModelE.isHasChildren()) {
                List<SubCategoryModelE> childModelsList = new ArrayList<>();
                for (int j = 0; j < myAcIcon.length; j++) {
                    SubCategoryModelE subcategoryModelE = new SubCategoryModelE();
                    subcategoryModelE.setMenuName(subname[j]);
                    subcategoryModelE.setImage(myAcIcon[j]);
                    subcategoryModelE.setGroup(true);
                    subcategoryModelE.setHasChildren(false);
                    subcategoryModelE.setUrl("");
                    childModelsList.add(subcategoryModelE);
                }
                childList.put(categoryModelE, childModelsList);
            } else {
                childList.put(categoryModelE, null);
            }
            headerList.add(categoryModelE);
        }

        expandableListAdapter = new ExpandableListCategoryAdapter(getActivity(), headerList, childList);
        binding.expandableList.setAdapter(expandableListAdapter);

    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListCategoryAdapter(getActivity(), headerList, childList);
        binding.expandableList.setAdapter(expandableListAdapter);

        binding.expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (headerList.get(groupPosition).isGroup()) {
                    if (!headerList.get(groupPosition).isHasChildren()) {
                        CategoryModelE model = headerList.get(groupPosition);
//                        Toast.makeText(getActivity(), model.getMenuName(), Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }
        });

        binding.expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    SubCategoryModelE model = childList.get(headerList.get(groupPosition)).get(childPosition);
//                    Toast.makeText(getActivity(), model.getMenuName(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), SeeAllProduct.class)
                            .putExtra("cat_id",headerList.get(groupPosition).getCat_id()+"")
                            .putExtra("subCatId",model.getSubCatId()+"")
                            .putExtra("type","cate")
                    );

                }
                return false;
            }
        });
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_UserCatList == taskcode) {
            Log.i("CatList_res", response);

            try {
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.optInt("status_code") == 1) {
                    JSONObject dataObj = jsonObject.optJSONObject("data");
                    if (dataObj.optJSONArray("catAndSubCatList") != null && dataObj.optJSONArray("catAndSubCatList").length() > 0) {
                        JSONArray catAndSubCatList = dataObj.optJSONArray("catAndSubCatList");
                        for (int i = 0; i < catAndSubCatList.length(); i++) {
                            JSONObject catObj = catAndSubCatList.optJSONObject(i);
                            CategoryModelE categoryModelE = new CategoryModelE();
                            categoryModelE.setMenuName(catObj.optString("cat_name"));
                            categoryModelE.setCat_slu(catObj.optString("cat_slu"));
                            categoryModelE.setCat_id(catObj.optString("cat_id"));
//                            categoryModelE.setImage(icon[i]);
                            categoryModelE.setIcon(catObj.optString("cat_icon"));
                            categoryModelE.setGroup(true);
                            if (catObj.optJSONArray("subCatList") != null && catObj.optJSONArray("subCatList").length() > 0) {
                                categoryModelE.setHasChildren(true);
                            } else {
                                categoryModelE.setHasChildren(false);
                            }
                            categoryModelE.setUrl("");

                            if (categoryModelE.isHasChildren()) {
                                List<SubCategoryModelE> childModelsList = new ArrayList<>();
                                JSONArray subCatList = catObj.optJSONArray("subCatList");
                                for (int j = 0; j < subCatList.length(); j++) {
                                    JSONObject subCatObj = subCatList.getJSONObject(j);
                                    SubCategoryModelE subcategoryModelE = new SubCategoryModelE();
                                    subcategoryModelE.setMenuName(subCatObj.optString("subCatName"));
                                    subcategoryModelE.setSubCatId(subCatObj.optString("subCatId"));
                                    subcategoryModelE.setImage_icon(subCatObj.optString("subCatImage"));
//                                    subcategoryModelE.setImage();
                                    subcategoryModelE.setGroup(true);
                                    subcategoryModelE.setHasChildren(false);
                                    subcategoryModelE.setUrl("");
                                    childModelsList.add(subcategoryModelE);
                                }
                                childList.put(categoryModelE, childModelsList);
                            } else {
                                childList.put(categoryModelE, null);
                            }
                            headerList.add(categoryModelE);
                        }
//                        binding.matrialProgress.setVisibility(View.GONE);
                        binding.expandableList.setVisibility(View.VISIBLE);
                        expandableListAdapter = new ExpandableListCategoryAdapter(getActivity(), headerList, childList);
                        binding.expandableList.setAdapter(expandableListAdapter);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
