package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrderListModel {

    public class Datum {

        @SerializedName("id")
        @Expose
        public String id;
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
        public String adminCommission;
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
        public Integer gstAmount;
        @SerializedName("shipping_charge")
        @Expose
        public Double shippingCharge;
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
        public String price;
        @SerializedName("offer_amount")
        @Expose
        public Integer offerAmount;
        @SerializedName("product_commission")
        @Expose
        public String productCommission;
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
}
