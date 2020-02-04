package com.grocito.grocito.api;


import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiConfig {

    @Multipart
    @POST("api")
    Call<JsonObject> PostContract(
//                                  @Part List<MultipartBody.Part> photos,
            @Part MultipartBody.Part image,
            @Part("request") RequestBody name
    );

    @FormUrlEncoded
    @POST("/user-reg-step-1")
    Call<JsonObject> SignUpStep1(
            @Field("mobile") String mobile,
            @Field("f_name") String f_name,
            @Field("l_name") String  l_name,
            @Field("email") String email,
            @Field("reff_code") String reff_code
    );

    @FormUrlEncoded
    @POST("user-search")
    Call<JsonObject> searchProduct(
            @Field("pincode") String pincode,
            @Field("key_words") String key_words
    );
    @Multipart
    @POST("update-profile-image")
    Call<JsonObject> uploadProfile(
            @Part("user_id") RequestBody user_id,
            @Part MultipartBody.Part profile_image
    );

}
