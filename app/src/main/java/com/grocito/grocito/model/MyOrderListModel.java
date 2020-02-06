package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrderListModel {

    @SerializedName("status_code")
    @Expose
    public Integer statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;

    public class Datum {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("warehouse_id")
        @Expose
        public String warehouseId;
        @SerializedName("seller_id")
        @Expose
        public String sellerId;
        @SerializedName("total_amount")
        @Expose
        public String  totalAmount;
        @SerializedName("order_id")
        @Expose
        public String orderId;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("shipped_date")
        @Expose
        public String shippedDate;
        @SerializedName("shipping_charge")
        @Expose
        public Integer shippingCharge;
        @SerializedName("total_return_amount")
        @Expose
        public String totalReturnAmount;
        @SerializedName("item_count")
        @Expose
        public Integer itemCount;
        @SerializedName("return_count")
        @Expose
        public Integer returnCount;

    }

}
