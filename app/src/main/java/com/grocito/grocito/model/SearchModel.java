package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchModel {

    public class Datum {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("value")
        @Expose
        public String value;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("search_type")
        @Expose
        public String searchType;

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
}
