package com.grocito.grocito.model;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.grocito.grocito.api.WebUrls;

public class CartDataModel {

//    public String brandName;
//    public int Image;

//    public ModelCart(String brandName, int image) {
//        this.brandName = brandName;
//        Image = image;
//    }



    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;
    @SerializedName("cart_count")
    @Expose
    public Integer cartCount;
    @SerializedName("error_message")
    @Expose
    public String errorMessage;

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


    public class CartProduct {

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
        @SerializedName("brand")
        @Expose
        public Brand brand;

    }


    public static class Datum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("seller_id")
        @Expose
        public Integer sellerId;
        @SerializedName("product_id")
        @Expose
        public Integer productId;
        @SerializedName("attributes")
        @Expose
        public String attributes;
        @SerializedName("is_return")
        @Expose
        public Integer isReturn;
        @SerializedName("is_exchange")
        @Expose
        public Integer isExchange;
        @SerializedName("item_id")
        @Expose
        public Integer itemId;
        @SerializedName("qty")
        @Expose
        public String qty;
        @SerializedName("price")
        @Expose
        public Integer price;
        @SerializedName("sprice")
        @Expose
        public String sprice;
        @SerializedName("admin_commission")
        @Expose
        public Integer adminCommission;
        @SerializedName("gst_percentage")
        @Expose
        public Integer gstPercentage;
        @SerializedName("weight")
        @Expose
        public String weight;
        @SerializedName("size")
        @Expose
        public String size;
        @SerializedName("product_image")
        @Expose
        public String productImage;
        @BindingAdapter("android:imageUrl")
        public static void loadImage(View view, String productImage){
            ImageView imageView = (ImageView)view;
            Glide.with(view).load(WebUrls.BASE_URL+WebUrls.IMAGE_PRODUCT+productImage).into(imageView);
//            imageView.setImageDrawable(ContextCompat.getDrawable(view.getContext(),image));
        }
        @SerializedName("product_name")
        @Expose
        public String productName;
        @SerializedName("shipping_free_amount")
        @Expose
        public Integer shippingFreeAmount;
        @SerializedName("system_address")
        @Expose
        public String systemAddress;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("get_item")
        @Expose
        public GetItem getItem;
        @SerializedName("cart_product")
        @Expose
        public CartProduct cartProduct;


    }

    public class GetItem {

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
        public Integer sprice;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

    }
}
