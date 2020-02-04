package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RaisingModel {

    public class Datum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("complaint_id")
        @Expose
        public String complaintId;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("problem")
        @Expose
        public String problem;
        @SerializedName("solution")
        @Expose
        public String solution;
        @SerializedName("status")
        @Expose
        public Integer status;
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
}
