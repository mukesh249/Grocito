package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterCatModel {


    public class Brand {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
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
    public class Data {

        @SerializedName("subCatData")
        @Expose
        public List<SubCatDatum> subCatData = null;
        @SerializedName("filter_brand")
        @Expose
        public List<FilterBrand> filterBrand = null;

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

    public class FilterBrand {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("brand_id")
        @Expose
        public Integer brandId;
        @SerializedName("slug")
        @Expose
        public String slug;
        @SerializedName("sku")
        @Expose
        public String sku;
        @SerializedName("category_id")
        @Expose
        public Integer categoryId;
        @SerializedName("category_slug")
        @Expose
        public String categorySlug;
        @SerializedName("sub_category_id")
        @Expose
        public Integer subCategoryId;
        @SerializedName("sub_category_slug")
        @Expose
        public String subCategorySlug;
        @SerializedName("super_sub_category_id")
        @Expose
        public Integer superSubCategoryId;
        @SerializedName("super_sub_category_slug")
        @Expose
        public String superSubCategorySlug;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("commission")
        @Expose
        public String commission;
        @SerializedName("p_gst")
        @Expose
        public Integer pGst;
        @SerializedName("color")
        @Expose
        public String color;
        @SerializedName("city_id")
        @Expose
        public Integer cityId;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("is_admin_approved")
        @Expose
        public Integer isAdminApproved;
        @SerializedName("stock_status")
        @Expose
        public Integer stockStatus;
        @SerializedName("share_count")
        @Expose
        public Integer shareCount;
        @SerializedName("is_cod")
        @Expose
        public Integer isCod;
        @SerializedName("is_return")
        @Expose
        public Integer isReturn;
        @SerializedName("is_exchange")
        @Expose
        public Integer isExchange;
        @SerializedName("is_sunday_bazar")
        @Expose
        public Integer isSundayBazar;
        @SerializedName("is_grocito_exclusive")
        @Expose
        public Integer isGrocitoExclusive;
        @SerializedName("related_product")
        @Expose
        public String relatedProduct;
        @SerializedName("is_featured")
        @Expose
        public Integer isFeatured;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("brand")
        @Expose
        public Brand brand;

    }
    public class SubCatDatum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("category_id")
        @Expose
        public Integer categoryId;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("category_slug")
        @Expose
        public String categorySlug;
        @SerializedName("slug")
        @Expose
        public String slug;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("status")
        @Expose
        public Integer status;

    }
}
