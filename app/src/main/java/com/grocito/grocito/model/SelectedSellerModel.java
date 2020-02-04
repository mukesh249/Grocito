package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SelectedSellerModel {


    public class Data {

        @SerializedName("item_data")
        @Expose
        public List<SeeAllProductsModel.ProductPriceDatum> itemData = null;
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

}
