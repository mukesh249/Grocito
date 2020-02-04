package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceOrderCodModel {

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
        public Integer gstAmount;
        @SerializedName("shipping_charge")
        @Expose
        public Integer shippingCharge;
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
        public Integer netAmount;
        @SerializedName("sgst_amount")
        @Expose
        public Integer sgstAmount;
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

    }


        @SerializedName("status_code")
        @Expose
        public Integer statusCode;
        @SerializedName("data")
        @Expose
        public Data data;
        @SerializedName("message")
        @Expose
        public String message;

}
