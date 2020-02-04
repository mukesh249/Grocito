package com.grocito.grocito.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

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
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.grocito.grocito.R;
import com.grocito.grocito.databinding.ActivitySignUpBinding;
import com.grocito.grocito.presenter.SignUpPresenter;
import com.grocito.grocito.utils.Utils;
import com.grocito.grocito.viewmodel.SignUpViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private SignUpViewModel signUpViewModel;

    private FusedLocationProviderClient fusedLocationProviderClient;
    double lati,longi;
    LocationManager locationManager;
    String city_name="";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);

        signUpViewModel = new SignUpViewModel(this);
        binding.setSignupViewModel(signUpViewModel);
        binding.setSignupPresenter(new SignUpPresenter() {
            @Override
            public void SignupData() {
                final String f_name = binding.firstNameEt.getText().toString();
                final String l_name = binding.lastNameEt.getText().toString();
                final String email = binding.emailEt.getText().toString();
                final String mobile = binding.phoneNoEt.getText().toString();
                final String referral = binding.referralEt.getText().toString();
                signUpViewModel.SignUpRequest(mobile, f_name, l_name, email, referral,city_name);
            }
        });

        binding.backBtn.setOnClickListener(v -> finish());
        binding.bottomLL.setOnClickListener(v -> finish());

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        LocationPermission();
//        startLocationUpdates();
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
                                lati = currentLocation.getLatitude();
                                longi = currentLocation.getLongitude();

                                Geocoder geocoder = new Geocoder(SignUp.this, Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);
                                    Address addressobj = addresses.get(0);
                                    if (addressobj!=null) {
                                        city_name = addressobj.getLocality();
                                        Log.d("city_name",city_name);
                                    }
//                                                               search_tv.setText(addressobj.getAddressLine(0));
//                                                               All_Book_List_Method();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Utils.Toast(SignUp.this, "Unable to find current location . Try again later");
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
}
