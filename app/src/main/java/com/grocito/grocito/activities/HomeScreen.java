package com.grocito.grocito.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.grocito.grocito.R;
import com.grocito.grocito.adapter.ExpandableListAdapter;
import com.grocito.grocito.adapter.PlaceAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityHomeScreenBinding;
import com.grocito.grocito.fragments.Category;
import com.grocito.grocito.fragments.HomeFragment;
import com.grocito.grocito.model.AvailablePlaceModel;
import com.grocito.grocito.model.MenuModel;
import com.grocito.grocito.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class HomeScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener, WebCompleteTask {

    @BindView(R.id.menu_icon)
    ImageView menu_icon;
    @BindView(R.id.bell_icon)
    ImageView bell_icon;

    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    FragmentManager fragmentManager;

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableList;
    private android.app.AlertDialog dialog;
    int countItem = 0;
    View badge;
    TextView countTv;

    TextView invaild;
    AlertDialog dialogPin;
    public static HomeScreen mInstance;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;
    LocationManager locationManager;
    private Location mLastKnowLocation;
    private LocationCallback locationCallback;
    double latitude, longitude;
    private String pinCode;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableList.setIndicatorBounds(expandableList.getRight() - 80, expandableList.getWidth());
        } else {
            expandableList.setIndicatorBoundsRelative(expandableList.getRight() - 80, expandableList.getWidth());
        }
    }

    public static ActivityHomeScreenBinding binding;

    private static GoogleApiClient googleApiClient;
//    private SpotsDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen);
        if (Utils.checkEmptyNull(SharedPrefManager.getPinCode(Constrants.PinCode))) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.containermain, new HomeFragment()).commit();
        }
//        progressDialog = new SpotsDialog(this, R.style.Custom);

        mInstance = this;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Places.initialize(HomeScreen.this, getResources().getString(R.string.google_key));
        placesClient = Places.createClient(this);
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        FacebookSdk.sdkInitialize(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        expandableList.setGroupIndicator(null);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        prepareMenuData();
        populateExpandableList();
        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);

        // setting list adapter
        expandableList.setAdapter(expandableListAdapter);
        binding.appbar.contentLayout.menuIcon.setOnClickListener(v -> {
            DrawerLayout navDrawer = findViewById(R.id.drawer_layout);
            // If the navigation drawer is not open then open it, if its already open then close it.
            if (!navDrawer.isDrawerOpen(Gravity.LEFT)) navDrawer.openDrawer(Gravity.LEFT);
            else navDrawer.closeDrawer(Gravity.RIGHT);
        });
        binding.appbar.contentLayout.bellIcon.setOnClickListener(view -> startActivity(new Intent(HomeScreen.this, Notification.class)));
        binding.headlyaout.bottomRL.setOnClickListener(view -> {
            startActivity(new Intent(HomeScreen.this, AddNewAddress.class));
            onBackPressed();
        });
        binding.headlyaout.topRl.setOnClickListener(v -> startActivity(new Intent(HomeScreen.this, MyAccount.class)));
        binding.appbar.contentLayout.currentLocatinLL.setOnClickListener(view -> startActivity(new Intent(HomeScreen.this, SelectLocation.class)));

        binding.appbar.contentLayout.searchLL.setOnClickListener(view -> startActivity(new Intent(HomeScreen.this, SearchItem.class)));


//        if (SharedPrefManager.getUserPic(Constrants.UserPic) != null) {
//            Glide.with(HomeScreen.this).load(SharedPrefManager.getUserPic(Constrants.UserPic))
//                    .placeholder(R.drawable.logo)
//                    .into(binding.headlyaout.headerUserIcon);
//        }
        binding.headlyaout.headlyaoutEmail.setText(SharedPrefManager.getUserMobile(Constrants.UserMobile));

        if (SharedPrefManager.getUserName(Constrants.UserName) != null) {
//            String[] name = SharedPrefManager.getUserName(Constrants.UserName).split(" ");
//            if (Utils.checkEmptyNull(name[0])) {
                binding.headlyaout.headlyaoutName.setText(Utils.FirstLatterCap(SharedPrefManager.getUserName(Constrants.UserName)));
//            }
        }

        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) binding.appbar.contentLayout.bottomNavigationView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(3);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
        binding.appbar.contentLayout.bottomNavigationView.setOnNavigationItemSelectedListener(this);

        badge = LayoutInflater.from(this)
                .inflate(R.layout.bagelayout, itemView, true);
        countTv = badge.findViewById(R.id.counterTv);
