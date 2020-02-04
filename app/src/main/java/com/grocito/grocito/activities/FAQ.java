package com.grocito.grocito.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.grocito.grocito.R;
import com.grocito.grocito.adapter.FaqAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.ActivityFaqBinding;
import com.grocito.grocito.model.UserPagesModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FAQ extends AppCompatActivity implements WebCompleteTask {

    List<UserPagesModel.FaqList> arrayList = new ArrayList<>();
    private ActivityFaqBinding binding;
    private FaqAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_faq);

        binding.headlyaout.searchIcon.setVisibility(View.GONE);
        binding.headlyaout.cartRL.setVisibility(View.GONE);
        binding.headlyaout.productCatName.setText(getResources().getString(R.string.faqs));
        binding.headlyaout.backBtn.setOnClickListener(view->finish());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonPages();
    }
    public void CommonPages(){
        HashMap objectNew = new HashMap();
//        objectNew.put("")
        new WebTask(this, WebUrls.BASE_URL+WebUrls.UserPages,objectNew,FAQ.this, RequestCode.CODE_UserPages,5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_UserPages == taskcode){
            Log.i("faq_pages",response);
            UserPagesModel userPagesModel = JsonDeserializer.deserializeJson(response,UserPagesModel.class);
            if (userPagesModel.statusCode==1){
                if (!userPagesModel.data.faq.faqList.isEmpty()){
                    arrayList.addAll(userPagesModel.data.faq.faqList);
                    adapter = new FaqAdapter(this,arrayList);
                    binding.recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
