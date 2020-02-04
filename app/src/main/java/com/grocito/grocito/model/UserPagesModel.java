package com.grocito.grocito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserPagesModel {

    public class AboutUs {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("description")
        @Expose
        public String description;
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

    public class Data {

        @SerializedName("about_us")
        @Expose
        public AboutUs aboutUs;
        @SerializedName("term_condition")
        @Expose
        public TermCondition termCondition;
        @SerializedName("privacy_policy")
        @Expose
        public PrivacyPolicy privacyPolicy;
        @SerializedName("return_policy")
        @Expose
        public ReturnPolicy returnPolicy;
        @SerializedName("shipping_delivery")
        @Expose
        public ShippingDelivery shippingDelivery;
        @SerializedName("payment_policy")
        @Expose
        public PaymentPolicy paymentPolicy;
        @SerializedName("discount_information")
        @Expose
        public DiscountInformation discountInformation;
        @SerializedName("faq")
        @Expose
        public Faq faq;

    }


    public class DiscountInformation {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("description")
        @Expose
        public String description;
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
        @SerializedName("status_code")
        @Expose
        public Integer statusCode;
        @SerializedName("data")
        @Expose
        public Data data;
        @SerializedName("faq")
        @Expose
        public List<Faq> faq = null;
        @SerializedName("message")
        @Expose
        public String message;


    public class Faq {

        @SerializedName("faq")
        @Expose
        public List<FaqList> faqList = null;

    }

    public class FaqList {

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


    public class PaymentPolicy {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("description")
        @Expose
        public String description;
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


    public class PrivacyPolicy {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("description")
        @Expose
        public String description;
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


    public class ReturnPolicy {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("description")
        @Expose
        public String description;
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


    public class ShippingDelivery {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("description")
        @Expose
        public String description;
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

    public class TermCondition {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("description")
        @Expose
        public String description;
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
