package com.grocito.grocito.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.grocito.grocito.R;
import com.grocito.grocito.adapter.PlaceAutocompleteAdapter;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityAddNewAddressBinding;
import com.grocito.grocito.model.PlaceInfo;
import com.grocito.grocito.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class AddNewAddress extends AppCompatActivity implements WebCompleteTask ,GoogleApiClient.OnConnectionFailedListener {

    ActivityAddNewAddressBinding addNewAddressBinding;
    List<Object> object = new ArrayList<Object>();

    String type = "";
    String id, name, mobile, address, house, street, city, landmark, state, pincode, lattitude, longitude, is_default;

    private static String TAG = AddNewAddress.class.getSimpleName();

    private LatLng center;
    private Geocoder geocoder;
    private List<Address> addresses;
    double lati,longi;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addNewAddressBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_address);

        addNewAddressBinding.headlyaout.searchIcon.setVisibility(View.GONE);
        addNewAddressBinding.headlyaout.cartRL.setVisibility(View.GONE);

        addNewAddressBinding.headlyaout.backBtn.setOnClickListener(view -> finish());
        addNewAddressBinding.officeBtn.setOnClickListener(view -> {
            type = "office";
            addNewAddressBinding.officeBtn.setBackground(getResources().getDrawable(R.drawable.assentcolorborderlightraduis));
            addNewAddressBinding.homeBtn.setBackground(getResources().getDrawable(R.drawable.greyborderlightradius));
            addNewAddressBinding.otherBtn.setBackground(getResources().getDrawable(R.drawable.greyborderlightradius));
        });
        addNewAddressBinding.homeBtn.setOnClickListener(view -> {
            type = "home";
            addNewAddressBinding.homeBtn.setBackground(getResources().getDrawable(R.drawable.assentcolorborderlightraduis));
            addNewAddressBinding.officeBtn.setBackground(getResources().getDrawable(R.drawable.greyborderlightradius));
            addNewAddressBinding.otherBtn.setBackground(getResources().getDrawable(R.drawable.greyborderlightradius));
        });
        addNewAddressBinding.otherBtn.setOnClickListener(view -> {
            type = "other";
            addNewAddressBinding.otherBtn.setBackground(getResources().getDrawable(R.drawable.assentcolorborderlightraduis));
            addNewAddressBinding.homeBtn.setBackground(getResources().getDrawable(R.drawable.greyborderlightradius));
            addNewAddressBinding.officeBtn.setBackground(getResources().getDrawable(R.drawable.greyborderlightradius));
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();

        addNewAddressBinding.myLocation.setOnClickListener(v -> getLocation());

        if (getIntent().getExtras() != null) {
            Log.i("sdafsfsafsf", object.toString());
            id = getIntent().getExtras().getString("id", "");
            type = getIntent().getExtras().getString("type", "");
            name = getIntent().getExtras().getString("name", "");
            // = getIntent().getExtras().getString("user_id","");
            mobile = getIntent().getExtras().getString("mobile", "");
            address = getIntent().getExtras().getString("address", "");
            house = getIntent().getExtras().getString("house", "");
            street = getIntent().getExtras().getString("street", "");
            city = getIntent().getExtras().getString("city", "");
            landmark = getIntent().getExtras().getString("landmark", "");
            state = getIntent().getExtras().getString("state", "");
            pincode = getIntent().getExtras().getString("pincode", "");
            lattitude = getIntent().getExtras().getString("lattitude", "");
            longitude = getIntent().getExtras().getString("longitude", "");
            is_default = getIntent().getExtras().getString("is_default", "");

            addNewAddressBinding.stateEt.setText(SharedPrefManager.getState(Constrants.State));
            addNewAddressBinding.cityEt.setText(SharedPrefManager.getCity(Constrants.City));
            addNewAddressBinding.pincodeEt.setText(pincode);
            addNewAddressBinding.nameEt.setText(name);
            addNewAddressBinding.contactEt.setText(mobile);
            addNewAddressBinding.streetdetailEt.setText(street);
            addNewAddressBinding.houseNoEt.setText(house);
            addNewAddressBinding.areaLocalityEt.setText(address);

            if (type.equalsIgnoreCase("home"))
                addNewAddressBinding.homeBtn.setBackground(getResources().getDrawable(R.drawable.assentcolorborderlightraduis));
            else if (type.equalsIgnoreCase("office"))
                addNewAddressBinding.officeBtn.setBackground(getResources().getDrawable(R.drawable.assentcolorborderlightraduis));
            else
                addNewAddressBinding.otherBtn.setBackground(getResources().getDrawable(R.drawable.assentcolorborderlightraduis));

        }else {
            addNewAddressBinding.stateEt.setText(SharedPrefManager.getState(Constrants.State));
            addNewAddressBinding.cityEt.setText(SharedPrefManager.getCity(Constrants.City));
            addNewAddressBinding.pincodeEt.setText(SharedPrefManager.getPinCode(Constrants.PinCode));
        }
        if (id!=null) {
            addNewAddressBinding.headlyaout.productCatName.setText(getResources().getString(R.string.edit_address));
            addNewAddressBinding.AddBtn.setText(getResources().getString(R.string.update_address));
        } else{
            addNewAddressBinding.headlyaout.productCatName.setText(getResources().getString(R.string.add_new_address));
            addNewAddressBinding.AddBtn.setText(getResources().getString(R.string.add_address));
        }

        addNewAddressBinding.AddBtn.setOnClickListener(v -> {
            if (addNewAddressBinding.nameEt.getText().toString().isEmpty()){
                addNewAddressBinding.nameEt.setError("Please enter name");
                addNewAddressBinding.nameEt.requestFocus();
            }else if (addNewAddressBinding.houseNoEt.getText().toString().isEmpty()){
                addNewAddressBinding.houseNoEt.setError("Please enter flat/house/office no.");
                addNewAddressBinding.houseNoEt.requestFocus();
            }else if (addNewAddressBinding.streetdetailEt.getText().toString().isEmpty()){
                addNewAddressBinding.streetdetailEt.setError("Please enter street/society/office name");
                addNewAddressBinding.streetdetailEt.requestFocus();
            }else if (addNewAddressBinding.areaLocalityEt.getText().toString().isEmpty()){
                addNewAddressBinding.areaLocalityEt.setError("Please enter area");
                addNewAddressBinding.areaLocalityEt.requestFocus();
            }else if (addNewAddressBinding.etAddressSign.getText().toString().isEmpty()){
                addNewAddressBinding.etAddressSign.setError("Please select your location");
                addNewAddressBinding.etAddressSign.requestFocus();
            }else if (type.isEmpty()){
                Utils.Toast(AddNewAddress.this,"Please select address type");
            }else {
                if (id!=null) {
                    updateAddress(id);
                } else{
                    addAddress();
                }
            }

        });

        Places.initialize(AddNewAddress.this, getResources().getString(R.string.google_key));
        placesClient = Places.createClient(this);
        addNewAddressBinding.etAddressSign.setOnClickListener(v -> {
            List<Place.Field>
                    fields = Arrays.asList(Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.ADDRESS,
                    Place.Field.LAT_LNG);

// Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(AddNewAddress.this);
            startActivityForResult(intent, 1);
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LocationPermission();
//        startLocationUpdates();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 51) {
            if (resultCode == RESULT_OK) {
//                getDeviceLocation();
            }
        }
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Place place =Autocomplete.getPlaceFromIntent(data);
            String address = place.getName() + ", " + place.getAddress();

//            SharedPrefManager.setAddress(Constrants.DeliveryAddress,address);
            addNewAddressBinding.etAddressSign.setText(address);
            center = place.getLatLng();
            if (center!=null) {
                lati = center.latitude;
                longi = center.longitude;
                Log.i("", "Place_search: " +address+"\n"+center.latitude+"  ,  " +center.longitude);
            }
//            mFusedLocationProviderClient.removeLocationUpdates(locationCallback);

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // TODO: Handle the error.
            Status status = Autocomplete.getStatusFromIntent(data);
            Log.i("", status.getStatusMessage());
        } else if (resultCode == RESULT_CANCELED) {
            // The user canceled the operation.

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
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
                                lati = currentLocation.getLatitude();
                                longi = currentLocation.getLongitude();

                                Geocoder geocoder = new Geocoder(AddNewAddress.this, Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);
                                    Address addressobj = addresses.get(0);
                                    if (addressobj!=null)
                                    addNewAddressBinding.etAddressSign.setText(addressobj.getAddressLine(0));
//                                                               search_tv.setText(addressobj.getAddressLine(0));
//                                                               All_Book_List_Method();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Utils.Toast(AddNewAddress.this, "Unable to find current location . Try again later");
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

    public void updateAddress(String Address_id) {
        HashMap objectNew = new HashMap();
        objectNew.put("address_id", Address_id);
        objectNew.put("name", addNewAddressBinding.nameEt.getText().toString());
        objectNew.put("street", addNewAddressBinding.streetdetailEt.getText().toString());
        objectNew.put("house", addNewAddressBinding.houseNoEt.getText().toString());
        objectNew.put("address", addNewAddressBinding.areaLocalityEt.getText().toString());
        objectNew.put("mobile", addNewAddressBinding.contactEt.getText().toString());
        objectNew.put("type", type);

        Log.i("updateAddress_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.UpdateUserAddress,
                objectNew, AddNewAddress.this, RequestCode.CODE_UpdateUserAddress, 1);
    }
    public void addAddress() {
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        objectNew.put("name", addNewAddressBinding.nameEt.getText().toString());
        objectNew.put("street", addNewAddressBinding.streetdetailEt.getText().toString());
        objectNew.put("house", addNewAddressBinding.houseNoEt.getText().toString());
        objectNew.put("address", addNewAddressBinding.areaLocalityEt.getText().toString());
        objectNew.put("mobile", addNewAddressBinding.contactEt.getText().toString());
        objectNew.put("lattitude", lati+"");
        objectNew.put("longitude",longi+"");
        objectNew.put("city", addNewAddressBinding.cityEt.getText().toString());
        objectNew.put("state", addNewAddressBinding.stateEt.getText().toString());
        objectNew.put("pincode", addNewAddressBinding.pincodeEt.getText().toString());
        objectNew.put("type", type);

        Log.i("addAddress_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.AddUserAddress,
                objectNew, AddNewAddress.this, RequestCode.CODE_AddUserAddress, 1);
    }
    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_UpdateUserAddress == taskcode) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Utils.Toast(AddNewAddress.this,jsonObject.optString("message"));
                Log.i("updateAddress_res", response);
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (RequestCode.CODE_AddUserAddress == taskcode) {
            Log.i("addAddress_res", response);
            finish();
        }
    }
}
