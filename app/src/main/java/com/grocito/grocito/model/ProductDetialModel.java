package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetialModel {

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

    public class RatingDatum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("product_id")
        @Expose
        public Integer productId;
        @SerializedName("rating")
        @Expose
        public Integer rating;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("order_id")
        @Expose
        public Integer orderId;
        @SerializedName("item_id")
        @Expose
        public Object itemId;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("user")
        @Expose
        public User user;

    }
    public class User {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("reff_code")
        @Expose
        public String reffCode;
        @SerializedName("ref_by")
        @Expose
        public String refBy;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("unique_code")
        @Expose
        public String uniqueCode;
        @SerializedName("agent_id")
        @Expose
        public String agentId;
        @SerializedName("category_id")
        @Expose
        public Integer categoryId;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("simple_pass")
        @Expose
        public String simplePass;
        @SerializedName("otp")
        @Expose
        public Integer otp;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("merchant_count")
        @Expose
        public Integer merchantCount;
        @SerializedName("is_email_varifried")
        @Expose
        public String isEmailVarifried;
        @SerializedName("is_otp_varified")
        @Expose
        public Integer isOtpVarified;
        @SerializedName("role_id")
        @Expose
        public Integer roleId;
        @SerializedName("activated")
        @Expose
        public Integer activated;
        @SerializedName("is_available")
        @Expose
        public Integer isAvailable;
        @SerializedName("banned")
        @Expose
        public Integer banned;
        @SerializedName("verify_status")
        @Expose
        public String verifyStatus;
        @SerializedName("ban_reason")
        @Expose
        public Object banReason;
        @SerializedName("new_password_key")
        @Expose
        public Object newPasswordKey;
        @SerializedName("reset_password_token")
        @Expose
        public Object resetPasswordToken;
        @SerializedName("new_email")
        @Expose
        public Object newEmail;
        @SerializedName("new_email_key")
        @Expose
        public Object newEmailKey;
        @SerializedName("device_token")
        @Expose
        public String deviceToken;
        @SerializedName("device_type")
        @Expose
        public String deviceType;
        @SerializedName("ip_address")
        @Expose
        public String ipAddress;
        @SerializedName("transaction_id")
        @Expose
        public String transactionId;
        @SerializedName("order_id")
        @Expose
        public String orderId;
        @SerializedName("last_login")
        @Expose
        public String lastLogin;
        @SerializedName("rider_lat")
        @Expose
        public String riderLat;
        @SerializedName("rider_long")
        @Expose
        public String riderLong;
        @SerializedName("last_ip")
        @Expose
        public String lastIp;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("deleted")
        @Expose
        public String deleted;
        @SerializedName("new_password_requested")
        @Expose
        public String newPasswordRequested;
        @SerializedName("is_active")
        @Expose
        public Integer isActive;
        @SerializedName("login_time")
        @Expose
        public String loginTime;

    }

    public class AllImage {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("product_id")
        @Expose
        public Integer productId;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("in_stock")
        @Expose
        public Integer inStock;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

    }

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

        @SerializedName("product_name")
        @Expose
        public String productName;
        @SerializedName("productImage")
        @Expose
        public String productImage;
        @SerializedName("sellerCount")
        @Expose
        public Integer sellerCount;
        @SerializedName("defaultSellerItem")
        @Expose
        public List<DefaultSellerItem> defaultSellerItem = null;
        @SerializedName("sellerList")
        @Expose
        public List<SellerList> sellerList = null;
        @SerializedName("product_details")
        @Expose
        public ProductDetails productDetails;
        @SerializedName("avgRating")
        @Expose
        public AvgRating avgRating;
        @SerializedName("productItem")
        @Expose
        public List<ProductItem_> productItem = null;
        @SerializedName("all_image")
        @Expose
        public List<AllImage> allImage = null;
        @SerializedName("relatedProductList")
        @Expose
        public List<RelatedProductList> relatedProductList = null;
        @SerializedName("ratingData")
        @Expose
        public List<RatingDatum> ratingData = null;
    }
    public class AvgRating {

        @SerializedName("average")
        @Expose
        public String average;
        @SerializedName("total_users")
        @Expose
        public Integer totalUsers;

    }

    public class DefaultSellerItem {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("product_id")
        @Expose
        public Integer productId;
        @SerializedName("seller_id")
        @Expose
        public Integer sellerId;
        @SerializedName("weight")
        @Expose
        public String weight;
        @SerializedName("price")
        @Expose
        public Integer price;
        @SerializedName("offer")
        @Expose
        public Integer offer;
        @SerializedName("qty")
        @Expose
        public Integer qty;
        @SerializedName("sprice")
        @Expose
        public String sprice;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

    }



    public class MainCategory {

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

    public class ProductDetails {

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
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
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
        @SerializedName("related_product")
        @Expose
        public String relatedProduct;
        @SerializedName("is_featured")
        @Expose
        public Integer isFeatured;
        @SerializedName("user_name")
        @Expose
        public UserName userName;
        @SerializedName("product_image")
        @Expose
        public List<ProductImage> productImage = null;
        @SerializedName("main_category")
        @Expose
        public MainCategory mainCategory;
        @SerializedName("brand")
        @Expose
        public Brand brand;
        @SerializedName("product_item")
        @Expose
        public List<ProductItem> productItem = null;

    }

    public class ProductImage {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("product_id")
        @Expose
        public Integer productId;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("in_stock")
        @Expose
        public Integer inStock;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

    }

    public class ProductItem {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("product_id")
        @Expose
        public Integer productId;
        @SerializedName("seller_id")
        @Expose
        public Integer sellerId;
        @SerializedName("weight")
        @Expose
        public String weight;
        @SerializedName("price")
        @Expose
        public Integer price;
        @SerializedName("offer")
        @Expose
        public Integer offer;
        @SerializedName("qty")
        @Expose
        public Integer qty;
        @SerializedName("sprice")
        @Expose
        public Double sprice;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

    }

    public class ProductItem_ {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("product_id")
        @Expose
        public Integer productId;
        @SerializedName("seller_id")
        @Expose
        public Integer sellerId;
        @SerializedName("weight")
        @Expose
        public String weight;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("offer")
        @Expose
        public Integer offer;
        @SerializedName("qty")
        @Expose
        public Integer qty;
        @SerializedName("sprice")
        @Expose
        public String sprice;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

    }

    public class SellerList {

        @SerializedName("seller_id")
        @Expose
        public Integer sellerId;
        @SerializedName("name")
        @Expose
        public String name;

    }

    public class UserName {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("reff_code")
        @Expose
        public String reffCode;
        @SerializedName("ref_by")
        @Expose
        public String refBy;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("unique_code")
        @Expose
        public String uniqueCode;
        @SerializedName("agent_id")
        @Expose
        public String agentId;
        @SerializedName("category_id")
        @Expose
        public Integer categoryId;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("simple_pass")
        @Expose
        public String simplePass;
        @SerializedName("otp")
        @Expose
        public Integer otp;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("merchant_count")
        @Expose
        public Integer merchantCount;
        @SerializedName("is_email_varifried")
        @Expose
        public String isEmailVarifried;
        @SerializedName("is_otp_varified")
        @Expose
        public Integer isOtpVarified;
        @SerializedName("role_id")
        @Expose
        public Integer roleId;
        @SerializedName("activated")
        @Expose
        public Integer activated;
        @SerializedName("is_available")
        @Expose
        public Integer isAvailable;
        @SerializedName("banned")
        @Expose
        public Integer banned;
        @SerializedName("verify_status")
        @Expose
        public String verifyStatus;
        @SerializedName("ban_reason")
        @Expose
        public Object banReason;
        @SerializedName("new_password_key")
        @Expose
        public Object newPasswordKey;
        @SerializedName("reset_password_token")
        @Expose
        public Object resetPasswordToken;
        @SerializedName("new_email")
        @Expose
        public Object newEmail;
        @SerializedName("new_email_key")
        @Expose
        public Object newEmailKey;
        @SerializedName("device_token")
        @Expose
        public String deviceToken;
        @SerializedName("device_type")
        @Expose
        public String deviceType;
        @SerializedName("ip_address")
        @Expose
        public String ipAddress;
        @SerializedName("transaction_id")
        @Expose
        public String transactionId;
        @SerializedName("order_id")
        @Expose
        public String orderId;
        @SerializedName("last_login")
        @Expose
        public String lastLogin;
        @SerializedName("rider_lat")
        @Expose
        public String riderLat;
        @SerializedName("rider_long")
        @Expose
        public String riderLong;
        @SerializedName("last_ip")
        @Expose
        public String lastIp;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("deleted")
        @Expose
        public String deleted;
        @SerializedName("new_password_requested")
        @Expose
        public String newPasswordRequested;
        @SerializedName("is_active")
        @Expose
        public Integer isActive;
        @SerializedName("login_time")
        @Expose
        public String loginTime;

    }

    public class RelatedProductList {

        @SerializedName("slug")
        @Expose
        public String slug;
        @SerializedName("p_name")
        @Expose
        public String pName;
        @SerializedName("image")
        @Expose
        public String image;

    }
}
