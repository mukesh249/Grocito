package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserOrderTrack {

    public class Datum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("order_id")
        @Expose
        public Integer orderId;
        @SerializedName("reason")
        @Expose
        public String reason;
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("type")
        @Expose
        public String type;
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
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;
    @SerializedName("message")
    @Expose
    public String message;

}
