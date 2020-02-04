package com.grocito.grocito.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.grocito.grocito.R;

public class SharedPrefManager {
    public static SharedPreferences sp;
    private static Context mCtx;
    private static SharedPrefManager mInstance;

    public SharedPrefManager(Context context) {
        mCtx = context;
        sp = mCtx.getSharedPreferences("Grocito", Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SharedPrefManager(context);
        return mInstance;
    }

    public static void setUserID(String type, String userId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, userId);
        editor.apply();
    }

    public static String getUserID(String type) {
        return sp.getString(type, "");
    }

    public static void setUserFirstName(String type,String value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type,value);
        editor.apply();
    }
    public static String getUserFirstname(String type){
        return sp.getString(type,"");
    }

    public static void setUserLastName(String type,String value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type,value);
        editor.apply();
    }

    public static String getUserLastName(String type){
        return sp.getString(type,"");
    }


    public static void setUserPic(String type, String userId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, userId);
        editor.apply();
    }

    public static String getUserPic(String type) {
        return sp.getString(type, "");
    }

    public static void setUserMobile(String type, String userId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, userId);
        editor.commit();
    }

    public static String getUserMobile(String type) {
        String e = sp.getString(type, "");
        return e;
    }

    public static void setUserEmail(String type, String userId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, userId);
        editor.commit();
    }

    public static String getUserEmail(String type) {
        return sp.getString(type, "");
    }

    public static void setUserName(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.apply();
    }
    public static void setDeviceToken(String type, String userId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, userId);
        editor.apply();
    }

    public static String getDeviceToken(String type) {
        return sp.getString(type, "");
    }

    public static String getUserName(String type) {
        return sp.getString(type, "");
    }

    public static void setPinCode(String type, String userId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, userId);
        editor.apply();
    }

    public static String getPinCode(String type) {
        return sp.getString(type, "");
    }

    public static void setCity(String type, String userId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, userId);
        editor.apply();
    }

    public static String getCity(String type) {
        return sp.getString(type, "");
    }

    public static void setState(String type, String userId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, userId);
        editor.apply();
    }

    public static String getState(String type) {
        return sp.getString(type, "");
    }

    public static void setreferCode(String type, String userId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, userId);
        editor.apply();
    }

    public static String getreferCode(String type) {
        return sp.getString(type, "");
    }

    public static void setreferString(String type, String userId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, userId);
        editor.apply();
    }

    public static String getreferString(String type) {
        return sp.getString(type, "");
    }

    public static void setDeliveryLat(String type, String userId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, userId);
        editor.apply();
    }

    public static String getDeliveryLat(String type) {
        String e = sp.getString(type, "");
        return e;
    }

    public static void setDeliveryLong(String type, String userId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, userId);
        editor.commit();
    }

    public static String getDeliveryLong(String type) {
        String e = sp.getString(type, "");
        return e;
    }

    public static void setAddress(String type, String userId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, userId);
        editor.commit();
    }

    public static String getAddress(String type) {
        String e = sp.getString(type, "");
        return e;
    }

    public static void setCartItemCount(String key,int value){

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key,value);
        editor.apply();

    }

    public static int getCartItemCount(String key){
        return sp.getInt(key,0);
    }

    public static void setNotifCount(String key,int value){

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key,value);
        editor.apply();

    }

    public static int getNotifCount(String key){
        return sp.getInt(key,0);
    }

    public static void showMessage(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean isIntro(String key){
        return sp.getBoolean(key,false);
    }

    public static void setIntro(String key,boolean value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public static boolean isFBLogin(String key){
        return sp.getBoolean(key,false);
    }

    public static void setFbLogin(String key,boolean value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public static boolean isGoogleLogin(String key){
        return sp.getBoolean(key,false);
    }

    public static void setGoogleLogin(String key,boolean value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public static boolean isLogin(String key){
        return sp.getBoolean(key,false);
    }

    public static void setLogin(String key,boolean value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void storeIsLoggedIn(Boolean isLoggedIn) {
        SharedPreferences sharedPreferencesReg = mCtx.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesReg.edit();
        editor.putBoolean("key", isLoggedIn);
        editor.commit();
    }

    public boolean getIsLoggedIn(boolean default_value) {
        SharedPreferences sharedPreferencesReg = mCtx.getSharedPreferences("login", Context.MODE_PRIVATE);
        return sharedPreferencesReg.getBoolean("key", default_value);
    }

    public void hideSoftKeyBoard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

            //check if no view has focus.
            View v = activity.getCurrentFocus();
            if (v == null)
                return;
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean CheckPassword(String password) {
        String[] re = {"[a-z]", "[?=.*[@#$%!\\-_?&])(?=\\\\S+$]"};
        for (String r : re) {
            if (!Pattern.compile(r).matcher(password).find()) return false;
        }
        return true;
    }

    public static void ToastMSg(Activity activity){
        Toast.makeText(activity,"Save For Later",Toast.LENGTH_LONG).show();
    }
    public static void setBuildVersion(Context context, String deviceToken) {
        sp = (SharedPreferences) context.getSharedPreferences(context.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        sp.edit().putString(Constrants.FLD_BUILD_VERSION, deviceToken).commit();
    }


    public static void setAboutUsDes(String type,String value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type,value);
        editor.commit();
    }

    public static String getAboutUsDes(String type){
        return sp.getString(type,"");
    }

    public static void setPrivacyPolicy(String type,String value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type,value);
        editor.commit();
    }

    public static String getPrivacyPolicy(String type){
        return sp.getString(type,"");
    }

    public static void setTermCondition(String type,String value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type,value);
        editor.commit();
    }

    public static String getTermCondition(String type){
        return sp.getString(type,"");
    }
    public static void setCancelReturn(String type,String value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type,value);
        editor.commit();
    }

    public static String getCancelReturn(String type){
        return sp.getString(type,"");
    }

    public static void setFaq(String type,String value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type,value);
        editor.apply();
    }

    public static String getFaq(String type){
        return sp.getString(type,"");
    }

}
