package com.grocito.grocito.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.grocito.grocito.R;
import com.grocito.grocito.adapter.MyOrderDetailsItemListAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.ActivityMyOrderDetailsBinding;
import com.grocito.grocito.model.MyOrderDetailsModel;
import com.grocito.grocito.model.UserOrderTrack;
import com.grocito.grocito.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class MyOrderDetails extends AppCompatActivity implements WebCompleteTask {

    private ActivityMyOrderDetailsBinding binding;
    private ArrayList<MyOrderDetailsModel.MetaDatum> arrayList = new ArrayList<>();
    private MyOrderDetailsItemListAdapter adapter;
    private String order_id = "", seller_id = "", shiped_date = "", current_date = "";
    public static long current_date_millis, shiped_date_mills;
    Display mDisplay;
    String imagesUri;
    String path;
    Bitmap b;
    int totalHeight;
    int totalWidth;
    public static final int READ_PHONE = 110;
    String file_name = "Screenshot";
    File myPath;
    private boolean forceRefresh = false;
    private boolean isRefreshing = false;
    public static boolean cancel = false,orderotp=false;
    ArrayList<String> exchArray = new ArrayList<>();
    ArrayList<String> returnArray = new ArrayList<>();

    private static MyOrderDetails mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_order_details);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mDisplay = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PHONE);
            }
        }

        mInstance = this;
        binding.headlyaout.cartRL.setVisibility(View.GONE);
        binding.headlyaout.searchIcon.setVisibility(View.GONE);
        binding.headlyaout.productCatName.setText(getResources().getString(R.string.my_order));
        binding.headlyaout.backBtn.setOnClickListener(view -> finish());


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyOrderDetailsItemListAdapter(this, arrayList, exchArray, returnArray);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (getIntent().getExtras() != null) {
            order_id = getIntent().getExtras().getString("order_id", "");
//            shiped_date = getIntent().getExtras().getString("shiped_date", "");
            seller_id = getIntent().getExtras().getString("seller_id", "");


//            Log.i("shipped_date", shiped_date + "");
//            final long millisToAdd = 3600000 * 16; //16 hours
//            Date d = null;
//            try {
//                d = Utils.formatDateFromDateDate("yyyy-MM-dd", "yyyy-MM-dd", shiped_date);
//                d.setTime(d.getTime() + millisToAdd);
//                shiped_date_mills = d.getTime() + millisToAdd;
//                Log.i("shipped_date_aft", d + "");
//                System.out.println("date_mils" + d.getTime() + "\n new_value: " + (d.getTime() + millisToAdd));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            myOrderList(1);
            userOrderTracking();
        }
