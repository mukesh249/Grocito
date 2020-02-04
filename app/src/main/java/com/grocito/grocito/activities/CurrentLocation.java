package com.grocito.grocito.activities;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.R;
import com.grocito.grocito.databinding.ActivityCurrentLocationBinding;
import com.grocito.grocito.model.AddressModelGoogle;
import com.grocito.grocito.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrentLocation extends AppCompatActivity implements OnMapReadyCallback , WebCompleteTask {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;

    private Location mLastKnowLocation;
    private LocationCallback locationCallback;
    private View mapView;
    private final float DEFAULT_ZOOM = 18;

    ActivityCurrentLocationBinding binding;

    private LatLng center;
    private Geocoder geocoder;
    private List<Address> addresses;
    double latitude,longitude;
    boolean searchadd = false;
    private String pinCode;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_current_location);

        // Initialize the AutocompleteSupportFragment.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            mapView = mapFragment.getView();
        }

        ((GradientDrawable) binding.bottomLL.getBackground()).setColor(getResources().getColor(R.color.white));
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(CurrentLocation.this);
        Places.initialize(CurrentLocation.this, getResources().getString(R.string.google_key));
        placesClient = Places.createClient(this);
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();


        binding.searchHeader.searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchadd = true;

//                startActivity(new Intent(CurrentLocation.this, SearchAddressGoogle.class));

                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.ADDRESS, Place.Field.LAT_LNG);

// Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(CurrentLocation.this);
                startActivityForResult(intent, 1);
            }
        });

        binding.searchHeader.backBtn.setOnClickListener(view -> finish());

        binding.findBtn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(CurrentLocation.this);
            View view1 = LayoutInflater.from(CurrentLocation.this).inflate(R.layout.retry_alert,null);
            builder.setView(view1);
            dialog = builder.create();
            TextView Ok = view1.findViewById(R.id.txtRASecond);
            TextView txtRAMsg = view1.findViewById(R.id.txtRAMsg);
            txtRAMsg.setText("Are you sure you want to update your delivery address?");
            TextView Cancel = view1.findViewById(R.id.txtRAFirst);
            Ok.setText("Ok");
            Cancel.setText("Cancel");
            Ok.setOnClickListener(v -> checkPinMethod(pinCode));
            Cancel.setOnClickListener(v -> dialog.dismiss());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

    }
    public void checkPinMethod(String pin) {
//        progressDialog.show();
        Utils.ProgressDialog(this);
        HashMap objectNew = new HashMap();
        objectNew.put("pincode", pin);
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        Log.i("pincode_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.UserCheckPinAvailability, objectNew,
                CurrentLocation.this, RequestCode.CODE_UserCheckPinAvailability, 5);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

//        try {
//            // Customise the styling of the base map using a JSON object defined
//            // in a raw resource file.
//            boolean success = googleMap.setMapStyle(
//                    MapStyleOptions.loadRawResourceStyle(
//                            this, R.raw.style_json));
//
//            if (!success) {
//                Log.e("", "Style parsing failed.");
//            }
//        } catch (Resources.NotFoundException e) {
//            Log.e("", "Can't find style. Error: ", e);
//        }
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        // For showing a move to my location button
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CurrentLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        } else {
            googleMap.setMyLocationEnabled(true);
        }

//        mMap.clear();

        if (!searchadd) {
            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    center = mMap.getCameraPosition().target;

                    mMap.clear();
                    try {
                        if (!searchadd){
                            new GetLocationAsync(center.latitude, center.longitude).execute();
                        }

                    } catch (Exception e) {
                    }
                }
            });
