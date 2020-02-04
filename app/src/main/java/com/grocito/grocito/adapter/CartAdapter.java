package com.grocito.grocito.adapter;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import com.grocito.grocito.activities.Cart;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.model.CartDataModel;
import com.grocito.grocito.R;
import com.grocito.grocito.databinding.CartItemBinding;
import com.grocito.grocito.utils.Utils;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> implements WebCompleteTask {

    List<CartDataModel.Datum> arrayList;
    Context context;
//    View.OnClickListener onClickListener;

    public CartAdapter(Context context, List<CartDataModel.Datum> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
//        this.onClickListener=onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        CartItemBinding cartItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                ,R.layout.cart_item,viewGroup,false);
        return new ViewHolder(cartItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//
        CartDataModel.Datum cartDataModel = arrayList.get(i);
        viewHolder.binding.setCartView(cartDataModel);


        String sddf = String.format("%.0f",Double.parseDouble(cartDataModel.sprice));
        Log.d("sfdsadfsaf",sddf);
        viewHolder.binding.totalTV.setText(sddf);
        viewHolder.binding.priceTV.setText(sddf);
        viewHolder.binding.totalTV.setText(Integer.parseInt(cartDataModel.qty)*Integer.parseInt(sddf)+"");
        Utils.setImage(context,viewHolder.binding.productImage, WebUrls.BASE_URL+WebUrls.IMAGE_PRODUCT+cartDataModel.productImage);



//        viewHolder.binding.plusIv.setTag(i);
//        viewHolder.binding.minusIv.setTag(i);
//        viewHolder.binding.plusIv.setOnClickListener(onClickListener);
//        viewHolder.binding.minusIv.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CartItemBinding binding;


        public ViewHolder(CartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.plusIv.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                int item = Integer.parseInt(binding.countTV.getText().toString());
                item += 1;
                if (item<=arrayList.get(pos).getItem.qty){
                    binding.countTV.setText(String.valueOf(item));
                    binding.totalTV.setText(String.valueOf(item*Integer.parseInt(binding.priceTV.getText().toString())));
                    qtyInc(pos,item,arrayList.get(pos).id);
                }else {
                    Utils.Toast(context,context.getResources().getString(R.string.out_of_stock));
                }

            });

            binding.minusIv.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                int item = Integer.parseInt(binding.countTV.getText().toString());
                if (item>1){
                    item -= 1;
                    if (item<=arrayList.get(pos).getItem.qty||item>=arrayList.get(pos).getItem.qty) {
                        binding.countTV.setText(String.valueOf(item));
                        binding.totalTV.setText(String.valueOf(item * Integer.parseInt(binding.priceTV.getText().toString())));
                        qtyInc(pos, item, arrayList.get(pos).id);
                    }else {
                        Utils.Toast(context,context.getResources().getString(R.string.out_of_stock));
                    }
                }
            });

            binding.productImage.setOnClickListener(view -> {
//                    context.startActivity(new Intent(context, ProductDetail.class));
            });
            binding.deleteIv.setOnClickListener(view -> cartItemDelete(getAdapterPosition(),arrayList.get(getAdapterPosition()).id));


        }
    }
    private int cartPos;
    public void qtyInc(int pos,int qty,int cartId) {
        cartPos = pos;
        HashMap objectNew = new HashMap();
        objectNew.put("cart_id", cartId+"");
        objectNew.put("qty", qty+"");
        objectNew.put("item_id", arrayList.get(pos).itemId+"");
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        arrayList.get(cartPos).qty = qty+"";
        Log.i("qtyInc_obj", objectNew + "");
        new WebTask(context, WebUrls.BASE_URL + WebUrls.UpdateCartData,
                objectNew, this, RequestCode.CODE_UpdateCartData, 1);
    }
    public void cartItemDelete(int pos,int cartId) {
        Cart.getInstance().showProgress();
        HashMap objectNew = new HashMap();
        objectNew.put("cart_id", cartId+"");
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        Log.i("deleteCartItem_obj", objectNew + "");
        new WebTask(context, WebUrls.BASE_URL + WebUrls.DeleteCart,
                objectNew, this, RequestCode.CODE_DeleteCart, 5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_UpdateCartData==taskcode){
            Log.i("qtyInc_res", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status")==1){
                    notifyDataSetChanged();
                    Cart.getInstance().setTotalSum();
                }else {
                    Utils.Toast(context,jsonObject.optString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (RequestCode.CODE_DeleteCart==taskcode){
            Log.i("deleteCartItem_res", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status")==1){
                    Cart.getInstance().cartList();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