//        countItem = SharedPrefManager.getCartItemCount(Constrants.CartItemCount);


        getCartCount();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LocationPermission();
//        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String url1 = WebUrls.BASE_URL + WebUrls.UserProfileImageURL + SharedPrefManager.getUserPic(Constrants.UserPic);
        Log.i("sadfasfsdafsa", url1);
        binding.headlyaout.headlyaoutEmail.setText(SharedPrefManager.getUserMobile(Constrants.UserMobile));
        if (Utils.checkEmptyNull(SharedPrefManager.getPinCode(Constrants.PinCode))) {
            if (SharedPrefManager.getUserPic(Constrants.UserPic) != null) {
                String url = WebUrls.BASE_URL + WebUrls.UserProfileImageURL + SharedPrefManager.getUserPic(Constrants.UserPic);
                Log.i("sadfasfsdafsa", url);
                Glide.with(HomeScreen.this).load(url)
                        .placeholder(R.drawable.avatar)
                        .into(binding.headlyaout.headerUserIcon);
            }
            String name = SharedPrefManager.getUserFirstname(Constrants.UserName);
            Log.i("sdafasfa", name);
            if (name.length() > 0)
                binding.headlyaout.headlyaoutName.setText(Utils.FirstLatterCap(name));
            getCartCount();
            if (TextUtils.isEmpty(SharedPrefManager.getPinCode(Constrants.PinCode))) {
                binding.appbar.contentLayout.addressTv.setText("Set your delivery address");
            } else {
                binding.appbar.contentLayout.addressTv.setText(String.format("%s(%s)",SharedPrefManager.getCity(Constrants.City),SharedPrefManager.getPinCode(Constrants.PinCode)));
            }
        } else {
            pincodeAlert();
        }
    }

    public void setCountItem(int countItem) {
        if (countItem > 0) {
            countTv.setVisibility(View.VISIBLE);
            Log.i("countItem_no", countItem + "");
            countTv.setText(countItem + "");
        } else {
            countTv.setVisibility(View.GONE);
        }

    }

    private void prepareMenuData() {

        MenuModel homeModel = new MenuModel(getString(R.string.home), R.drawable.home_a, true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(homeModel);
//        if (!homeModel.hasChildren) {
//            childList.put(homeModel, null);
//        }
        MenuModel MyAccount = new MenuModel(getString(R.string.my_account), R.drawable.account, true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(MyAccount);
        MenuModel MyOrder = new MenuModel(getString(R.string.my_order), R.drawable.my_order, true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(MyOrder);
        MenuModel MyWallet = new MenuModel(getString(R.string.my_wallet), R.drawable.wallet, true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(MyWallet);
        MenuModel ReferEarn = new MenuModel(getString(R.string.refer_earm), R.drawable.refer, true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(ReferEarn);
        MenuModel Support = new MenuModel(getString(R.string.support), R.drawable.support, true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(Support);
        MenuModel SellOn = new MenuModel(getString(R.string.about_us), R.drawable.about_us, true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(SellOn);
        MenuModel Suggest = new MenuModel(getString(R.string.privacy_policy), R.drawable.privacy_policy, true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(Suggest);
        MenuModel CancelOderDes = new MenuModel(getString(R.string.cancel_return), R.drawable.return_cancel, true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(CancelOderDes);
//        MenuModel Customer = new MenuModel(getString(R.string.customer_services), R.drawable.services, true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
//        headerList.add(Customer);
        MenuModel TermCondition = new MenuModel(getString(R.string.term_and_condition), R.drawable.terms_and_conditions, true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(TermCondition);
        MenuModel Faq = new MenuModel(getString(R.string.faqs), R.drawable.faq, true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(Faq);
        MenuModel Logout = new MenuModel(getString(R.string.logout), R.drawable.logout, true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(Logout);

//        List<MenuModel> childModelsList = new ArrayList<>();
//
//        MenuModel childModel = new MenuModel(getString(R.string.my_order), R.drawable.my_order, false, false, "https://www.journaldev.com/7153/core-java-tutorial");
//        childModelsList.add(childModel);
//
//        childModel = new MenuModel(getString(R.string.my_wallet), R.drawable.wallet, false, false, "https://www.journaldev.com/19187/java-fileinputstream");
//        childModelsList.add(childModel);
//
//        childModel = new MenuModel(getString(R.string.my_profile), R.drawable.user, false, false, "https://www.journaldev.com/19187/java-fileinputstream");
//        childModelsList.add(childModel);
////
//        if (homeModel.hasChildren) {
//            Log.d("API123", "here");
//            childList.put(homeModel, childModelsList);
//        }

    }
    PlaceAdapter adapter;
    RecyclerView recyclerViewPlace;
    ArrayList<AvailablePlaceModel.Datum> arrayList = new ArrayList<>();
    public void pincodeAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.pincode_check, null);
        builder.setView(view);

        recyclerViewPlace = (RecyclerView) view.findViewById(R.id.recyclerViewPlace);
        invaild = (TextView) view.findViewById(R.id.invaildTv);
        TextView checkBtn = view.findViewById(R.id.checkAvlBtn);
        EditText editText = view.findViewById(R.id.checkPinEt);
        dialogPin = builder.create();
        checkBtn.setOnClickListener(v -> {
            if (!Utils.checkEmptyNull(editText.getText().toString())) {
                invaild.setVisibility(View.VISIBLE);
            } else {
                checkPinMethod(editText.getText().toString());
            }
        });
        recyclerViewPlace.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        availableCityList();
        dialogPin.setCancelable(false);
        dialogPin.setCanceledOnTouchOutside(false);
        dialogPin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPin.show();
    }

    public void availableCityList() {
//        progressDialog.show();
//        Utils.ProgressDialog(this);
        HashMap objectNew = new HashMap();
        Log.i("pincode_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.availableCityList, objectNew,
                HomeScreen.this, RequestCode.CODE_availableCityList, 5);
    }

    public void checkPinMethod(String pin) {
//        progressDialog.show();
        Utils.ProgressDialog(this);
        HashMap objectNew = new HashMap();
        objectNew.put("pincode", pin);
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        Log.i("pincode_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.UserCheckPinAvailability, objectNew,
                HomeScreen.this, RequestCode.CODE_UserCheckPinAvailability, 5);
    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableList.setAdapter(expandableListAdapter);

        expandableList.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            Fragment currentFragment = (Fragment) fragmentManager.findFragmentById(R.id.containermain);
            if (headerList.get(groupPosition).isGroup) {
                if (!headerList.get(groupPosition).hasChildren) {
                    MenuModel model = headerList.get(groupPosition);
                    if (model.menuName.equals(getString(R.string.home))) {
//                             Toast.makeText(HomeScreen.this, model.menuName, Toast.LENGTH_SHORT).show();

                        if (!(currentFragment instanceof HomeFragment)) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.containermain, new HomeFragment()).commit();
                        }
                    } else if (model.menuName.equals(getString(R.string.about_us))) {
//                            Toast.makeText(HomeScreen.this, model.menuName, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeScreen.this, About.class));
                    } else if (model.menuName.equals(getString(R.string.suggest_a_new))) {
                        Toast.makeText(HomeScreen.this, model.menuName, Toast.LENGTH_SHORT).show();
//                            if (marshMallowPermission.checkPermissionForCamera()) {
//                                startActivity(new Intent(Home.this, ScanActivity.class).putExtra("activity", "home"));

                    } else if (model.menuName.equals(getString(R.string.my_account))) {
//                            Toast.makeText(HomeScreen.this, model.menuName, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeScreen.this, MyAccount.class));
                    } else if (model.menuName.equals(getString(R.string.shop_by_cat))) {

                        if (!(currentFragment instanceof Category)) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.containermain, new Category()).commit();
                        }
                    } else if (model.menuName.equals(getString(R.string.privacy_policy))) {
//                            Toast.makeText(HomeScreen.this, model.menuName, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeScreen.this, PrivacyPolicy.class));
                    } else if (model.menuName.equals(getString(R.string.term_and_condition))) {
                        startActivity(new Intent(HomeScreen.this, TermAndCondition.class));
                    } else if (model.menuName.equals(getString(R.string.support))) {
                        startActivity(new Intent(HomeScreen.this, SupportActivity.class));
                    } else if (model.menuName.equals(getString(R.string.cancel_return))) {
                        startActivity(new Intent(HomeScreen.this, CancelAndReturn.class));
                    } else if (model.menuName.equals(getResources().getString(R.string.my_order))) {
                        startActivity(new Intent(HomeScreen.this, MyOrderList.class));
                    } else if (model.menuName.equals(getResources().getString(R.string.my_wallet))) {
                        startActivity(new Intent(HomeScreen.this, MyWallet.class));
                    } else if (model.menuName.equals(getResources().getString(R.string.refer_earm))) {
                        startActivity(new Intent(HomeScreen.this, ReferAndEarn.class));
                    } else if (model.menuName.equals(getString(R.string.logout))) {
                        dialog = Utils.retryAlertDialog(HomeScreen.this, getResources().getString(R.string.app_name), getResources().getString(R.string.Are_you_sure_to_logout), getResources().getString(R.string.Cancel), getResources().getString(R.string.Yes), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SignOut();
                            }
                        });
//
                    } else if (model.menuName.equals(getString(R.string.faqs))) {
//                        Toast.makeText(HomeScreen.this, model.menuName, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeScreen.this, FAQ.class));
                    }
                    onBackPressed();
                }
            }

            return false;
        });

        expandableList.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {

            if (childList.get(headerList.get(groupPosition)) != null) {
                MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                if (model.menuName.equals(getString(R.string.my_order))) {
                    Toast.makeText(HomeScreen.this, model.menuName, Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(Home.this, AccountSetting.class));
                } else if (model.menuName.equals(getString(R.string.my_wallet))) {
                    Toast.makeText(HomeScreen.this, model.menuName, Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(Home.this, NotificationSetting.class));
                } else if (model.menuName.equals(getString(R.string.my_profile))) {
//                        Toast.makeText(HomeScreen.this, model.menuName, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeScreen.this, MyAccount.class));
                }
            }
            return false;
        });
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);

        } else {
//            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            Task<Location> location = fusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            if (currentLocation != null) {
                                latitude = currentLocation.getLatitude();
                                longitude = currentLocation.getLongitude();

                                Geocoder geocoder = new Geocoder(HomeScreen.this, Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                    Address addressobj = addresses.get(0);
//                                                                addressobj.getAddressLine(0);
                                    pinCode = addressobj.getPostalCode();
                                    checkPinMethod(pinCode);
//                                                               search_tv.setText(addressobj.getAddressLine(0));
//                                                               All_Book_List_Method();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Utils.Toast(HomeScreen.this, "Unable to find current location . Try again later");
                        }
                    }
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                getLocation();
                Log.i("onRequestPermissions", "onRequestPermissionsResult");
                break;
        }
    }

    public void LocationPermission() {
        //        LocationServiceOn_Off();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager == null) {
            showSettingsAlert();
        } else {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                Log.i("About GPS", "GPS is Enabled in your device");
//                getLocation();
                // Toast.makeText(ctx,"enable",Toast.LENGTH_SHORT).show();
            } else {
                //showAlert
                showSettingsAlert();
//            context.startActivity(new Intent(context, DialogActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(R.string.gps_setting);
        // Setting Dialog Message
        alertDialog.setMessage(R.string.gps_setting_menu);

        // On pressing Settings button
        alertDialog.setPositiveButton(R.string.settings,
                (dialog, which) -> {
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                });

        // on pressing cancel button
        alertDialog.setNegativeButton(getString(R.string.cancel),
                (dialog, which) -> dialog.cancel());

        // Showing Alert Message
        alertDialog.show();
    }

    public static HomeScreen getInstance() {
        return mInstance;
    }

    public void Notif(int count) {
        if (count > 0) {
            binding.appbar.contentLayout.bellItemNo.setVisibility(View.VISIBLE);
            binding.appbar.contentLayout.bellItemNo.setText(count + "");
//            SharedPrefManager.setCartItemCount();
        } else {
            binding.appbar.contentLayout.bellItemNo.setVisibility(View.GONE);
        }
    }

    public void SignOut() {

        if (SharedPrefManager.isGoogleLogin(Constrants.IsGoogleLogin)) {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    SharedPrefManager.setGoogleLogin(Constrants.IsGoogleLogin, false);
                    SharedPrefManager.setFbLogin(Constrants.IsFBLogin, false);
                    SharedPrefManager.setLogin(Constrants.IsLogin, false);
                    Utils.logout(HomeScreen.this);
                    dialog.dismiss();
                }
            });
        } else if (SharedPrefManager.isFBLogin(Constrants.IsFBLogin)) {
            LoginManager.getInstance().logOut();
            SharedPrefManager.setGoogleLogin(Constrants.IsGoogleLogin, false);
            SharedPrefManager.setFbLogin(Constrants.IsFBLogin, false);
            SharedPrefManager.setLogin(Constrants.IsLogin, false);
            Utils.logout(HomeScreen.this);
            dialog.dismiss();
        } else if (SharedPrefManager.isLogin(Constrants.IsLogin)) {
            SharedPrefManager.setGoogleLogin(Constrants.IsGoogleLogin, false);
            SharedPrefManager.setFbLogin(Constrants.IsFBLogin, false);
            SharedPrefManager.setLogin(Constrants.IsLogin, false);
            Utils.logout(HomeScreen.this);
            dialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            backCountToExit();
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    private void backCountToExit() {

        FragmentManager fm = this.getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {

            if (doubleBackToExitPressedOnce) {
                finish();
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.Please_BACK_again_to_exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // uncheck the other items.
        Fragment currentFragment = (Fragment) fragmentManager.findFragmentById(R.id.containermain);
        int mMenuId = item.getItemId();
        switch (item.getItemId()) {
            case R.id.home_btm: {
                if (!(currentFragment instanceof HomeFragment)) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.containermain, new HomeFragment()).commit();
                }
            }
            break;
            case R.id.category_btm: {
                if (!(currentFragment instanceof Category)) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.containermain, new Category()).commit();
                }
            }
            break;
            case R.id.offer_btm: {
                Toast.makeText(HomeScreen.this, "Coming soon", Toast.LENGTH_LONG).show();
            }
            break;
            case R.id.cart_btm: {
                startActivity(new Intent(HomeScreen.this, Cart.class));
            }
            break;
        }
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void getCartCount() {
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));

        Log.i("getCartCount_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.CartCount, objectNew,
                HomeScreen.this, RequestCode.CODE_CartCount, 5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_CartCount == taskcode) {
            Log.i("getCartCount_res", response + "");
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status") == 1) {
                    countItem = jsonObject.optInt("cart_count");
                    SharedPrefManager.setCartItemCount(Constrants.CartItemCount, countItem);
                    SharedPrefManager.setreferCode(Constrants.ReferCode, jsonObject.optString("ref_id"));
                    SharedPrefManager.setreferString(Constrants.ReferString,jsonObject.optJSONObject("refferSetting").optString("referral_description"));
                    setCountItem(countItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (RequestCode.CODE_UserCheckPinAvailability == taskcode) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status_code") == 1) {
                    invaild.setVisibility(View.GONE);
                    JSONObject data = jsonObject.optJSONObject("data");
                    SharedPrefManager.setPinCode(Constrants.PinCode, data.optString("pincode"));
                    SharedPrefManager.setCity(Constrants.City, data.optString("city_name"));
                    SharedPrefManager.setState(Constrants.State, data.optString("state_name"));
                    dialogPin.dismiss();
                    if (TextUtils.isEmpty(SharedPrefManager.getPinCode(Constrants.PinCode))) {
                        binding.appbar.contentLayout.addressTv.setText("Set your delivery address");
                    } else {
                        binding.appbar.contentLayout.addressTv.setText(String.format("%s(%s)",SharedPrefManager.getCity(Constrants.City),SharedPrefManager.getPinCode(Constrants.PinCode)));
                    }
                    fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.containermain, new HomeFragment()).commit();
                } else {
                    invaild.setVisibility(View.VISIBLE);
                    invaild.setText(jsonObject.optString("message"));
                }
                Utils.ProgressDialogHide(this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (RequestCode.CODE_availableCityList == taskcode){
            Log.i("availableCityList_res", response + "");
            AvailablePlaceModel model = JsonDeserializer.deserializeJson(response,AvailablePlaceModel.class);
            if (model.statusCode == 1){
//                if (!model.data.size()>0){
//                    arrayList.addAll(model.data);
                    adapter = new PlaceAdapter(this, model.data);
                    recyclerViewPlace.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
//                }
                new Handler().postDelayed(() -> getLocation(),3000);

            }
        }
    }
}
