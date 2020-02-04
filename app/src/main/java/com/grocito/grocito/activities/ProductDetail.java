package com.grocito.grocito.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.grocito.grocito.R;
import com.grocito.grocito.adapter.CustomerRevRatingAdapter;
import com.grocito.grocito.adapter.ImageViewPagerAdapter;
import com.grocito.grocito.adapter.SimilarProductAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityProductDetailBinding;
import com.grocito.grocito.model.CartAddModel;
import com.grocito.grocito.model.ImageModel;
import com.grocito.grocito.model.ProductDetialModel;
import com.grocito.grocito.model.SelectedSellerModel2;
import com.grocito.grocito.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

@SuppressLint("DefaultLocale")
public class ProductDetail extends AppCompatActivity implements WebCompleteTask {

    private ActivityProductDetailBinding binding;
    ImageViewPagerAdapter imageViewPagerAdapter;
    ArrayList<ImageModel> imageModelArrayList = new ArrayList<>();

    @BindView(R.id.backBtn)
    ImageView backBtn;
    @BindView(R.id.search_icon)
    ImageView search_icon;

    private SimilarProductAdapter similarProductAdapter;
    private CustomerRevRatingAdapter customerRevRatingAdapter;
    private List<ProductDetialModel.RelatedProductList> arrayList = new ArrayList<>();
    private int countitem = 0;

