package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SelectedAddressModel {

    public class Datum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("house")
        @Expose
        public String house;
        @SerializedName("street")
        @Expose
        public String street;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("landmark")
        @Expose
        public String landmark;
        @SerializedName("state")
        @Expose
        public String state;
        @SerializedName("pincode")
        @Expose
        public String pincode;
        @SerializedName("lattitude")
        @Expose
        public String lattitude;
        @SerializedName("longitude")
        @Expose
        public String longitude;
        @SerializedName("is_default")
        @Expose
        public Integer isDefault;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

    }

    @SerializedName("status_code")
    @Expose
    public Integer statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;
    @SerializedName("error_message")
    @Expose
    public String errorMessage;


}
