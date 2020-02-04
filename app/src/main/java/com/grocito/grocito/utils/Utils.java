package com.grocito.grocito.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import dmax.dialog.SpotsDialog;

import com.grocito.grocito.activities.Splash;
import com.grocito.grocito.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static AlertDialog retryCustomAlert;
    public static boolean isShown = false;
    public static AlertDialog progressDialog;

    public static boolean isOnline(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info != null)
            if (info.isConnected()) {
                return true;
            } else {
                return false;
            }
        else
            return false;
    }

    public static AlertDialog retryAlertDialog(Context mContext, String title, String
            msg, String firstButton, String SecondButton, View.OnClickListener secondButtonClick) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.retry_alert, null);
        dialogBuilder.setView(dialogView);

        TextView txtRAMsg = (TextView) dialogView.findViewById(R.id.txtRAMsg);
        TextView txtRAFirst = (TextView) dialogView.findViewById(R.id.txtRAFirst);
        TextView txtRASecond = (TextView) dialogView.findViewById(R.id.txtRASecond);
        View deviderView = (View) dialogView.findViewById(R.id.deviderView);

        txtRAMsg.setText(msg);

        if (firstButton.length() == 0) {
            txtRAFirst.setVisibility(View.GONE);
            deviderView.setVisibility(View.GONE);
        } else {
            txtRAFirst.setVisibility(View.VISIBLE);
            txtRAFirst.setText(firstButton);
        }

        if (SecondButton.length() == 0) {
            txtRASecond.setVisibility(View.GONE);
            deviderView.setVisibility(View.GONE);
        } else {
            txtRASecond.setVisibility(View.VISIBLE);
            txtRASecond.setText(SecondButton);
        }

        txtRAFirst.setOnClickListener(view -> {
            retryCustomAlert.dismiss();
            isShown = false;
        });

        if (secondButtonClick == null) {
            secondButtonClick = view -> {
                retryCustomAlert.dismiss();
                isShown = false;
            };
        }
        txtRASecond.setOnClickListener(secondButtonClick);

        if (!isShown) {

            retryCustomAlert = dialogBuilder.create();
            retryCustomAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            retryCustomAlert.show();
            isShown = true;
        }
        return retryCustomAlert;
    }

    public static void logout(Activity mContext) {
//        SharedPrefManager.setLogin(mContext, false);
//        SharedPrefManager.setUserModelJSON(mContext, "");
//        SharedPrefManager.setUserId(mContext, "");
        mContext.startActivity(new Intent(mContext, Splash.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        isShown = false;
    }

    public static void strikeText(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public static void Toast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }

    public static String FirstLatterCap(String string) {
        String fl = string.substring(0, 1).toUpperCase();
        return string.replaceFirst(string.substring(0, 1), fl);
    }

    public static boolean checkEmptyNull(String string) {
        return !TextUtils.isEmpty(string) && !string.equals("null");
    }

    public static boolean checkEmpty(String string) {
        return TextUtils.isEmpty(string);
    }

    public static void alertDialog(Context context, String title, String msg) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.retry_alert, null);
        dialogBuilder.setView(dialogView);

        TextView txtRAMsg = (TextView) dialogView.findViewById(R.id.txtRAMsg);
        TextView txtRAFirst = (TextView) dialogView.findViewById(R.id.txtRAFirst);
        TextView txtRASecond = (TextView) dialogView.findViewById(R.id.txtRASecond);
        View deviderView = (View) dialogView.findViewById(R.id.deviderView);

        AlertDialog dialog = dialogBuilder.create();
        txtRAMsg.setText(msg);
        txtRAFirst.setText("Ok");
        txtRASecond.setVisibility(View.GONE);

        txtRAFirst.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public static void setImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url).into(imageView);
    }

    public static void ProgressShow(Context context, AVLoadingIndicatorView loadingIndicatorView, View recyclerView) {
        loadingIndicatorView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public static void ProgressHide(Context context, AVLoadingIndicatorView loadingIndicatorView, View recyclerView) {
        loadingIndicatorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public static void CartCount(Context context, TextView view, int count) {
        if (view != null) {
            if (count > 0) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);

            }
            view.setText(String.valueOf(count));
        }
    }

    public static void ProgressDialog(Context context) {
        progressDialog = new SpotsDialog(context, R.style.Custom);
        progressDialog.show();
    }

    public static void ProgressDialogHide(Context context) {
        if (progressDialog.isShowing())
        progressDialog.dismiss();
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDateFromDateString(String inputDateFormat, String outputDateFormat, String inputDate) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormat);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormat);
        Date date = inputFormat.parse(inputDate);
        return outputFormat.format(date);
    }
    @SuppressLint("SimpleDateFormat")
    public static Date formatDateFromDateDate(String inputDateFormat, String outputDateFormat, String inputDate) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormat);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormat);
        Date date = inputFormat.parse(inputDate);
        return date;
    }
    public String compressImage(Context context,String imageUri) {

        String filePath = getRealPathFromURI(context,imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
//            Log.i("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
//                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
//                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
//                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private String getRealPathFromURI(Context context,String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

}