//            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
//
//                @Override
//                public void onCameraChange(CameraPosition arg0) {
//                      center = mMap.getCameraPosition().target;
//
//                    mMap.clear();
//                    try {
//                        new GetLocationAsync(center.latitude, center.longitude)
//                                .execute();
//
//                    } catch (Exception e) {
//                    }
//                }
//            });
        }

        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 180);


            //check if gps is enabled or not then request user to enable it.
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(5000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            SettingsClient settingsClient = LocationServices.getSettingsClient(CurrentLocation.this);
            Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());


            task.addOnSuccessListener(CurrentLocation.this, new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    getDeviceLocation();
                }
            });

            task.addOnFailureListener(CurrentLocation.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if (e instanceof ResolvableApiException) {
                        ResolvableApiException resolve = (ResolvableApiException) e;

                        try {
                            resolve.startResolutionForResult(CurrentLocation.this, 51);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 51) {
            if (resultCode == RESULT_OK) {
                getDeviceLocation();
            }
        }
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Place place =Autocomplete.getPlaceFromIntent(data);
            String address = place.getName() + ", " + place.getAddress();
                    Log.i("", "Place_search: " +address+"\n"+latitude+"  ,  " +longitude);
            binding.addressTv.setText(address);

            center = place.getLatLng();
            if (center!=null) {
                latitude = center.latitude;
                longitude = center.longitude;
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( latitude,longitude), DEFAULT_ZOOM));
                searchadd = false;
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
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_UserCheckPinAvailability == taskcode) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status_code") == 1) {
                    binding.invaildTv.setVisibility(View.GONE);
                    JSONObject data = jsonObject.optJSONObject("data");
                    SharedPrefManager.setPinCode(Constrants.PinCode, data.optString("pincode"));
                    SharedPrefManager.setCity(Constrants.City, data.optString("city_name"));
                    SharedPrefManager.setState(Constrants.State, data.optString("state_name"));

                    SharedPrefManager.setAddress(Constrants.DeliveryAddress,binding.addressTv.getText().toString());
                    SharedPrefManager.setDeliveryLat(Constrants.DeliveryLat,latitude+"");
                    SharedPrefManager.setDeliveryLong(Constrants.DeliveryLong,longitude+"");
//                    SharedPrefManager.setPinCode(Constrants.PinCode,pinCode);

                    startActivity(new Intent(CurrentLocation.this,HomeScreen.class)
                            .putExtra("activity","CurrentLocation").setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();

                } else {
                    binding.invaildTv.setVisibility(View.VISIBLE);
                    binding.invaildTv.setText(jsonObject.optString("message"));
                    dialog.dismiss();
//                    Utils.Toast(this,jsonObject.optString("message"));
                }
                Utils.ProgressDialogHide(this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetLocationAsync extends AsyncTask<String, Void, String> {
        double x, y;
        StringBuilder str;

        public GetLocationAsync(double latitude, double longitude) {
            x = latitude;
            y = longitude;
        }

        @Override
        protected void onPreExecute() {
            binding.addressTv.setText(" Getting location... ");
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                geocoder = new Geocoder(CurrentLocation.this, Locale.ENGLISH);
                addresses = geocoder.getFromLocation(x, y, 1);
                str = new StringBuilder();
                if (Geocoder.isPresent()) {
                    if (addresses.size() > 0) {
                        Address returnAddress = addresses.get(0);
                        String localityString = returnAddress.getLocality();
                        String city = returnAddress.getCountryName();
                        String region_code = returnAddress.getCountryCode();
                        String zipcode = returnAddress.getPostalCode();

                        str.append(localityString + "");
                        str.append(city + "" + region_code + "");
                        str.append(zipcode + "");
                    } else {
                    }

                } else {

                }
            } catch (IOException e) {
                Log.e("tag", e.getMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (addresses.size() > 0) {
                    binding.addressTv.setText(addresses.get(0).getAddressLine(0) + " ");
                    latitude = center.latitude;
                    longitude = center.longitude;
                    pinCode = addresses.get(0).getPostalCode();
                    Log.i("", "Place_onCameraMove:  " + center.latitude + "    " + center.longitude+ " picCode: " + pinCode);

                } else {

                    binding.addressTv.setText("Location not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    getDeviceLocation();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                    if (ActivityCompat.checkSelfPermission(CurrentLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(CurrentLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CurrentLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                1);
                    }
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void getDeviceLocation() {

        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                if (task.isSuccessful()) {
                    mLastKnowLocation = task.getResult();
                    if (mLastKnowLocation != null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnowLocation.getLatitude(), mLastKnowLocation.getLongitude()), DEFAULT_ZOOM));
                    } else {
                        LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setInterval(10000);
                        locationRequest.setFastestInterval(5000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                if (locationResult == null) {
                                    return;
                                }
                                mLastKnowLocation = locationResult.getLastLocation();
                                latitude = mLastKnowLocation.getLatitude();
                                longitude = mLastKnowLocation.getLongitude();
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), DEFAULT_ZOOM));
                                mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                            }
                        };

                        mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                } else {
                    Toast.makeText(CurrentLocation.this, "unable to get last location", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
