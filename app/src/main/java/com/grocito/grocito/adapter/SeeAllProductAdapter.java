package com.grocito.grocito.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.ProductDetail;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.SeeAllListItemBBinding;
import com.grocito.grocito.model.SeeAllProductsModel;
import com.grocito.grocito.utils.Utils;

import java.util.List;

@SuppressLint("DefaultLocale")
public class SeeAllProductAdapter extends RecyclerView.Adapter<SeeAllProductAdapter.ViewHolder>  {
    List<SeeAllProductsModel.ProductList> arrayList;
    Context context;
    int raw_pos;
    SeeAllListItemBBinding binding;
    private int positionItem;

    int count = 0;
    private AdapterView.OnItemSelectedListener onItemSelectedListener;

    public SeeAllProductAdapter(Context context, List<SeeAllProductsModel.ProductList> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.see_all_list_item_b, viewGroup,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        SeeAllProductsModel.ProductList homeCatProductModel = arrayList.get(i);


        if (Utils.checkEmptyNull(homeCatProductModel.brandName))
            viewHolder.binding.brandTv.setText(homeCatProductModel.brandName);
        else
            viewHolder.binding.brandTv.setVisibility(View.GONE);

        viewHolder.binding.productName.setText(homeCatProductModel.defaultProductName);
        Utils.strikeText(viewHolder.binding.priveDisTv);
        Utils.setImage(context, viewHolder.binding.productImage, WebUrls.BASE_URL + homeCatProductModel.defaultImage);
        binding.totalTV.setText(String.format("₹%.0f", homeCatProductModel.sprice));
        binding.priveDisTv.setText(String.format("%.0f", homeCatProductModel.price));
        binding.priceTV.setText(String.format("%.0f", homeCatProductModel.sprice));
        binding.offTv.setText(String.format("₹%.0f Off", homeCatProductModel.offerprice));


//        //-------------------------------Seller Spinner------------------------------
//        ArrayList<String> sellerList = new ArrayList<>();
//        for (SeeAllProductsModel.SellerList sellerList1 : homeCatProductModel.sellerList) {
//            sellerList.add(sellerList1.name);
//        }
//        ArrayAdapter<String> sellerAdapter = new ArrayAdapter<>(context,
//                android.R.layout.simple_spinner_item, sellerList);
//        sellerAdapter.setDropDownViewResource(R.layout.spinnerlayout);
//        viewHolder.binding.sellerSpinner.setAdapter(sellerAdapter);
//        viewHolder.binding.sellerSpinner.setTag(i);
//
//        //-------------------------------Price Spinner-----------------
//        ArrayList<String> proPrcList = new ArrayList<>();
//        for (SeeAllProductsModel.ProductPriceDatum price : homeCatProductModel.productPriceData) {
//            proPrcList.add(String.format("%s - ₹%.0f", price.weight, price.sprice));
//        }
//        ArrayAdapter<String> priceadapter = new ArrayAdapter<>(context,
//                android.R.layout.simple_spinner_item, proPrcList);
//        priceadapter.setDropDownViewResource(R.layout.spinnerlayout);
//        viewHolder.binding.capcitySpinner.setAdapter(priceadapter);
//        viewHolder.binding.capcitySpinner.setTag(i);


      /*  //--------------------------------Color RecyclerView---------------------------------------
        binding.colorRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        if (homeCatProductModel.pList.color.split(",").length > 0 && Utils.checkEmptyNull(homeCatProductModel.pList.color.split(",")[0])) {
            binding.colorLL.setVisibility(View.VISIBLE);
            ColorAdapter adapter = new ColorAdapter(context, homeCatProductModel.pList.color.split(","));
            binding.colorRv.setAdapter(adapter);
        } else {
            binding.colorLL.setVisibility(View.GONE);
        }//--------------------*/

//        binding.plusIv.setTag(i);
//      binding.plusIv.setOnClickListener(this);

        //binding.sellerSpinner.setSelection(arrayList.get(positionItem).selcted);
//        binding.sellerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // raw_pos = getAdapterPosition();
//                try {
//                    if (arrayList.get(positionItem).selcted != position) {
//                        positionItem = i;
//                        arrayList.get(positionItem).selcted = position;
//                        System.out.print("call_count" + count++);
//                        getSellerProductItem(raw_pos,
//                                "" + arrayList.get(positionItem).sellerList.get(position).sellerId,
//                                "" + arrayList.get(positionItem).pList.id);
//
//                    }
//                } catch (IndexOutOfBoundsException e) {
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//        binding.capcitySpinner.setSelection(arrayList.get(i).selcted);
//        try {
//            binding.totalTV.setText(String.format("₹%.0f", arrayList.get(i).productPriceData.get(arrayList.get(i).selcted).sprice));
//            binding.priveDisTv.setText(String.format("%.0f", arrayList.get(i).productPriceData.get(arrayList.get(i).selcted).price));
//            binding.priceTV.setText(String.format("%.0f", arrayList.get(i).productPriceData.get(arrayList.get(i).selcted).sprice));
//            binding.offTv.setText(String.format("₹%s Off", arrayList.get(i).productPriceData.get(arrayList.get(i).selcted).offer));
//
//        } catch (ArrayIndexOutOfBoundsException e) {
//
//        }

//        if (arrayList.get(i).)
//        try {


//        } catch (ArrayIndexOutOfBoundsException e) {
//
//        }


//        binding.totalTV.setText(String.format("₹%.0f", arrayList.get(i).productPriceData.get(arrayList.get(i).selcted).sprice));
        /*binding.capcitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    binding.totalTV.setText(String.format("₹%.0f", arrayList.get(i).productPriceData.get(position ).sprice));
                    binding.priveDisTv.setText(String.format("%.0f", arrayList.get(i).productPriceData.get(position).price));
                    binding.priceTV.setText(String.format("%.0f", arrayList.get(i).productPriceData.get(position).sprice));
                    binding.offTv.setText(String.format("₹%s Off", arrayList.get(i).productPriceData.get(position).offer));

                } catch (ArrayIndexOutOfBoundsException e) {

                }
             //   cap_pos = position;
             //   arrayList.get(i).selcted = position;
              //   notifyItemChanged(i);
               // Log.i("sadfsafsafdsafdsaf", cap_pos + "");
*//*
                    binding.totalTV.setText(String.format("₹%.0f", arrayList.get(getAdapterPosition()).productPriceData.get(position).sprice));
                    binding.priveDisTv.setText(String.format("%.0f", arrayList.get(getAdapterPosition()).productPriceData.get(position).price));
                    binding.priceTV.setText(String.format("%.0f", arrayList.get(getAdapterPosition()).productPriceData.get(position).sprice));
                    binding.offTv.setText(String.format("₹%s Off", arrayList.get(getAdapterPosition()).productPriceData.get(position).offer));

                    if (arrayList.get(getAdapterPosition()).productPriceData.get(position).qty > 0
                            && arrayList.get(getAdapterPosition()).productPriceData.get(position).qty <= 10) {
//                        item = 1;

                        binding.countTV.setText(String.valueOf(item));
                        binding.outOfStockTv.setVisibility(View.VISIBLE);
                        binding.outOfTv.setVisibility(View.GONE);
                        binding.outOfStockTv.setText(String.format("Only %d item in stock",
                                arrayList.get(getAdapterPosition()).productPriceData.get(position).qty));
                        binding.cartLL.setVisibility(View.VISIBLE);

                    } else if (arrayList.get(getAdapterPosition()).productPriceData.get(position).qty > 10) {

                        binding.outOfStockTv.setVisibility(View.GONE);
                        binding.outOfTv.setVisibility(View.GONE);
                        binding.cartLL.setVisibility(View.VISIBLE);

                    } else {

                        binding.outOfStockTv.setVisibility(View.GONE);
                        binding.outOfTv.setVisibility(View.VISIBLE);
                        binding.outOfTv.setText(context.getString(R.string.out_of_stock));
                        binding.cartLL.setVisibility(View.GONE);

                    }

                    binding.plusIv.setOnClickListener(view1 -> {
                        if (arrayList.get(getAdapterPosition()).productPriceData.get(position).qty <= item) {
                            Utils.Toast(context, context.getString(R.string.out_of_stock));
                        } else {
                            item += 1;
                            binding.countTV.setText(String.valueOf(item));
                            binding.totalTV.setText(String.format("%.2f", item * Double.parseDouble(binding.priceTV.getText().toString())));
                        }
                    });
                    binding.minusIv.setOnClickListener(view1 -> {
                        if (item > 1) {
                            item -= 1;
                            binding.countTV.setText(String.valueOf(item));
                            binding.totalTV.setText(String.format("%.2f", item * Double.parseDouble(binding.priceTV.getText().toString())));
                        }
                    });*//*
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });*/
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  position;
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SeeAllListItemBBinding binding;
        int item = 1;
        int cap_pos = 0;

        public ViewHolder(@NonNull SeeAllListItemBBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.topRl.setOnClickListener(view -> context.startActivity(new Intent(context, ProductDetail.class)
                    .putExtra("product_slug", arrayList.get(getAdapterPosition()).productSlug)));

//            binding.cartLL.setOnClickListener(v -> {
//                SeeAllProduct.getInstance().proShow(true, "");
//                addtoCart(raw_pos,
//                        arrayList.get(getAdapterPosition()).sellerList.get(binding.sellerSpinner.getSelectedItemPosition()).sellerId
//                        , arrayList.get(getAdapterPosition()).productPriceData.get(raw_pos).productId
//                        , arrayList.get(getAdapterPosition()).productPriceData.get(binding.capcitySpinner.getSelectedItemPosition()).id
//                        , item);
//            });

                //  binding.totalTV.setText(String.format("₹%.0f", arrayList.get(getAdapterPosition()).productPriceData.get(0).sprice));
      /*          binding.totalTV.setText(String.format("₹%.0f", arrayList.get(getAdapterPosition()).productPriceData.get(arrayList.get(getAdapterPosition()).selcted).sprice));
                binding.priveDisTv.setText(String.format("%.0f", arrayList.get(getAdapterPosition()).productPriceData.get(arrayList.get(getAdapterPosition()).selcted).price));
                binding.priceTV.setText(String.format("%.0f", arrayList.get(getAdapterPosition()).productPriceData.get(arrayList.get(getAdapterPosition()).selcted).sprice));
                binding.offTv.setText(String.format("₹%s Off", arrayList.get(getAdapterPosition()).productPriceData.get(arrayList.get(getAdapterPosition()).selcted).offer));
*/

//            binding.capcitySpinner.setTag(getAdapterPosition());
//            binding.capcitySpinner.setOnItemSelectedListener(onItemSelectedListener);
//            Log.i("sadfsafdssadfsdfafdsaf", "sadf");
          /*  binding.capcitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cap_pos = position;
                    arrayList.get(getAdapterPosition()).selcted = position;
                    // notifyItemChanged(getAdapterPosition());
                    Log.i("sadfsafsafdsafdsaf", cap_pos + "");
*//*
                    binding.totalTV.setText(String.format("₹%.0f", arrayList.get(getAdapterPosition()).productPriceData.get(position).sprice));
                    binding.priveDisTv.setText(String.format("%.0f", arrayList.get(getAdapterPosition()).productPriceData.get(position).price));
                    binding.priceTV.setText(String.format("%.0f", arrayList.get(getAdapterPosition()).productPriceData.get(position).sprice));
                    binding.offTv.setText(String.format("₹%s Off", arrayList.get(getAdapterPosition()).productPriceData.get(position).offer));

                    if (arrayList.get(getAdapterPosition()).productPriceData.get(position).qty > 0
                            && arrayList.get(getAdapterPosition()).productPriceData.get(position).qty <= 10) {
//                        item = 1;

                        binding.countTV.setText(String.valueOf(item));
                        binding.outOfStockTv.setVisibility(View.VISIBLE);
                        binding.outOfTv.setVisibility(View.GONE);
                        binding.outOfStockTv.setText(String.format("Only %d item in stock",
                                arrayList.get(getAdapterPosition()).productPriceData.get(position).qty));
                        binding.cartLL.setVisibility(View.VISIBLE);

                    } else if (arrayList.get(getAdapterPosition()).productPriceData.get(position).qty > 10) {

                        binding.outOfStockTv.setVisibility(View.GONE);
                        binding.outOfTv.setVisibility(View.GONE);
                        binding.cartLL.setVisibility(View.VISIBLE);

                    } else {

                        binding.outOfStockTv.setVisibility(View.GONE);
                        binding.outOfTv.setVisibility(View.VISIBLE);
                        binding.outOfTv.setText(context.getString(R.string.out_of_stock));
                        binding.cartLL.setVisibility(View.GONE);

                    }

                    binding.plusIv.setOnClickListener(view1 -> {
                        if (arrayList.get(getAdapterPosition()).productPriceData.get(position).qty <= item) {
                            Utils.Toast(context, context.getString(R.string.out_of_stock));
                        } else {
                            item += 1;
                            binding.countTV.setText(String.valueOf(item));
                            binding.totalTV.setText(String.format("%.2f", item * Double.parseDouble(binding.priceTV.getText().toString())));
                        }
                    });
                    binding.minusIv.setOnClickListener(view1 -> {
                        if (item > 1) {
                            item -= 1;
                            binding.countTV.setText(String.valueOf(item));
                            binding.totalTV.setText(String.format("%.2f", item * Double.parseDouble(binding.priceTV.getText().toString())));
                        }
                    });*//*
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }

            });*/

//            binding.plusIv.setOnClickListener(view1 -> {
//                if (arrayList.get(getAdapterPosition()).productPriceData.get(cap_pos).qty <= item) {
//                    Utils.Toast(context, context.getString(R.string.out_of_stock));
//                } else {
//                    item += 1;
//                    binding.countTV.setText(String.valueOf(item));
//                    binding.totalTV.setText(String.format("%.2f", item * Double.parseDouble(binding.priceTV.getText().toString())));
//                }
//            });
//            binding.minusIv.setOnClickListener(view1 -> {
//                if (item > 1) {
//                    item -= 1;
//                    binding.countTV.setText(String.valueOf(item));
//                    binding.totalTV.setText(String.format("%.2f", item * Double.parseDouble(binding.priceTV.getText().toString())));
//                }
//            });

        }
    }

//    private void getSellerProductItem(int pos, String seller_id, String product_id) {
//        raw_pos = pos;
//        HashMap objectNew = new HashMap();
//        objectNew.put("seller_id", seller_id);
//        objectNew.put("product_id", product_id);
//        Log.i("GetSellerProductItem", objectNew + "");
//        new WebTask(context, WebUrls.BASE_URL + WebUrls.GetSellerProductItem, objectNew,
//                this, RequestCode.CODE_GetSellerProductItem, 5);
//    }
//
//    private void addtoCart(int pos, int seller_id, int product_id, int itemId, int qty) {
//        raw_pos = pos;
//        HashMap objectNew = new HashMap();
//        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
//        objectNew.put("product_id", product_id + "");
//        objectNew.put("is_return", arrayList.get(raw_pos).pList.isReturn + "");
//        objectNew.put("is_exchange", arrayList.get(raw_pos).pList.isExchange + "");
//        objectNew.put("seller_id", seller_id + "");
//        objectNew.put("qty", qty + "");
////        objectNew.put("color", "");
//        objectNew.put("item_id", itemId + "");
//        Log.i("addtoCart_obj", objectNew + "");
//        new WebTask(context, WebUrls.BASE_URL + WebUrls.AddToCart, objectNew,
//                this, RequestCode.CODE_AddToCart, 5);
//    }
//
//    @Override
//    public void onComplete(String response, int taskcode) {
//        if (RequestCode.CODE_GetSellerProductItem == taskcode) {
//            Log.i("GetSellerProductItem", response);
//            SelectedSellerModel selectedSellerModel = JsonDeserializer.deserializeJson(response, SelectedSellerModel.class);
//            if (selectedSellerModel.statusCode == 1) {
//                if (selectedSellerModel.data.itemData != null) {
//                    arrayList.get(positionItem).productPriceData.clear();
//                    arrayList.get(positionItem).productPriceData.addAll(selectedSellerModel.data.itemData);
//                } else {
//
//                }
//                notifyItemChanged(positionItem);
//            }
//        }
//        if (RequestCode.CODE_AddToCart == taskcode) {
//            Log.i("AddtoCart", response);
//            CartAddModel cartAddModel = JsonDeserializer.deserializeJson(response, CartAddModel.class);
//            if (cartAddModel.status == 1) {
//                SeeAllProduct.getInstance().addCart(cartAddModel.cartCount);
//            }
//            SeeAllProduct.getInstance().proShow(false, cartAddModel.message);
//        }
//    }
}