    private String product_slug;
    public static boolean product_detail = true;
    List<ProductDetialModel.ProductItem_> prices_arrayList = new ArrayList<>();
    private String sellerId, itemId, productId, qty, is_return, is_exchange;
    int item = 1;
    public AlertDialog progressDialog;
    int qty_avlb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);

        ButterKnife.bind(this, this);
        progressDialog = new SpotsDialog(this, R.style.Custom);

        binding.productRecyView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.productRecyView.setNestedScrollingEnabled(false);

        similarProductAdapter = new SimilarProductAdapter(this, arrayList);
        binding.productRecyView.setAdapter(similarProductAdapter);
        similarProductAdapter.notifyDataSetChanged();

        binding.customerRevRecyView.setLayoutManager(new LinearLayoutManager(this));

        backBtn.setOnClickListener(view -> finish());
        product_detail = true;
        binding.headlyaout.cartIcon.setOnClickListener(view -> startActivity(new Intent(ProductDetail.this, Cart.class)));
        binding.headlyaout.searchIcon.setOnClickListener(view -> startActivity(new Intent(ProductDetail.this, SearchItem.class)));
        binding.saveForLaterLL.setOnClickListener(view -> {
            binding.saveForLaterLL.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast.makeText(ProductDetail.this, "Save For Later", Toast.LENGTH_LONG).show();
        });
        Utils.strikeText(binding.priveDisTv);

        countitem = SharedPrefManager.getCartItemCount(Constrants.CartItemCount);
        setCartCount(countitem);

        binding.plusIv.setOnClickListener(v -> {
            if (item<=qty_avlb) {
                binding.addcartTv.setText(getResources().getString(R.string.add_to_cart));
                binding.addcartTv.setTextColor(getResources().getColor(R.color.white));
                binding.cartIcon.setVisibility(View.VISIBLE);

                binding.countTV.setText(++item + "");
            }else {
                binding.addcartTv.setText(getResources().getString(R.string.out_of_stock));
                binding.addcartTv.setTextColor(getResources().getColor(R.color.red));
                binding.cartIcon.setVisibility(View.GONE);
            }
        });
        binding.minusIv.setOnClickListener(v -> {
            if (item > 1) {
                if (item>=qty_avlb||item<=qty_avlb) {
                    binding.addcartTv.setText(getResources().getString(R.string.add_to_cart));
                    binding.addcartTv.setTextColor(getResources().getColor(R.color.white));
                    binding.cartIcon.setVisibility(View.VISIBLE);

                    binding.countTV.setText(--item + "");
                }else {
                    binding.addcartTv.setText(getResources().getString(R.string.out_of_stock));
                    binding.addcartTv.setTextColor(getResources().getColor(R.color.red));
                    binding.cartIcon.setVisibility(View.GONE);
                }
//                binding.countTV.setText(--item + "");
            }
        });
        binding.addCartLL.setOnClickListener(view -> {
            qty = binding.countTV.getText().toString();
            if (binding.addcartTv.getText().toString().equals(getResources().getString(R.string.add_to_cart)))
                addtoCart(sellerId, productId, itemId, qty);
        });


        if (getIntent().getExtras() != null) {
            product_slug = getIntent().getExtras().getString("product_slug", "");
        }
        productDetail();
        binding.capcitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (prices_arrayList.size() > 0) {
                    binding.addcartTv.setVisibility(View.VISIBLE);
                    itemId = prices_arrayList.get(position).id.toString();
                    binding.priveDisTv.setText(String.format("%.0f", Double.parseDouble(prices_arrayList.get(position).price)));
                    binding.priceTV.setText(String.format("%.0f", Double.parseDouble(prices_arrayList.get(position).sprice)));
                    binding.offerPriceTv.setText(String.format("Rs.%s Off", prices_arrayList.get(position).offer));
                    qty_avlb = prices_arrayList.get(position).qty;
                    if (prices_arrayList.get(position).qty > 0) {
                        binding.addcartTv.setText(getResources().getString(R.string.add_to_cart));
                        binding.addcartTv.setTextColor(getResources().getColor(R.color.white));
                        binding.cartIcon.setVisibility(View.VISIBLE);
                    } else {
                        binding.addcartTv.setText(getResources().getString(R.string.out_of_stock));
                        binding.addcartTv.setTextColor(getResources().getColor(R.color.red));
                        binding.cartIcon.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void productDetail() {
        Utils.ProgressShow(this, binding.matrialProgress, binding.nestScrollview);
        HashMap objectNew = new HashMap();
        objectNew.put("product_slug", product_slug);
        objectNew.put("pincode", SharedPrefManager.getPinCode(Constrants.PinCode));
        Log.i("productDetail_res", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.ProductDetails, objectNew,
                ProductDetail.this, RequestCode.CODE_ProductDetails, 5);
    }

    private void addtoCart(String seller_id, String product_id, String itemId, String qty) {
        ProgressDialog("");
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        objectNew.put("product_id", product_id);
        objectNew.put("is_return", is_return);
        objectNew.put("is_exchange", is_exchange);
        objectNew.put("seller_id", seller_id);
        objectNew.put("qty", qty);
        objectNew.put("item_id", itemId + "");
        Log.i("addtoCart_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.AddToCart, objectNew,
                this, RequestCode.CODE_AddToCart, 5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_ProductDetails == taskcode) {
            Log.i("productDetail_res", response);

            ProductDetialModel productDetialModel = JsonDeserializer.deserializeJson(response, ProductDetialModel.class);

            if (productDetialModel.statusCode == 1) {
                binding.productNameTv.setText(productDetialModel.data.productName);
                binding.headlyaout.productCatName.setText(productDetialModel.data.productName);
                productId = productDetialModel.data.productDetails.id.toString();
                is_exchange = productDetialModel.data.productDetails.isExchange.toString();
                is_return = productDetialModel.data.productDetails.isReturn.toString();
                binding.aboutViewTv.setText(Html.fromHtml(productDetialModel.data.productDetails.description));
                binding.ratingBar.setRating(Float.parseFloat(String.format("%s", productDetialModel.data.avgRating.average)));

//                if (productDetialModel.data.sellerCount!=null && productDetialModel.data.sellerCount>1) {
//                    binding.sellerNotv.setVisibility(View.VISIBLE);
                    binding.sellerNotv.setText(String.format("Select seller(%s)", productDetialModel.data.sellerCount + ""));
//                }else {
//                    binding.sellerNotv.setVisibility(View.GONE);
//                }

                binding.whatsAppLL.setOnClickListener(view -> {
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.whatsapp");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, WebUrls.BASE_URL +
                            "\n Product:  " + WebUrls.BASE_URL + "product/" + product_slug);
                    try {
                        startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
                    }
                });

                binding.fbLL.setOnClickListener(view -> {
//            shareFacebook(this, "Referrel Code:  "+ binding.referCodeTv.getText().toString(),WebUrls.BASE_URL);
                    FacebookSdk.sdkInitialize(getApplicationContext());
                    ShareDialog shareDialog = new ShareDialog(this);
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setQuote("Product :  " + WebUrls.BASE_URL + "product/" + product_slug)
//                        .setImageUrl(Uri.parse("https://lh3.googleusercontent.com/jUej7mN6M6iulmuwmW6Mk28PrzXgl-Ebn-MpTmkmwtOfj5f0hvnuw8j0NEzK0GuKoDE=w300-rw"))
                                .setContentUrl(Uri.parse(WebUrls.BASE_URL))
                                .build();

                        shareDialog.show(linkContent);
                    }
                });

                if (productDetialModel.data.productDetails.brand != null && productDetialModel.data.productDetails.brand.name != null)
                    binding.brandTv.setText(productDetialModel.data.productDetails.brand.name);

                //--------------------------Price Spinner---------------
                ArrayList<String> priceAry = new ArrayList<>();
                if (productDetialModel.data.productItem != null && productDetialModel.data.productItem.size() > 0) {
                    this.prices_arrayList.addAll(productDetialModel.data.productItem);
                    for (ProductDetialModel.ProductItem_ price : productDetialModel.data.productItem) {
                        priceAry.add(String.format("%s - Rs%.0f", price.weight, Double.parseDouble(price.sprice)));
                    }
                    ArrayAdapter<String> priceadapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, priceAry);
                    priceadapter.setDropDownViewResource(R.layout.spinnerlayout);
                    binding.capcitySpinner.setAdapter(priceadapter);
                }


                prices_arrayList.clear();
                //--------------------------Image View---------------
                if (productDetialModel.data.allImage != null
                        && productDetialModel.data.allImage.size() > 0) {

                    for (ProductDetialModel.AllImage allImage : productDetialModel.data.allImage) {
                        ImageModel imageModel = new ImageModel();
                        imageModel.setImage(productDetialModel.data.productImage + allImage.image);
                        imageModelArrayList.add(imageModel);
                    }
                    imageViewPagerAdapter = new ImageViewPagerAdapter(this, imageModelArrayList);
                    binding.viewPager.setAdapter(imageViewPagerAdapter);
                    binding.dotsIndicator.setViewPager(binding.viewPager);
                    imageViewPagerAdapter.notifyDataSetChanged();
                }

                //--------------------------Similar Products---------------
                if (productDetialModel.data.relatedProductList != null
                        && productDetialModel.data.relatedProductList.size() > 0) {
                    binding.similarTv.setVisibility(View.VISIBLE);
                    this.arrayList.addAll(productDetialModel.data.relatedProductList);
                    similarProductAdapter.notifyDataSetChanged();
                } else {
                    binding.similarTv.setVisibility(View.GONE);
                }


                if (productDetialModel.data.ratingData.isEmpty()) {
                    binding.customerRevTv.setVisibility(View.GONE);
                    binding.customerRevRecyView.setVisibility(View.GONE);
                } else {
                    binding.customerRevTv.setVisibility(View.VISIBLE);
                    binding.customerRevRecyView.setVisibility(View.VISIBLE);

                    customerRevRatingAdapter = new CustomerRevRatingAdapter(this, productDetialModel.data.ratingData);
                    binding.customerRevRecyView.setAdapter(customerRevRatingAdapter);
                    customerRevRatingAdapter.notifyDataSetChanged();
                }

                //-------------------------------Seller array------------------------------

                ArrayList<String> sellerList = new ArrayList<>();
                if (productDetialModel.data.sellerList != null
                        && productDetialModel.data.sellerList.size() > 0) {
                    for (ProductDetialModel.SellerList sellerList1 : productDetialModel.data.sellerList) {
                        sellerList.add(sellerList1.name);
                    }
//                    if (sellerList.size()>1){
//                        binding.sellerSpinner.setVisibility(View.VISIBLE);
//                    }else {
//                        binding.sellerSpinner.setVisibility(View.INVISIBLE);
//                    }
                    ArrayAdapter<String> selleradapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, sellerList);
                    selleradapter.setDropDownViewResource(R.layout.spinnerlayout);
                    binding.sellerSpinner.setAdapter(selleradapter);
                    binding.sellerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            sellerId = productDetialModel.data.sellerList.get(position).sellerId.toString();
                            getSellerProductItem(sellerId, productId);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }


                Utils.ProgressHide(this, binding.matrialProgress, binding.nestScrollview);
            }

        }
        if (RequestCode.CODE_GetSellerProductItem == taskcode) {
            Log.i("GetSellerProductItem", response);
            //--------------------------Price Spinner---------------
            SelectedSellerModel2 selectedSellerModel = JsonDeserializer.deserializeJson(response, SelectedSellerModel2.class);
            if (selectedSellerModel.statusCode == 1) {
                prices_arrayList.clear();
                ArrayList<String> priceAry = new ArrayList<>();
                if (selectedSellerModel.data.itemData != null) {
                    prices_arrayList.addAll(selectedSellerModel.data.itemData);
                    for (ProductDetialModel.ProductItem_ price : selectedSellerModel.data.itemData) {
                        priceAry.add(String.format("%s - Rs%.0f", price.weight, Double.parseDouble(price.sprice)));
                    }
                    ArrayAdapter<String> priceadapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, priceAry);
                    priceadapter.setDropDownViewResource(R.layout.spinnerlayout);
                    binding.capcitySpinner.setAdapter(priceadapter);
                }
            }

        }
        if (RequestCode.CODE_AddToCart == taskcode) {
            Log.i("AddtoCart", response);
            CartAddModel cartAddModel = JsonDeserializer.deserializeJson(response, CartAddModel.class);
            if (cartAddModel.status == 1) {
                SharedPrefManager.setCartItemCount(Constrants.CartItemCount, cartAddModel.cartCount);
                setCartCount(cartAddModel.cartCount);
            }
            ProgressDialog(cartAddModel.message);
        }
    }

    private void getSellerProductItem(String seller_id, String product_id) {
        HashMap objectNew = new HashMap();
        objectNew.put("seller_id", seller_id);
        objectNew.put("product_id", product_id);
        Log.i("GetSellerProductItem", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.GetSellerProductItem, objectNew, this, RequestCode.CODE_GetSellerProductItem, 5);
    }

    public void ProgressDialog(String message) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            Utils.Toast(this, message);
        } else
            progressDialog.show();
    }

    public void setCartCount(int count) {
        if (binding.headlyaout.cartItemNo != null) {
            if (count > 0) {
                binding.headlyaout.cartItemNo.setVisibility(View.VISIBLE);
            } else {
                binding.headlyaout.cartItemNo.setVisibility(View.GONE);
            }
            binding.headlyaout.cartItemNo.setText(String.valueOf(count));
        }
    }

}
