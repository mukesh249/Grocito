package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GrocitoWalletModel {
    @SerializedName("status_code")
    @Expose
    public Integer statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("total_amount")
    @Expose
    public String totalAmount;
    @SerializedName("grocito_wallet")
    @Expose
    public List<GrocitoWallet> grocitoWallet = null;
    @SerializedName("refer_wallet")
    @Expose
    public List<ReferWallet> referWallet = null;

    public class GrocitoWallet {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("ref_id")
        @Expose
        public Integer refId;
        @SerializedName("merchant_id")
        @Expose
        public String merchantId;
        @SerializedName("amount")
        @Expose
        public String amount;
        @SerializedName("payment_type")
        @Expose
        public String paymentType;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("commission_status")
        @Expose
        public Integer commissionStatus;
        @SerializedName("reason")
        @Expose
        public String reason;
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

    public class ReferWallet {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("ref_id")
        @Expose
        public Integer refId;
        @SerializedName("merchant_id")
        @Expose
        public String merchantId;
        @SerializedName("amount")
        @Expose
        public Integer amount;
        @SerializedName("payment_type")
        @Expose
        public String paymentType;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("commission_status")
        @Expose
        public Integer commissionStatus;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("user")
        @Expose
        public User_ user;
        @SerializedName("reason")
        @Expose
        public String reason;

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

    public class User_ {

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
}
