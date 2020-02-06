package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeeAllProductsModel {

    @SerializedName("status_code")
    @Expose
    public Integer statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {
        @SerializedName("productList")
        @Expose
        public List<ProductList> productList = null;

    }

    public class ProductList {

        @SerializedName("defaultProductName")
        @Expose
        public String defaultProductName;
        @SerializedName("defaultImage")
        @Expose
        public String defaultImage;
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("brand_name")
        @Expose
        public String brandName;
        @SerializedName("product_slug")
        @Expose
        public String productSlug;
        @SerializedName("offerprice")
        @Expose
        public Double offerprice;
        @SerializedName("price")
        @Expose
        public Double price;
        @SerializedName("sprice")
        @Expose
        public Double sprice;

    }

//    @SerializedName("status_code")
//    @Expose
//    public Integer statusCode;
//    @SerializedName("message")
//    @Expose
//    public String message;
//    @SerializedName("data")
//    @Expose
//    public Data data;
//
//    public class Catdata {
//
//        @SerializedName("id")
//        @Expose
//        public Integer id;
//        @SerializedName("name")
//        @Expose
//        public String name;
//        @SerializedName("slug")
//        @Expose
//        public String slug;
//        @SerializedName("type")
//        @Expose
//        public String type;
//        @SerializedName("description")
//        @Expose
//        public String description;
//        @SerializedName("position")
//        @Expose
//        public Integer position;
//        @SerializedName("is_special")
//        @Expose
//        public Integer isSpecial;
//        @SerializedName("image")
//        @Expose
//        public String image;
//        @SerializedName("created_at")
//        @Expose
//        public String createdAt;
//        @SerializedName("updated_at")
//        @Expose
//        public String updatedAt;
//        @SerializedName("status")
//        @Expose
//        public Integer status;
//
//    }
//
//    public class Data {
//
//        @SerializedName("catdata")
//        @Expose
//        public Catdata catdata;
//        @SerializedName("productList")
//        @Expose
//        public List<ProductList> productList = null;
//
//    }
//
//    public class PList {
//
//        @SerializedName("id")
//        @Expose
//        public Integer id;
//        @SerializedName("user_id")
//        @Expose
//        public Integer userId;
//        @SerializedName("brand_id")
//        @Expose
//        public Integer brandId;
//        @SerializedName("slug")
//        @Expose
//        public String slug;
//        @SerializedName("sku")
//        @Expose
//        public String sku;
//        @SerializedName("category_id")
//        @Expose
//        public Integer categoryId;
//        @SerializedName("category_slug")
//        @Expose
//        public String categorySlug;
//        @SerializedName("sub_category_id")
//        @Expose
//        public Integer subCategoryId;
//        @SerializedName("sub_category_slug")
//        @Expose
//        public String subCategorySlug;
//        @SerializedName("super_sub_category_id")
//        @Expose
//        public Integer superSubCategoryId;
//        @SerializedName("super_sub_category_slug")
//        @Expose
//        public String superSubCategorySlug;
//        @SerializedName("name")
//        @Expose
//        public String name;
//        @SerializedName("commission")
//        @Expose
//        public String commission;
//        @SerializedName("p_gst")
//        @Expose
//        public Integer pGst;
//        @SerializedName("color")
//        @Expose
//        public String color;
//        @SerializedName("city_id")
//        @Expose
//        public Integer cityId;
//        @SerializedName("description")
//        @Expose
//        public String description;
//        @SerializedName("type")
//        @Expose
//        public String type;
//        @SerializedName("created_at")
//        @Expose
//        public String createdAt;
//        @SerializedName("updated_at")
//        @Expose
//        public String updatedAt;
//        @SerializedName("status")
//        @Expose
//        public Integer status;
//        @SerializedName("is_admin_approved")
//        @Expose
//        public Integer isAdminApproved;
//        @SerializedName("stock_status")
//        @Expose
//        public Integer stockStatus;
//        @SerializedName("share_count")
//        @Expose
//        public Integer shareCount;
//        @SerializedName("is_cod")
//        @Expose
//        public Integer isCod;
//        @SerializedName("is_return")
//        @Expose
//        public Integer isReturn;
//        @SerializedName("is_exchange")
//        @Expose
//        public Integer isExchange;
//        @SerializedName("related_product")
//        @Expose
//        public String relatedProduct;
//        @SerializedName("is_featured")
//        @Expose
//        public Integer isFeatured;
//        @SerializedName("image")
//        @Expose
//        public String image;
//        @SerializedName("brand_name")
//        @Expose
//        public String brandName;
//
//    }
//
//
//    public class ProductList {
//
//        @SerializedName("defaultProductName")
//        @Expose
//        public String defaultProductName;
//        @SerializedName("defaultImage")
//        @Expose
//        public String defaultImage;
//        @SerializedName("pList")
//        @Expose
//        public PList pList;
//        @SerializedName("productPriceData")
//        @Expose
//        public List<ProductPriceDatum> productPriceData = null;
//        @SerializedName("sellerList")
//        @Expose
//        public List<SellerList> sellerList = null;
//        public int selcted;
//        public int selectedC;
//        public List<String> colorList = null;
//
//    }
//
//
//    public class ProductPriceDatum {
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
//        public Double price;
//        @SerializedName("offer")
//        @Expose
//        public Integer offer;
//        @SerializedName("qty")
//        @Expose
//        public Integer qty;
//        @SerializedName("sprice")
//        @Expose
//        public Double sprice;
//        @SerializedName("created_at")
//        @Expose
//        public String createdAt;
//        @SerializedName("updated_at")
//        @Expose
//        public String updatedAt;
//
//        public int quantity;
//
//    }
//
//
//    public class SellerList {
//
//        @SerializedName("seller_id")
//        @Expose
//        public Integer sellerId;
//        @SerializedName("name")
//        @Expose
//        public String name;
//
//    }
}
