package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrderDetailsModel {

    public class Address {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("house")
        @Expose
        public String house;
        @SerializedName("street")
        @Expose
        public String street;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("landmark")
        @Expose
        public String landmark;
        @SerializedName("state")
        @Expose
        public String state;
        @SerializedName("pincode")
        @Expose
        public String pincode;
        @SerializedName("lattitude")
        @Expose
        public String lattitude;
        @SerializedName("longitude")
        @Expose
        public String longitude;
        @SerializedName("is_default")
        @Expose
        public Integer isDefault;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

    }

    public class Data {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("warehouse_id")
        @Expose
        public Integer warehouseId;
        @SerializedName("seller_id")
        @Expose
        public Integer sellerId;
        @SerializedName("total_amount")
        @Expose
        public String totalAmount;
        @SerializedName("admin_commission")
        @Expose
        public Double adminCommission;
        @SerializedName("address_id")
        @Expose
        public Integer addressId;
        @SerializedName("order_id")
        @Expose
        public String orderId;
        @SerializedName("ord_payment_id")
        @Expose
        public String ordPaymentId;
        @SerializedName("delivery_boy_id")
        @Expose
        public Integer deliveryBoyId;
        @SerializedName("reason")
        @Expose
        public String reason;
        @SerializedName("shipped_by")
        @Expose
        public String shippedBy;
        @SerializedName("dock_no")
        @Expose
        public String dockNo;
        @SerializedName("payment_amount")
        @Expose
        public Integer paymentAmount;
        @SerializedName("gst_amount")
        @Expose
        public String gstAmount;
        @SerializedName("shipping_charge")
        @Expose
        public String shippingCharge;
        @SerializedName("cashback_amount")
        @Expose
        public Integer cashbackAmount;
        @SerializedName("extra_amount")
        @Expose
        public Integer extraAmount;
        @SerializedName("wallet_amount")
        @Expose
        public Integer walletAmount;
        @SerializedName("net_amount")
        @Expose
        public String netAmount;
        @SerializedName("sgst_amount")
        @Expose
        public String sgstAmount;
        @SerializedName("wallet_use")
        @Expose
        public Integer walletUse;
        @SerializedName("wallet_pay")
        @Expose
        public Integer walletPay;
        @SerializedName("payment_mode")
        @Expose
        public String paymentMode;
        @SerializedName("payment_status")
        @Expose
        public String paymentStatus;
        @SerializedName("status_message")
        @Expose
        public String statusMessage;
        @SerializedName("transaction_id")
        @Expose
        public String transactionId;
        @SerializedName("delivery_type")
        @Expose
        public String deliveryType;
        @SerializedName("delivery_date")
        @Expose
        public String deliveryDate;
        @SerializedName("delivery_time")
        @Expose
        public String deliveryTime;
        @SerializedName("express_time")
        @Expose
        public String expressTime;
        @SerializedName("shipped_date")
        @Expose
        public String shippedDate;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("is_cod_submitted")
        @Expose
        public Integer isCodSubmitted;
        @SerializedName("distance")
        @Expose
        public Integer distance;
        @SerializedName("d_p_d_amount")
        @Expose
        public Integer dPDAmount;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("order_meta_data")
        @Expose
        public List<OrderMetaDatum> orderMetaData = null;
        @SerializedName("address")
        @Expose
        public Address address;

    }

    @SerializedName("status_code")
    @Expose
    public Integer statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("total_return_amount")
    @Expose
    public String totalReturnAmount;

    @SerializedName("item_count")
    @Expose
    public Integer itemCount;

    @SerializedName("return_count")
    @Expose
    public Integer returnCount;

    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("meta_data")
    @Expose
    public List<MetaDatum> metaData = null;

    @SerializedName("return_reason")
    @Expose
    public List<ReturnReason> returnReason = null;
    @SerializedName("exchange_reason")
    @Expose
    public List<ExchangeReason> exchangeReason = null;

    @SerializedName("order_otp")
    @Expose
    public String order_otp;

    public class ReturnReason {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;


    }

    public class ExchangeReason {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
    }

    public class MetaDatum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("parent_id")
        @Expose
        public Integer parentId;
        @SerializedName("seller_id")
        @Expose
        public Integer sellerId;
        @SerializedName("order_id")
        @Expose
        public Integer orderId;
        @SerializedName("sub_order_id")
        @Expose
        public String subOrderId;
        @SerializedName("product_id")
        @Expose
        public Integer productId;
        @SerializedName("item_id")
        @Expose
        public Integer itemId;
        @SerializedName("weight")
        @Expose
        public String weight;
        @SerializedName("size")
        @Expose
        public String size;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("offer_amount")
        @Expose
        public String offerAmount;
        @SerializedName("product_commission")
        @Expose
        public Double productCommission;
        @SerializedName("gst_amount")
        @Expose
        public Integer gstAmount;
        @SerializedName("cashback_amount")
        @Expose
        public Integer cashbackAmount;
        @SerializedName("qty")
        @Expose
        public Integer qty;
        @SerializedName("attributes")
        @Expose
        public String attributes;
        @SerializedName("product_image")
        @Expose
        public String productImage;
        @SerializedName("product_name")
        @Expose
        public String productName;
        @SerializedName("shipping_free_amount")
        @Expose
        public Integer shippingFreeAmount;
        @SerializedName("is_return")
        @Expose
        public Integer isReturn;
        @SerializedName("is_exchange")
        @Expose
        public Integer isExchange;
        @SerializedName("expected_delivery_date")
        @Expose
        public String expectedDeliveryDate;
        @SerializedName("cancel_request")
        @Expose
        public Integer cancelRequest;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("delivery_date")
        @Expose
        public String deliveryDate;

        public long shippedDateMills;

        @SerializedName("return_status")
        @Expose
        public Integer returnStatus;
        @SerializedName("exchange_status")
        @Expose
        public Integer exchangeStatus;
        @SerializedName("net_amount")
        @Expose
        public String netAmount;
        @SerializedName("delivery_boy_id")
        @Expose
        public Integer deliveryBoyId;
        @SerializedName("product")
        @Expose
        public Product product;

    }

    public class ProductRating {

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

    }

    public class OrderMetaDatum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("parent_id")
        @Expose
        public Integer parentId;
        @SerializedName("seller_id")
        @Expose
        public Integer sellerId;
        @SerializedName("order_id")
        @Expose
        public Integer orderId;
        @SerializedName("sub_order_id")
        @Expose
        public String subOrderId;
        @SerializedName("product_id")
        @Expose
        public Integer productId;
        @SerializedName("item_id")
        @Expose
        public Integer itemId;
        @SerializedName("weight")
        @Expose
        public String weight;
        @SerializedName("size")
        @Expose
        public String size;
        @SerializedName("price")
        @Expose
        public Double price;
        @SerializedName("offer_amount")
        @Expose
        public Integer offerAmount;
        @SerializedName("product_commission")
        @Expose
        public Double productCommission;
        @SerializedName("gst_amount")
        @Expose
        public Integer gstAmount;
        @SerializedName("cashback_amount")
        @Expose
        public Integer cashbackAmount;
        @SerializedName("qty")
        @Expose
        public Integer qty;
        @SerializedName("attributes")
        @Expose
        public String attributes;
        @SerializedName("product_image")
        @Expose
        public String productImage;
        @SerializedName("product_name")
        @Expose
        public String productName;
        @SerializedName("shipping_free_amount")
        @Expose
        public Integer shippingFreeAmount;
        @SerializedName("is_return")
        @Expose
        public Integer isReturn;
        @SerializedName("is_exchange")
        @Expose
        public Integer isExchange;
        @SerializedName("expected_delivery_date")
        @Expose
        public String expectedDeliveryDate;
        @SerializedName("cancel_request")
        @Expose
        public Integer cancelRequest;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("return_status")
        @Expose
        public Integer returnStatus;
        @SerializedName("exchange_status")
        @Expose
        public Integer exchangeStatus;
        @SerializedName("net_amount")
        @Expose
        public String netAmount;
        @SerializedName("delivery_boy_id")
        @Expose
        public Integer deliveryBoyId;

    }

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
        @SerializedName("product_rating")
        @Expose
        public ProductRating productRating;

    }
}
