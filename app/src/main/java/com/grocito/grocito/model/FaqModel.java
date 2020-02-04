package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FaqModel {
    @SerializedName("faq")
    @Expose
    public List<Faq> faq = null;

    public class Faq {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("section_id")
        @Expose
        public Integer sectionId;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("banned")
        @Expose
        public Integer banned;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

    }
}