//        binding.generateInvoice.setVisibility(View.VISIBLE);
//        binding.generateInvoice.setOnClickListener(v -> {
//            binding.generateInvoice.setVisibility(View.GONE);
//            takeScreenShot();
//        });
        binding.refresh.setOnRefreshListener(() -> {
            if (!isRefreshing) {
                forceRefresh = true;
                binding.refresh.setRefreshing(true);
                userOrderTracking();
                myOrderList(1);
            }
        });

    }

    public static MyOrderDetails getInstance(){
        return mInstance;
    }

    public void userOrderTracking() {
        HashMap objectNew = new HashMap();
        objectNew.put("order_id", order_id);
        new WebTask(this, WebUrls.BASE_URL + WebUrls.UserOrderTracking, objectNew,
                MyOrderDetails.this, RequestCode.CODE_UserOrderTracking, 5);
    }

    public void myOrderList(int reload) {
//        binding.matrialProgress.setVisibility(View.VISIBLE);
//        binding.emptyLL.setVisibility(View.GONE);
//        binding.recyclerView.setVisibility(View.GONE);
        binding.llPdf.setVisibility(View.GONE);
        HashMap objectNew = new HashMap();
        objectNew.put("order_id", order_id);
        objectNew.put("seller_id", seller_id);
        Log.i("UserOrderDetails_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.UserOrderDetails, objectNew,
                MyOrderDetails.this, RequestCode.CODE_UserOrderDetails, reload);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onComplete(String response, int taskcode) {

        if (RequestCode.CODE_UserOrderDetails == taskcode) {
            Log.i("UserOrderDetails_res", response);
            MyOrderDetailsModel orderListModel = JsonDeserializer.deserializeJson(response, MyOrderDetailsModel.class);
            if (orderListModel.statusCode == 1) {

//                binding.matrialProgress.setVisibility(View.GONE);
//                binding.emptyLL.setVisibility(View.GONE);
//                binding.recyclerView.setVisibility(View.VISIBLE);

                if (orderListModel.date != null && !orderListModel.date.isEmpty()) {
                    shiped_date = orderListModel.date;
                    Log.i("shipped_date", shiped_date + "");
                    final long millisToAdd = 3600000 * 16; //16 hours
                    Date d = null;
                    try {
                        d = Utils.formatDateFromDateDate("yyyy-MM-dd", "yyyy-MM-dd", shiped_date);
                        d.setTime(d.getTime() + millisToAdd);
                        shiped_date_mills = d.getTime() + millisToAdd;
                        Log.i("shipped_date_aft", d + "");
                        System.out.println("date_mils" + d.getTime() + "\n new_value: " + (d.getTime() + millisToAdd));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                current_date = dateFormat.format(calendar.getTime());
                Log.i("current_date", current_date);
                current_date_millis = calendar.getTimeInMillis();
                Log.i("current_date_mills", current_date_millis + "");

                Log.i("differ_mills", current_date_millis - shiped_date_mills + "");

                if (orderListModel.order_otp!=null){
                    orderotp = true;
                }else {
                    orderotp = false;
                }

                binding.orderidTv.setText(String.format("Order id : %s", orderListModel.data.orderId));
                binding.dateTimeTv.setText(String.format("%s", orderListModel.data.createdAt));

                if (orderListModel.data.status.equalsIgnoreCase("cancelled")) {
                    cancel = true;
                    binding.orderTrackLL.setVisibility(View.GONE);
                    binding.orderStTv.setVisibility(View.VISIBLE);
                } else {
                    binding.orderTrackLL.setVisibility(View.VISIBLE);
                    binding.orderStTv.setVisibility(View.GONE);
                }

                if (orderListModel.data.createdAt != null && !orderListModel.data.createdAt.isEmpty()) {
                    final long millisToAdd = 60000 * Integer.parseInt(orderListModel.data.expressTime); //55 minutes
                    Date d = null;
                    try {
                        d = Utils.formatDateFromDateDate("yyyy-MM-dd hh:mm:ss", "hh:mm:ss", orderListModel.data.createdAt);
                        d.setTime(d.getTime() + millisToAdd);
//                        shiped_date_mills = d.getTime() + millisToAdd;
                        if (orderListModel.data.deliveryType.equalsIgnoreCase("express")) {
                            SimpleDateFormat inputFormat = new SimpleDateFormat(
                                    "EEE MMM dd HH:mm:ss 'GMT' yyyy", Locale.US);
                            inputFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));

                            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a");
// Adjust locale and zone appropriately
//                            Date date = inputFormat.parse(inputText);
                            String outputText = outputFormat.format(d);
                            binding.deliveryTimeTv.setText(String.format("%s", outputText + ""));
                        } else {
                            binding.deliveryTimeTv.setText(String.format("%s", orderListModel.data.deliveryTime));
                        }
                        System.out.println("d_date" + orderListModel.data.createdAt + "\n new_date: " + d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                binding.deliveryDateTv.setText(String.format("%s", orderListModel.data.deliveryDate));
                binding.deliveryTypTv.setText(String.format("%s", orderListModel.data.deliveryType));

                if (orderListModel.order_otp != null && !orderListModel.order_otp.isEmpty()) {
                    binding.otpview.setVisibility(View.VISIBLE);
                    binding.orderOtpTv.setText(String.format("%s", orderListModel.order_otp));
                } else {
                    binding.otpview.setVisibility(View.GONE);
                }

                String string = orderListModel.data.status.replaceAll("_", " ");
                binding.orderStatusTv.setText(String.format("%s", Utils.FirstLatterCap(string)));
                binding.paymentOptionTv.setText(String.format("Payment Mode : %s", orderListModel.data.paymentMode));

                if (orderListModel.data.paymentStatus.equals("faild")) {
                    binding.paymentStatusTv.setTextColor(getResources().getColor(R.color.red));
                    binding.paymentStatusTv.setText(String.format("Payment Status : %s", "Failed"));
                } else {
                    binding.paymentStatusTv.setTextColor(getResources().getColor(R.color.green));
                    if (orderListModel.data.paymentStatus.equals("cod"))
                        binding.paymentStatusTv.setText(String.format("Payment Status : %s", "Cash on delivery"));
                    else
                        binding.paymentStatusTv.setText(String.format("Payment Status : %s", Utils.FirstLatterCap(orderListModel.data.paymentStatus)));
                }
//                binding.netAmtTv.setText(String.format("₹.%.2f", Double.parseDouble(orderListModel.data.netAmount)));
                binding.sgstAmtTv.setText(String.format("₹%.0f", Double.parseDouble(orderListModel.data.sgstAmount)));
                binding.cgstAmtTv.setText(String.format("₹%.0f", Double.parseDouble(orderListModel.data.gstAmount)));

                double subAmt = Double.parseDouble(orderListModel.data.totalAmount)-Double.parseDouble(orderListModel.totalReturnAmount);
                binding.subAmtTv.setText(String.format("₹%.0f", subAmt));
                double delivery_charge;
                if (orderListModel.itemCount == orderListModel.returnCount){
                    delivery_charge = 0;
                }else {
                    delivery_charge = Double.parseDouble(orderListModel.data.shippingCharge);
                }
                binding.deliveryChargeTv.setText(String.format("₹%.0f", delivery_charge));
                double total = subAmt + delivery_charge;
                binding.totalTv.setText(String.format("₹%.0f", total));
                if (orderListModel.data.address != null) {
                    if (orderListModel.data.address.name != null)
                        binding.nameTv.setText(String.format("%s \n%s", orderListModel.data.address.name, orderListModel.data.address.mobile));
                    if (orderListModel.data.address.type != null)
                        binding.typeTv.setText(String.format("%s", orderListModel.data.address.type));

                    binding.addressTv.setText(String.format(Utils.FirstLatterCap(orderListModel.data.address.type) +" Address : %s %s %s %s %s(%s)",
                            orderListModel.data.address.house,
                            orderListModel.data.address.street,
                            orderListModel.data.address.landmark,
                            orderListModel.data.address.city,
                            orderListModel.data.address.state,
                            orderListModel.data.address.pincode));

                }
                arrayList.clear();
                arrayList.addAll(orderListModel.metaData);
                if (exchArray != null)
                    exchArray.clear();
                if (returnArray != null)
                    returnArray.clear();
                for (int i = 0; i < orderListModel.exchangeReason.size(); i++) {
                    exchArray.add(orderListModel.exchangeReason.get(i).title);
                }
                for (int i = 0; i < orderListModel.returnReason.size(); i++) {
                    returnArray.add(orderListModel.returnReason.get(i).title);
                }
                adapter.notifyDataSetChanged();
                binding.llPdf.setVisibility(View.VISIBLE);
            } else {
//                binding.matrialProgress.setVisibility(View.GONE);
//                binding.emptyLL.setVisibility(View.VISIBLE);
//                binding.recyclerView.setVisibility(View.GONE);
            }


        }

        if (RequestCode.CODE_UserOrderTracking == taskcode) {
            Log.i("UserOrderTracking_res", response);
            UserOrderTrack userOrderTrack = JsonDeserializer.deserializeJson(response, UserOrderTrack.class);
            if (userOrderTrack.statusCode == 1) {

                forceRefresh = false;
                isRefreshing = false;
                binding.refresh.setRefreshing(false);

                for (int i = 0; i < userOrderTrack.data.size(); i++) {
                    if (userOrderTrack.data.get(i).type.equalsIgnoreCase("pending")) {
                        placed(userOrderTrack.data.get(i).date);
                    } else if (userOrderTrack.data.get(i).type.equalsIgnoreCase("assign_to_rider")) {
                        placed(userOrderTrack.data.get(i).date);
                        accept(userOrderTrack.data.get(i).date);
                    } else if (userOrderTrack.data.get(i).type.equalsIgnoreCase("ready_to_shiped")) {
                        placed(userOrderTrack.data.get(i).date);
                        accept(userOrderTrack.data.get(i).date);
                        shipped(userOrderTrack.data.get(i).date);
                    } else if (userOrderTrack.data.get(i).type.equalsIgnoreCase("assign_to_rider_to_deliverd")) {
                        placed(userOrderTrack.data.get(i).date);
                        accept(userOrderTrack.data.get(i).date);
                        shipped(userOrderTrack.data.get(i).date);
                        onWay(userOrderTrack.data.get(i).date);
                    } else if (userOrderTrack.data.get(i).type.equalsIgnoreCase("delivered")) {
                        placed(userOrderTrack.data.get(i).date);
                        accept(userOrderTrack.data.get(i).date);
                        shipped(userOrderTrack.data.get(i).date);
                        onWay(userOrderTrack.data.get(i).date);
                        delivered(userOrderTrack.data.get(i).date);
                    }
                }
//                if (userOrderTrack.data.size() == 1) {
//                    Log.i("order_type", userOrderTrack.data.get(0).type);
//                    Log.i("order_date", userOrderTrack.data.get(0).date);
//                    placed(userOrderTrack.data.get(0).date);
//                } else if (userOrderTrack.data.size() == 2) {
//                    placed(userOrderTrack.data.get(0).date);
//                    accept(userOrderTrack.data.get(1).date);
//                } else if (userOrderTrack.data.size() == 3) {
//                    placed(userOrderTrack.data.get(0).date);
//                    accept(userOrderTrack.data.get(1).date);
//                    shipped(userOrderTrack.data.get(2).date);
//                } else if (userOrderTrack.data.size() == 4) {
//                    placed(userOrderTrack.data.get(0).date);
//                    accept(userOrderTrack.data.get(1).date);
//                    shipped(userOrderTrack.data.get(2).date);
//                    onWay(userOrderTrack.data.get(3).date);
////                    delivered(userOrderTrack.data.get(3).date);
//                } else if (userOrderTrack.data.size() == 5) {
//                    placed(userOrderTrack.data.get(0).date);
//                    accept(userOrderTrack.data.get(1).date);
//                    shipped(userOrderTrack.data.get(2).date);
//                    onWay(userOrderTrack.data.get(3).date);
//                    delivered(userOrderTrack.data.get(4).date);
//                }
            }
        }
    }

    public void placed(String date) {
        try {
            binding.placedDT.setText(Utils.formatDateFromDateString(
                    "yyyy-MM-dd HH:mm:ss",
                    "dd-MMM-yyyy hh:mm:ss", date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.orderPlacedIv.setImageResource(R.drawable.placed_b);
        binding.a1.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
    }

    public void accept(String date) {
        binding.orderAcceptIv.setImageResource(R.drawable.accept_b);
        try {
            binding.acceptDT.setText(Utils.formatDateFromDateString(
                    "yyyy-MM-dd HH:mm:ss",
                    "dd-MMM-yyyy hh:mm:ss", date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.a1.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
        binding.a2.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
    }

    public void shipped(String date) {
        binding.ordershipedIv.setImageResource(R.drawable.shiped_b);
        try {
            binding.shippedDT.setText(Utils.formatDateFromDateString(
                    "yyyy-MM-dd HH:mm:ss",
                    "dd-MMM-yyyy hh:mm:ss", date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.a1.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
        binding.a2.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
        binding.a3.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
    }

    public void onWay(String date) {
        binding.orderOntheWayIv.setImageResource(R.drawable.onway_b);
        try {
            binding.onWayDT.setText(Utils.formatDateFromDateString(
                    "yyyy-MM-dd HH:mm:ss",
                    "dd-MMM-yyyy hh:mm:ss", date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.a1.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
        binding.a2.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
        binding.a3.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
        binding.a4.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
    }

    public void delivered(String date) {
        binding.orderDeliveredIv.setImageResource(R.drawable.delivered_b);
        try {
            binding.deliveredDT.setText(Utils.formatDateFromDateString(
                    "yyyy-MM-dd HH:mm:ss",
                    "dd-MMM-yyyy hh:mm:ss", date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.a1.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
        binding.a2.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
        binding.a3.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
        binding.a4.setBackgroundColor(getResources().getColor(R.color.md_teal_A700));
    }

    private void takeScreenShot() {

        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ScreenShot/");

        if (!folder.exists()) {
            boolean success = folder.mkdir();
        }

        path = folder.getAbsolutePath();
        path = path + "/" + file_name + System.currentTimeMillis() + ".pdf";

        // List<Integer> hights = new ArrayList<>();
        View u = findViewById(R.id.llPdf);

        NestedScrollView z = (NestedScrollView) findViewById(R.id.nestPdf);
        totalHeight = z.getChildAt(0).getHeight();
        totalWidth = z.getChildAt(0).getWidth();

        Log.e("totalHeight--", "" + totalHeight);
        Log.e("totalWidth--", "" + totalWidth);

        //Save bitmap
        String extr = Environment.getExternalStorageDirectory() + "/ScreenShot/";
        File file = new File(extr);
        if (!file.exists())
            file.mkdir();
        String fileName = file_name + ".jpg";
        myPath = new File(extr, fileName);
        imagesUri = myPath.getPath();
        FileOutputStream fos = null;
        b = getBitmapFromView(u, totalHeight, totalWidth);

        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        createPdf();

    }

    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    private void createPdf() {

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(b.getWidth(), b.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);

        Bitmap bitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);
        File filePath = new File(path);
        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();


        if (myPath.exists())
            myPath.delete();

        openPdf(path);
    }

    public void openPdf(String path) {
//        binding.generateInvoice.setVisibility(View.VISIBLE);
        File file = new File(path);
        Intent target = new Intent(Intent.ACTION_VIEW);
        Uri photoURI = FileProvider.getUriForFile(MyOrderDetails.this,
                getApplicationContext().getPackageName() + ".provider", file);
        target.setDataAndType(photoURI, "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No app to read PDF File", Toast.LENGTH_LONG).show();
        }
    }
}
