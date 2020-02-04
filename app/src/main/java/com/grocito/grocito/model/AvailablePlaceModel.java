package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AvailablePlaceModel {
    public class Datum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("icon")
        @Expose
        public String icon;
        @SerializedName("state_id")
        @Expose
        public Integer stateId;
        @SerializedName("status")
        @Expose
        public Integer status;

    }

    @SerializedName("status_code")
    @Expose
    public Integer statusCode;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;
    @SerializedName("icon_path")
    @Expose
    public String iconPath;
    @SerializedName("message")
    @Expose
    public String message;
}
