package com.grocito.grocito.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.grocito.grocito.R;
import com.grocito.grocito.api.ApiConfig;
import com.grocito.grocito.api.AppConfig;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityMyAccountBinding;
import com.grocito.grocito.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccount extends AppCompatActivity {

    private ActivityMyAccountBinding myAccountBinding;
    private android.app.AlertDialog dialog;
    private int REQUEST_CAMERA_PERMISSION = 1;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myAccountBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_account);

        myAccountBinding.headlyaout.cartRL.setVisibility(View.GONE);
        myAccountBinding.headlyaout.searchIcon.setVisibility(View.GONE);

        myAccountBinding.headlyaout.productCatName.setText(getResources().getString(R.string.my_account));

        myAccountBinding.headlyaout.backBtn.setOnClickListener(view -> finish());

        myAccountBinding.editIcon.setOnClickListener(view -> startActivity(new Intent(MyAccount.this, EditProfile.class)));

        myAccountBinding.changeTV.setOnClickListener(view -> startActivity(new Intent(MyAccount.this, AddNewAddress.class)));

        myAccountBinding.fbIcon.setOnClickListener(v -> {
                    if (isAppInstalled()) {
                        Toast.makeText(getApplicationContext(), "facebook app already installed", Toast.LENGTH_SHORT).show();
                        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                        String facebookUrl = getFacebookPageURL(this);
                        facebookIntent.setData(Uri.parse(facebookUrl));
                        startActivity(facebookIntent);

                    } else {
                        Toast.makeText(getApplicationContext(), "facebook app not installing", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        myAccountBinding.instaIcon.setOnClickListener(v -> {
                    Uri uri = Uri.parse("http://instagram.com/_u/grocito_online");
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                    likeIng.setPackage("com.instagram.android");

                    try {
                        startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://instagram.com/grocito_online")));
                    }
                }
        );
//        myAccountBinding.logoutLL.setOnClickListener(view -> dialog =
//                Utils.retryAlertDialog(MyAccount.this,
//                        getResources().getString(R.string.app_name),
//                        getResources().getString(R.string.Are_you_sure_to_logout),
//                        getResources().getString(R.string.Cancel),
//                        getResources().getString(R.string.Yes), v -> {
//            //                        logout(true);
//                            Utils.logout(MyAccount.this);
//                            dialog.dismiss();
//                        }));

        myAccountBinding.userEmailTv.setText(SharedPrefManager.getUserEmail(Constrants.UserEmail));
        myAccountBinding.mobileTv.setText(SharedPrefManager.getUserMobile(Constrants.UserMobile));
//        myAccountBinding.addressTv.setText(SharedPrefManager.getAddress(Constrants.));

        if (SharedPrefManager.getUserName(Constrants.UserName) != null) {
            String[] name = SharedPrefManager.getUserName(Constrants.UserName).split(" ");
            if (Utils.checkEmptyNull(name[0])) {
                myAccountBinding.userNameTv.setText(Utils.FirstLatterCap(name[0]));
            }
        }

        String url = WebUrls.BASE_URL + WebUrls.UserProfileImageURL + SharedPrefManager.getUserPic(Constrants.UserPic);
        Log.i("sadfasfsdafsa", url);
        Glide.with(this).load(url).placeholder(R.drawable.avatar).into(myAccountBinding.userPic);

//        myAccountBinding.myOrderLL.setOnClickListener(v -> startActivity(new Intent(MyAccount.this, MyOrderList.class)));
        myAccountBinding.userPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePopup();
            }
        });
    }

    public static String FACEBOOK_URL = "https://www.facebook.com/grocito";
    public static String FACEBOOK_PAGE_ID = "grocito";

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.orca", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    public boolean isAppInstalled() {
        try {
            getApplicationContext().getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        myAccountBinding.userEmailTv.setText(SharedPrefManager.getUserEmail(Constrants.UserEmail));
        myAccountBinding.mobileTv.setText(SharedPrefManager.getUserMobile(Constrants.UserMobile));
//        myAccountBinding.addressTv.setText(SharedPrefManager.getAddress(Constrants.));

        if (SharedPrefManager.getUserName(Constrants.UserName) != null) {
            String[] name = SharedPrefManager.getUserName(Constrants.UserName).split("");
            if (Utils.checkEmptyNull(name[0])) {
                myAccountBinding.userNameTv.setText("Hello," + Utils.FirstLatterCap(name[0]));
            }
        }
    }

    private void ImagePopup() {
        try {
            final Dialog dialog = new Dialog(MyAccount.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.pop_profile);
            dialog.show();

            LinearLayout txtGallery = (LinearLayout) dialog.findViewById(R.id.layoutGallery);
            LinearLayout txtCamera = (LinearLayout) dialog.findViewById(R.id.layoutCamera);
            txtCamera.setOnClickListener(v -> {
                int currentAPIVersion = Build.VERSION.SDK_INT;
                if (currentAPIVersion >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(MyAccount.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MyAccount.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
                    } else {
                        selectCameraImage();
                        dialog.dismiss();
                    }
                } else {
                    selectCameraImage();
                    dialog.dismiss();
                }
            });
            txtGallery.setOnClickListener(v -> {
                int currentAPIVersion = Build.VERSION.SDK_INT;
                if (currentAPIVersion >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(MyAccount.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MyAccount.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    } else {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_PICK);
                        startActivityForResult(Intent.createChooser(intent, "Select Image"), 100);
                    }
                } else {
                    dialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(intent, "Select Image"), 100);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void selectCameraImage() {
        // TODO Auto-generated method stub
        String fileName = "new-photo-name.jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image captured by camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, 300);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                myAccountBinding.userPic.setImageBitmap(selectedImage);
                productUploadMethod();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 300) {
            Uri selectedImageUri = null;
            selectedImageUri = imageUri;
            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                myAccountBinding.userPic.setImageBitmap(selectedImage);
                productUploadMethod();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
//        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
//            imageUri = data.getData();
//            final InputStream imageStream;
//            try {
//                imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                myAccountBinding.userPic.setImageBitmap(selectedImage);
//                productUploadMethod();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//        } else if (requestCode == 300) {
//            Uri selectedImageUri = null;
//            selectedImageUri = imageUri;
//            final InputStream imageStream;
//            try {
//                imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                myAccountBinding.userPic.setImageBitmap(selectedImage);
//                productUploadMethod();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static String getPath(Context context, Uri uri) {
        String[] data = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, uri, data, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void productUploadMethod() {
        if (imageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading, please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            MultipartBody.Part filePartmultipleImages;
            Log.d("upload_image_array", imageUri.toString());

            File file = new File(getPath(MyAccount.this, imageUri));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            filePartmultipleImages = MultipartBody.Part.createFormData("profile_image", file.getName(), requestBody);

            RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), SharedPrefManager.getUserID(Constrants.UserId));

            ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
            Call<JsonObject> call = getResponse.uploadProfile(
                    user_id,
                    filePartmultipleImages
            );
            Log.d("profile_OBj", call.toString());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject obj = new JSONObject(response.body().toString());
                            if (obj.optInt("status_code") == 1) {

                                SharedPrefManager.setUserPic(Constrants.UserPic, obj.optString("image"));
                                Log.d("sdafsafsafsa", obj.optString("image"));
                                Toast.makeText(MyAccount.this, obj.optString("message"), Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(MyAccount.this, obj.optString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {

                        try {
                            JSONObject responseJ = new JSONObject(response.errorBody().string());
                            System.out.println("error response " + responseJ.toString());
                            progressDialog.dismiss();

                        } catch (Exception e) {

                        }

                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(MyAccount.this, "" + t.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println("fail response.." + t.toString());
//                        dismissProgressBar();
                }
            });

        } else {
            Utils.Toast(this, "Please Select Image");
        }
    }
}
