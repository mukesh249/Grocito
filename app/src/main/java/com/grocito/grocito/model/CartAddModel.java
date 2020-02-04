package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartAddModel {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("cart_count")
    @Expose
    public Integer cartCount;
    @SerializedName("error_message")
    @Expose
    public String errorMessage;
}
