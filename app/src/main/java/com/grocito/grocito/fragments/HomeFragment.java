package com.grocito.grocito.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.grocito.grocito.R;
import com.grocito.grocito.activities.HomeScreen;
import com.grocito.grocito.activities.ProductDetail;
import com.grocito.grocito.activities.SeeAllProduct;
import com.grocito.grocito.adapter.CateHomeAdapter;
import com.grocito.grocito.adapter.HomeAdapter;
import com.grocito.grocito.adapter.ImageViewPagerAdapter;
import com.grocito.grocito.adapter.OfferHomeAdapter;
import com.grocito.grocito.adapter.OtherAdapter;
import com.grocito.grocito.adapter.SliderPagerAdapter;
import com.grocito.grocito.adapter.SunProductAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.MyApplication;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.FragmentHomeBinding;
import com.grocito.grocito.model.HomeGsonModel;
import com.grocito.grocito.model.ImageModel;
import com.grocito.grocito.utils.Utils;
import com.grocito.grocito.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements WebCompleteTask {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //------------Bottom Image ------------------------
    OtherAdapter otherAdapter;
    FragmentHomeBinding binding;
    ArrayList<HomeGsonModel.MainCategory> specialArrayList = new ArrayList<>();

    //--------------------Slider-----------------------
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<HomeGsonModel.SliderList> sliderPArrayList = new ArrayList<>();
    ImageViewPagerAdapter imageViewPagerAdapter;
    ArrayList<ImageModel> imageModelArrayList;


    OfferHomeAdapter offerHomeAdapter;
    List<HomeGsonModel.FirstOfferBanner> firstBannerArrayList = new ArrayList<>();
    //--------------Category---------------------------
    CateHomeAdapter cateHomeAdapter;
    List<HomeGsonModel.CatDatum> cateHomeModelArrayList = new ArrayList<>();
    private int size;
    private boolean click = false;

    //---------------Category wtih product-------------
    List<HomeGsonModel.CatWithProduct> homeModelArrayList = new ArrayList<>();
    HomeAdapter homeAdapter;


    //---------------Sunday offer-------------
    List<HomeGsonModel.SundayProduct> sundayProductArrayList = new ArrayList<>();
    SunProductAdapter sunProductAdapter;

    //---------------Exclusive offer-------------
    List<HomeGsonModel.SundayProduct> isGrocitoExclusiveArrayList = new ArrayList<>();
    //---------------New offer-------------
    List<HomeGsonModel.SundayProduct> newArrivalArrayList = new ArrayList<>();

    private HomeViewModel homeViewModel;
    public static int noticount = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        ProductDetail.product_detail = false;

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.i("newToken", newToken);
                SharedPrefManager.setDeviceToken(Constrants.Token,newToken);
            }
        });

        //----------------Slider Image----------------------
        imageModelArrayList = new ArrayList<>();
        imageViewPagerAdapter = new ImageViewPagerAdapter(getActivity(), imageModelArrayList);
        binding.viewPager.setAdapter(imageViewPagerAdapter);
        binding.dotsIndicator.setViewPager(binding.viewPager);

        //-------------------------------------isSpecial-----------------

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
//        manager.setSpanSizeLookup(
//                new GridLayoutManager.SpanSizeLookup() {
//                    @Override
//                    public int getSpanSize(int position) {
//                        return_cancel (position % 3 == 0 ? 2 : 1);
//                    }
//                }
//        );
        binding.isSpecialRecyclerView.setLayoutManager(manager);
        MyApplication.RecyclerView(binding.isSpecialRecyclerView);

        //-------------------------------------Home sun-----------------
        binding.sunRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        MyApplication.RecyclerView(binding.sunRecyclerView);


        //-------------------------------------Home isExcl-----------------
        binding.exlRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        MyApplication.RecyclerView(binding.exlRecyclerView);

        binding.firstBannerRv.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
        MyApplication.RecyclerView(binding.firstBannerRv);

        //-------------------------------------Home new-----------------
        binding.newRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        MyApplication.RecyclerView(binding.newRecyclerView);

        //-------------------------------------Home Category -----------------
        binding.shopbyCatRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        MyApplication.RecyclerView(binding.shopbyCatRecyclerView);

        //-------------------------------------Home Category with Product-----------------
        binding.TopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        MyApplication.RecyclerView(binding.TopRecyclerView);
        homeAdapter = new HomeAdapter(getActivity(), homeModelArrayList);
        binding.TopRecyclerView.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
        HomeData();

        binding.seeAll.setVisibility(View.GONE);
        binding.seeAll.setOnClickListener(v -> {
            if (cateHomeModelArrayList.size() > 4) {
                if (size == 4) {
                    binding.seeAll.setText("View Less");
                    size = cateHomeModelArrayList.size();
                    CateSize();
                } else {
                    binding.seeAll.setText("View All");
                    size = 4;
                    CateSize();
                }
            } else {
                size = cateHomeModelArrayList.size();
            }
        });

        binding.seeAllSun.setOnClickListener(v -> startActivity(new Intent(getActivity(), SeeAllProduct.class)
                .putExtra("type", "is_sunday_bazar")
        ));
        binding.seeAllExl.setOnClickListener(v -> startActivity(new Intent(getActivity(), SeeAllProduct.class)
                .putExtra("type", "is_grocito_exclusive")
        ));
        binding.seeAllNew.setOnClickListener(v -> startActivity(new Intent(getActivity(), SeeAllProduct.class)
                .putExtra("type", "new")
        ));
        return binding.getRoot();
    }

    Runnable runnable;
    Handler handler;

    public void autoScroll() {
        final int speedScroll = 3000;
        handler = new Handler();
        runnable = new Runnable() {
            int count = -1;

            @Override
            public void run() {
                if (count < binding.firstBannerRv.getAdapter().getItemCount()) {
                    binding.firstBannerRv.smoothScrollToPosition(++count);
                    handler.postDelayed(this, speedScroll);
                }
                if (count == binding.firstBannerRv.getAdapter().getItemCount()) {
                    count = -1;
//                    binding.firstBannerRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                    binding.firstBannerRv.smoothScrollToPosition(--count);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };

        handler.post(runnable);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (sliderPArrayList.size() > 0) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);
        }
        if (binding.firstBannerRv.getAdapter() != null && binding.firstBannerRv.getAdapter().getItemCount() > 0)
//            autoScroll();
            HomeScreen.getInstance().Notif(noticount);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (timer != null)
            timer.cancel();
        if (runnable != null)
            handler.removeCallbacks(runnable);
    }

    public void CateSize() {
        cateHomeAdapter = new CateHomeAdapter(getActivity(), cateHomeModelArrayList, size);
        binding.shopbyCatRecyclerView.setAdapter(cateHomeAdapter);
        cateHomeAdapter.notifyDataSetChanged();
    }

    public void HomeData() {
        binding.nestScrollview.setVisibility(View.GONE);
//        Utils.ProgressShow(getActivity(), binding.materialProgress, binding.nestScrollview);
        HashMap objectNew = new HashMap();
        objectNew.put("pincode", SharedPrefManager.getPinCode(Constrants.PinCode));
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        new WebTask(getActivity(), WebUrls.BASE_URL + WebUrls.HomeApi, objectNew,
                HomeFragment.this, RequestCode.CODE_HomeData, 1);

    }

    Timer timer;

    private class SliderTimer extends TimerTask {
        int count = -1;
        @Override
        public void run() {
            Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                if (binding.viewPager.getCurrentItem() < sliderPArrayList.size() - 1) {
                    binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
                } else {
                    binding.viewPager.setCurrentItem(0);
                }

                if (count < binding.firstBannerRv.getAdapter().getItemCount()) {
                    binding.firstBannerRv.smoothScrollToPosition(++count);
//                    handler.postDelayed(this, speedScroll);
                }
                if (count == binding.firstBannerRv.getAdapter().getItemCount()) {
                    count = -1;
//                    binding.firstBannerRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                    binding.firstBannerRv.smoothScrollToPosition(--count);
//                    handler.postDelayed(this, speedScroll);
                }
            });
        }
    }


    @Override
    public void onComplete(String response, int taskcode) {

        if (RequestCode.CODE_HomeData == taskcode) {

            HomeGsonModel homeGsonModel = JsonDeserializer.deserializeJson(response, HomeGsonModel.class);
//                JSONObject jsonObject = new JSONObject(response);

            Log.i("Home_Data_Res", response);
            if (homeGsonModel.statusCode == 1) {

                noticount = homeGsonModel.data.userNotifyCount;
                HomeScreen.getInstance().Notif(noticount);
//                    notiCount();
                //-------------------------------------Category home-----------------
                cateHomeModelArrayList.clear();
                if (!homeGsonModel.data.catData.isEmpty()) {
                    cateHomeModelArrayList.addAll(homeGsonModel.data.catData);
                    if (cateHomeModelArrayList.size() > 4) {
                        size = 4;
                        binding.seeAll.setVisibility(View.VISIBLE);
                    } else {
                        size = cateHomeModelArrayList.size();
                        binding.seeAll.setVisibility(View.GONE);
                    }
                    CateSize();
                } else {
                    binding.seeAll.setVisibility(View.GONE);
                }

                firstBannerArrayList.clear();
                if (!homeGsonModel.data.firstOfferBanner.isEmpty()) {
                    firstBannerArrayList.addAll(homeGsonModel.data.firstOfferBanner);
                    offerHomeAdapter = new OfferHomeAdapter(getActivity(), firstBannerArrayList, firstBannerArrayList.size());
                    binding.firstBannerRv.setAdapter(offerHomeAdapter);
                }
                //-------------------------------------Home Category with Product-----------------

                if (!homeGsonModel.data.catWithProduct.isEmpty()) {
                    homeModelArrayList.addAll(homeGsonModel.data.catWithProduct);
                    homeAdapter.notifyDataSetChanged();

                }

                //----------------Sun offer----------------------------
                sundayProductArrayList.clear();
                if (!homeGsonModel.data.sundayProduct.isEmpty()) {
                    binding.sundayLL.setVisibility(View.VISIBLE);
                    sundayProductArrayList.addAll(homeGsonModel.data.sundayProduct);
                    sunProductAdapter = new SunProductAdapter(getActivity(), sundayProductArrayList);
                    binding.sunRecyclerView.setAdapter(sunProductAdapter);
                } else {
                    binding.sundayLL.setVisibility(View.GONE);
                }

                //---------------- isExcl offer----------------------------
                isGrocitoExclusiveArrayList.clear();
                if (!homeGsonModel.data.isGrocitoExclusive.isEmpty()) {
                    binding.grocitoExLL.setVisibility(View.VISIBLE);
                    isGrocitoExclusiveArrayList.addAll(homeGsonModel.data.isGrocitoExclusive);
                    sunProductAdapter = new SunProductAdapter(getActivity(), isGrocitoExclusiveArrayList);
                    binding.exlRecyclerView.setAdapter(sunProductAdapter);
                } else {
                    binding.grocitoExLL.setVisibility(View.GONE);
                }

                //---------------- new offer----------------------------
                newArrivalArrayList.clear();
                if (!homeGsonModel.data.newArrival.isEmpty()) {
                    binding.NewArrivalLL.setVisibility(View.VISIBLE);
                    newArrivalArrayList.addAll(homeGsonModel.data.newArrival);
                    sunProductAdapter = new SunProductAdapter(getActivity(), newArrivalArrayList);
                    binding.newRecyclerView.setAdapter(sunProductAdapter);
                } else {
                    binding.NewArrivalLL.setVisibility(View.GONE);
                }


                //-------------------------------------Home Slider-----------------
                if (!homeGsonModel.data.sliderList.isEmpty()) {
                    sliderPArrayList.addAll(homeGsonModel.data.sliderList);
                    sliderPagerAdapter = new SliderPagerAdapter(getActivity(), sliderPArrayList);
                    binding.viewPager.setAdapter(sliderPagerAdapter);
                    binding.dotsIndicator.setViewPager(binding.viewPager);

                    timer = new Timer();
                    timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);
                }
                //-------------------------------------Banners-----------------

                if (homeGsonModel.data.middleBanner.images != null)
                    Utils.setImage(getActivity(), binding.middleImage, WebUrls.BASE_URL + WebUrls.Banner_Image_URL +
                            homeGsonModel.data.middleBanner.images);

                binding.middleImage.setOnClickListener(v -> {
                    if (homeGsonModel.data.middleBanner.linkType.equals("category")) {
                        if (homeGsonModel.data.middleBanner.mainCategoryp != null) {
                            startActivity(new Intent(getActivity(), SeeAllProduct.class)
                                    .putExtra("cat_id", homeGsonModel.data.middleBanner.mainCategoryp.id + "")
                                    .putExtra("subCatId", "")
                            );
                        }
                    } else if (homeGsonModel.data.middleBanner.linkType.equals("product")) {
                        startActivity(new Intent(getActivity(), ProductDetail.class)
                                .putExtra("product_slug", homeGsonModel.data.middleBanner.product.slug + ""));
                    }
                });

                Utils.setImage(getActivity(), binding.bottomBanner, WebUrls.BASE_URL + WebUrls.Banner_Image_URL +
                        homeGsonModel.data.bottomBanner.images);

                binding.bottomBanner.setOnClickListener(v -> {
                    if (homeGsonModel.data.bottomBanner.linkType.equals("category")) {
                        if (homeGsonModel.data.bottomBanner.mainCategoryp != null) {
                            startActivity(new Intent(getActivity(), SeeAllProduct.class)
                                    .putExtra("cat_id", homeGsonModel.data.bottomBanner.mainCategoryp.id + "")
                                    .putExtra("subCatId", "")
                            );
                        }
                    } else if (homeGsonModel.data.bottomBanner.linkType.equals("product")) {
                        startActivity(new Intent(getActivity(), ProductDetail.class)
                                .putExtra("product_slug", homeGsonModel.data.bottomBanner.product.slug + ""));
                    }
                });

                //-------------------------------------isSpecial Image-----------------

                if (homeGsonModel.data.isSpecial.name != null) {
                    binding.isSpecialHeadTv.setText(homeGsonModel.data.isSpecial.name);
                    Utils.setImage(getActivity(), binding.isSpecialImage, WebUrls.BASE_URL + WebUrls.Catgory_Image_URL +
                            homeGsonModel.data.isSpecial.image);
                    binding.isSpecialImage.setOnClickListener(v -> {
                        if (homeGsonModel.data.isSpecial.id != null) {
                            startActivity(new Intent(getActivity(), SeeAllProduct.class)
                                    .putExtra("cat_id", homeGsonModel.data.isSpecial.id + "")
                                    .putExtra("subCatId", "")
                                    .putExtra("name", homeGsonModel.data.isSpecial.name)
                            );
                        }
                    });
                    if (homeGsonModel.data.isSpecial.mainCategory.size() > 4) {
                        specialArrayList.addAll(homeGsonModel.data.isSpecial.mainCategory);
                        otherAdapter = new OtherAdapter(getActivity(), specialArrayList, 4);
                    } else {
                        specialArrayList.addAll(homeGsonModel.data.isSpecial.mainCategory);
                        otherAdapter = new OtherAdapter(getActivity(), specialArrayList, specialArrayList.size());
                    }

                    binding.isSpecialRecyclerView.setAdapter(otherAdapter);
                    otherAdapter.notifyDataSetChanged();
                }
                binding.nestScrollview.setVisibility(View.VISIBLE);
//                autoScroll();
//                Utils.ProgressHide(getActivity(), binding.materialProgress, binding.nestScrollview);

            }
        }

    }

//    public static void notiCount(){
//        HomeScreen.getInstance().Notif();
//    }

}
