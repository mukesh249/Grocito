package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SelectedSellerModel2 {

    public class Data {

        @SerializedName("item_data")
        @Expose
        public List<ProductDetialModel.ProductItem_> itemData = null;
        @SerializedName("schemeName")
        @Expose
        public String schemeName;
        @SerializedName("productImagePath")
        @Expose
        public String productImagePath;

    }


        @SerializedName("status_code")
        @Expose
        public Integer statusCode;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("data")
        @Expose
        public Data data;



//    public class ItemDatum {
//
//        @SerializedName("id")
//        @Expose
//        public Integer id;
//        @SerializedName("product_id")
//        @Expose
//        public Integer productId;
//        @SerializedName("seller_id")
//        @Expose
//        public Integer sellerId;
//        @SerializedName("weight")
//        @Expose
//        public String weight;
//        @SerializedName("price")
//        @Expose
//        public Integer price;
//        @SerializedName("offer")
//        @Expose
//        public Integer offer;
//        @SerializedName("qty")
//        @Expose
//        public Integer qty;
//        @SerializedName("sprice")
//        @Expose
//        public Integer sprice;
//        @SerializedName("created_at")
//        @Expose
//        public String createdAt;
//        @SerializedName("updated_at")
//        @Expose
//        public String updatedAt;
//
//    }
}
