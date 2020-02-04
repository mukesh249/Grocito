package com.grocito.grocito.model;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeCatProductModel implements Serializable {

    String id,user_id,brand_id,slug,category_id,category_slug,sub_category_id,sub_category_slug,name;
    String commission,p_gst,color,city_id,type,status,is_admin_approved,stock_status,share_count,is_cod,is_return,is_exchange,related_product,is_featured,description,image,brand_name,price_pro;
    JSONArray priceAry = new JSONArray();
    JSONArray sellerList = new JSONArray();
    ArrayList<String> colorAry= new ArrayList<>();
    ArrayList<String> selectedcolorAry= new ArrayList<>();

    ArrayList<ArrayList<HomeCatProductModel.Price>> price_arrayList_Ary;
    ArrayList<ArrayList<HomeCatProductModel.Seller>> seller_arrayList_Ary;


    public ArrayList<String> getSelectedcolorAry() {
        return selectedcolorAry;
    }

    public void setSelectedcolorAry(ArrayList<String> selectedcolorAry) {
        this.selectedcolorAry = selectedcolorAry;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_admin_approved() {
        return is_admin_approved;
    }

    public void setIs_admin_approved(String is_admin_approved) {
        this.is_admin_approved = is_admin_approved;
    }

    public String getStock_status() {
        return stock_status;
    }

    public void setStock_status(String stock_status) {
        this.stock_status = stock_status;
    }

    public String getShare_count() {
        return share_count;
    }

    public void setShare_count(String share_count) {
        this.share_count = share_count;
    }

    public String getIs_cod() {
        return is_cod;
    }

    public void setIs_cod(String is_cod) {
        this.is_cod = is_cod;
    }

    public String getIs_return() {
        return is_return;
    }

    public void setIs_return(String is_return) {
        this.is_return = is_return;
    }

    public String getIs_exchange() {
        return is_exchange;
    }

    public void setIs_exchange(String is_exchange) {
        this.is_exchange = is_exchange;
    }

    public String getRelated_product() {
        return related_product;
    }

    public void setRelated_product(String related_product) {
        this.related_product = related_product;
    }

    public String getIs_featured() {
        return is_featured;
    }

    public void setIs_featured(String is_featured) {
        this.is_featured = is_featured;
    }

    public ArrayList<String> getColorAry() {
        return colorAry;
    }

    public void setColorAry(ArrayList<String> colorAry) {
        this.colorAry = colorAry;
    }

    public ArrayList<ArrayList<Price>> getPrice_arrayList_Ary() {
        return price_arrayList_Ary;
    }

    public void setPrice_arrayList_Ary(ArrayList<ArrayList<Price>> price_arrayList_Ary) {
        this.price_arrayList_Ary = price_arrayList_Ary;
    }

    public ArrayList<ArrayList<Seller>> getSeller_arrayList_Ary() {
        return seller_arrayList_Ary;
    }

    public void setSeller_arrayList_Ary(ArrayList<ArrayList<Seller>> seller_arrayList_Ary) {
        this.seller_arrayList_Ary = seller_arrayList_Ary;
    }

    public JSONArray getPriceAry() {
        return priceAry;
    }

    public void setPriceAry(JSONArray priceAry) {
        this.priceAry = priceAry;
    }

    public JSONArray getSellerList() {
        return sellerList;
    }

    public void setSellerList(JSONArray sellerList) {
        this.sellerList = sellerList;
    }

    public String getPrice_pro() {
        return price_pro;
    }

    public void setPrice_pro(String price_pro) {
        this.price_pro = price_pro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_slug() {
        return category_slug;
    }

    public void setCategory_slug(String category_slug) {
        this.category_slug = category_slug;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getSub_category_slug() {
        return sub_category_slug;
    }

    public void setSub_category_slug(String sub_category_slug) {
        this.sub_category_slug = sub_category_slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getP_gst() {
        return p_gst;
    }

    public void setP_gst(String p_gst) {
        this.p_gst = p_gst;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public static class Price implements Serializable{
        String price_id,product_id,seller_id,weight,price,offer,qty,sprice;
        Integer position;

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public String getPrice_id() {
            return price_id;
        }

        public void setPrice_id(String price_id) {
            this.price_id = price_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOffer() {
            return offer;
        }

        public void setOffer(String offer) {
            this.offer = offer;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getSprice() {
            return sprice;
        }

        public void setSprice(String sprice) {
            this.sprice = sprice;
        }
    }

    public static class Seller implements Serializable{
        String seller_id,name;

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

