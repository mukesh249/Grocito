package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeGsonModel {
    @SerializedName("status_code")
    @Expose
    public Integer statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("error_message")
    @Expose
    public String errorMessage;
    @SerializedName("product_img_url")
    @Expose
    public String productImgUrl;
    @SerializedName("cat_img_url")
    @Expose
    public String catImgUrl;

    public class BottomBanner {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("images")
        @Expose
        public String images;
        @SerializedName("link")
        @Expose
        public String link;
        @SerializedName("p_link")
        @Expose
        public Integer pLink;
        @SerializedName("link_type")
        @Expose
        public String linkType;
        @SerializedName("link_slug")
        @Expose
        public Object linkSlug;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("main_category")
        @Expose
        public MainCategoryP mainCategoryp;
        @SerializedName("product")
        @Expose
        public Product product;
    }

    public class CatAndSubCatList {

        @SerializedName("cat_id")
        @Expose
        public Integer catId;
        @SerializedName("cat_slu")
        @Expose
        public String catSlu;
        @SerializedName("cat_name")
        @Expose
        public String catName;
        @SerializedName("subCatList")
        @Expose
        public List<SubCatList> subCatList = null;

    }

    public class CatDatum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("slug")
        @Expose
        public String slug;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("position")
        @Expose
        public Integer position;
        @SerializedName("is_special")
        @Expose
        public Integer isSpecial;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("app_icon")
        @Expose
        public String appIcon;
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

    public class CatWithProduct {

        @SerializedName("cat_id")
        @Expose
        public Integer catId;
        @SerializedName("cat_name")
        @Expose
        public String catName;
        @SerializedName("cat_slug")
        @Expose
        public String catSlug;
        @SerializedName("productListData")
        @Expose
        public List<ProductListDatum> productListData = null;

    }

    public class Data {
        @SerializedName("userNotifyCount")
        @Expose
        public Integer userNotifyCount;
        @SerializedName("catAndSubCatList")
        @Expose
        public List<CatAndSubCatList> catAndSubCatList = null;
        @SerializedName("catData")
        @Expose
        public List<CatDatum> catData = null;
        @SerializedName("catWithProduct")
        @Expose
        public List<CatWithProduct> catWithProduct = null;
        @SerializedName("isSpecial")
        @Expose
        public IsSpecial isSpecial;
        @SerializedName("sliderList")
        @Expose
        public List<SliderList> sliderList = null;
        @SerializedName("middleBanner")
        @Expose
        public MiddleBanner middleBanner;
        @SerializedName("bottomBanner")
        @Expose
        public BottomBanner bottomBanner;
        @SerializedName("sundayProduct")
        @Expose
        public List<SundayProduct> sundayProduct = null;
        @SerializedName("isGrocitoExclusive")
        @Expose
        public List<SundayProduct> isGrocitoExclusive = null;
        @SerializedName("newArrival")
        @Expose
        public List<SundayProduct> newArrival = null;
        @SerializedName("firstOfferBanner")
        @Expose
        public List<FirstOfferBanner> firstOfferBanner = null;

    }
    public class FirstOfferBanner {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("images")
        @Expose
        public String images;
        @SerializedName("link")
        @Expose
        public String link;
        @SerializedName("p_link")
        @Expose
        public Integer pLink;
        @SerializedName("link_type")
        @Expose
        public String linkType;
        @SerializedName("link_slug")
        @Expose
        public Object linkSlug;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("main_category")
        @Expose
        public MainCategory mainCategory;
        @SerializedName("product")
        @Expose
        public Product product;

    }

//    public class MainCategory {
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
//        @SerializedName("app_icon")
//        @Expose
//        public String appIcon;
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

    public class Product {

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

    }

    public class IsSpecial {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("slug")
        @Expose
        public String slug;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("position")
        @Expose
        public Integer position;
        @SerializedName("is_special")
        @Expose
        public Integer isSpecial;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("app_icon")
        @Expose
        public String appIcon;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("status")
        @Expose
        public Integer status;

        @SerializedName("main_category_special")
        @Expose
        public List<MainCategory> mainCategory = null;

    }

    public class MainCategory {

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

    public class MiddleBanner {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("images")
        @Expose
        public String images;
        @SerializedName("link")
        @Expose
        public String link;
        @SerializedName("p_link")
        @Expose
        public Integer pLink;
        @SerializedName("link_type")
        @Expose
        public String linkType;
        @SerializedName("link_slug")
        @Expose
        public Object linkSlug;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("main_category")
        @Expose
        public MainCategoryP mainCategoryp;
        @SerializedName("product")
        @Expose
        public Product product;

    }
    public class ProductListDatum {

        @SerializedName("is_scheme")
        @Expose
        public Integer isScheme;
        @SerializedName("p_slug")
        @Expose
        public String pSlug;
        @SerializedName("p_name")
        @Expose
        public String pName;
        @SerializedName("p_image")
        @Expose
        public String pImage;

    }


    public class SliderList {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("images")
        @Expose
        public String images;
        @SerializedName("link")
        @Expose
        public String link;
        @SerializedName("p_link")
        @Expose
        public Integer pLink;
        @SerializedName("link_type")
        @Expose
        public String linkType;
        @SerializedName("link_slug")
        @Expose
        public Object linkSlug;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("main_category")
        @Expose
        public MainCategoryP mainCategoryp;
        @SerializedName("product")
        @Expose
        public Product product;

    }
    public class MainCategoryP {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("slug")
        @Expose
        public String slug;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("position")
        @Expose
        public Integer position;
        @SerializedName("is_special")
        @Expose
        public Integer isSpecial;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("app_icon")
        @Expose
        public String appIcon;
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

    public class SubCatList {

        @SerializedName("subCatId")
        @Expose
        public Integer subCatId;
        @SerializedName("subCatSlug")
        @Expose
        public String subCatSlug;
        @SerializedName("subCatName")
        @Expose
        public String subCatName;

    }

    public class SundayProduct {

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
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("brand_name")
        @Expose
        public String brandName;

    }
}
